package com.api.event.gateway.metric;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Component
public class MetricFilter implements Filter {

    @Autowired
    private IMetricService metricService;
    
    private static final String EVENT_API_URI_PATH = "/event-api";

   

    @Override
    public void init(final FilterConfig config) throws ServletException {
        if (metricService == null) {
            metricService = (IMetricService) WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext()).getBean("metricService");
        }
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws java.io.IOException, ServletException {
        final HttpServletRequest httpRequest = ((HttpServletRequest) request);
        final String req = httpRequest.getMethod() + " " + httpRequest.getRequestURI();

        chain.doFilter(request, response);

        final int status = ((HttpServletResponse) response).getStatus();
        
        if (req.contains(EVENT_API_URI_PATH)) {
        	metricService.increaseCount(req, status);
        }
    }

    @Override
    public void destroy() {

    }
}
