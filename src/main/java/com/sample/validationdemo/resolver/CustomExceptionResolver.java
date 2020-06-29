package com.sample.validationdemo.resolver;

import com.sample.validationdemo.error.X2ApiErrors;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CustomExceptionResolver extends AbstractHandlerExceptionResolver {
    @Override
    protected ModelAndView doResolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        logger.info("in ExceptionResolver");
        X2ApiErrors errors = new X2ApiErrors();
        errors.setMessage(e.getMessage());
        ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
        mav.addObject(errors);
        return mav;
    }
}
