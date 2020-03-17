package com.api.event.gateway.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.api.event.gateway.metric.IMetricService;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(APIMetricController.class)
public class APIMetricControllerTests {
	
	
	@Autowired
    private MockMvc mvc;
	
	@MockBean
    private IMetricService service;
 
	@After
    public void tearDown() {
        
    }
	
	@Before
    public void tearUp() {
		Map result = new HashMap<>();
		result.put("200", "1");
		
		given(service.getStatusMetric()).willReturn(result);
		given(service.getFullMetric()).willReturn(result);
    }
	
	@Test
	public void statusMetric_Test() throws Exception {
		mvc.perform(get("/auth/status-metric")
			      .contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());

    }
	
	@Test
	public void metric_Test() throws Exception {
		mvc.perform(get("/auth/metric")
			      .contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());

    }
}

