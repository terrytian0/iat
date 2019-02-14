package com.terry.iat.service.common.filter;


import com.terry.iat.service.common.content.ThreadLocalContext;
import com.terry.iat.service.common.content.WebContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author terry
 */
@Slf4j
public class WebContextFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        ThreadLocalContext localContext = new ThreadLocalContext();
        WebContext.registry(localContext);
        try {
            allowCrossDomainRequest(httpServletRequest, (HttpServletResponse) response);
            chain.doFilter(request, response);
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            throw e;
        } finally {
            WebContext.release();
            stopWatch.stop();
        }
    }


    @Override
    public void destroy() {
    }

    private void allowCrossDomainRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String header = request.getHeader("Origin");
        if (header != null) {
            response.setHeader("Access-Control-Allow-Origin", header);
        }
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Authentication,Content-Type");
    }

}
