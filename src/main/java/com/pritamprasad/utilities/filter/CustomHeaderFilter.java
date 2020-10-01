package com.pritamprasad.utilities.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
public class CustomHeaderFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(CustomHeaderFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOG.info("Executing CustomHeaderFilter... ");
        ((HttpServletResponse)servletResponse).setHeader("myheader", UUID.randomUUID().toString());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
