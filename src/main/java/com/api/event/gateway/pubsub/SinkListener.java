package com.api.event.gateway.pubsub;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.api.event.gateway.exception.EventAPIException;

@Component
public class SinkListener implements WeakConcurrentHashMapListener {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private int eventsRequestSize = 0;
	private boolean sinkStatus;

	private static Integer batchSize;
	private static String sinkDataFilesPath;
	
	@Autowired
	public  SinkListener(@Value("${sink.batch.size}") String batchSize,
			@Value("${sink.data.files.path}") String sinkFilePath) {
		this.batchSize = Integer.valueOf(batchSize);
		this.sinkDataFilesPath = sinkFilePath;
	}
	
	 public SinkListener() {
	 }

	@Override
	public boolean notifyOnAdd(WeakConcurrentHashMap<String, List> dataMap) {

		eventsRequestSize = 0;
		sinkStatus = false;

		dataMap.entrySet().stream().forEach((entry) -> {
			eventsRequestSize = eventsRequestSize + entry.getValue().size();
			if (eventsRequestSize == batchSize) {
				applySink(dataMap);
				sinkStatus = true;
			}
		});
		return sinkStatus;
	}
	@Override
	public  boolean applySink(WeakConcurrentHashMap dataMap) {
		
		//logger.info(" <<<<<<<<<<<<<<<<<< Sink Batch Job Running >>>>>>>>>>>>>>>>>>>>>>>");
		
		boolean sinkStatus = false;
		try {
		for (Object key : dataMap.keySet()) {
			StringBuilder sb1 = new StringBuilder();

			for (SinkData data : (List<SinkData>) dataMap.get(key)) {
				sb1.append(data.toString());
				sb1.append("\n");
			}

			Path filePathObj = Paths.get(sinkDataFilesPath + (String) key);

			boolean fileExists = Files.exists(filePathObj);

			
				if (!fileExists) {
					Files.createFile(filePathObj);
				}
				// Appending The New Data To The Existing File
				Files.write(filePathObj, sb1.toString().getBytes(), StandardOpenOption.APPEND);
				// System.out.println("! Data Successfully Appended !");
				sinkStatus = true;
		}} catch (EventAPIException | IOException ioExceptionObj) {
			throw new EventAPIException("Error while running the sync process !!");
			
		} 
		//logger.info(" <<<<<<<<<<<<<<<<<< Sink Batch Job Ends >>>>>>>>>>>>>>>>>>>>>>>");
		return sinkStatus;
	}
   
}
