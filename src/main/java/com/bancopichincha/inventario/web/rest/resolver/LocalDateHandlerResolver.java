package com.bancopichincha.inventario.web.rest.resolver;

import com.bancopichincha.inventario.service.params.RangeDateParam;
import com.bancopichincha.inventario.web.rest.util.ValidatorUtils;
import com.bancopichincha.inventario.web.rest.validator.annotation.DateRange;
import java.time.LocalDate;
import java.util.Objects;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LocalDateHandlerResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return (methodParameter.getParameterAnnotation(DateRange.class) != null && RangeDateParam.class.equals(methodParameter.getParameterType()));
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String startDate = nativeWebRequest.getParameter("startDate");
        String endDate = nativeWebRequest.getParameter("endDate");
        LocalDate localStartDate = LocalDate.parse(startDate);
        LocalDate localEndDate = LocalDate.parse(endDate);
        RangeDateParam dateParam = new RangeDateParam(localStartDate, localEndDate);
        WebDataBinder binder = webDataBinderFactory.createBinder(nativeWebRequest, dateParam, Objects.requireNonNull(methodParameter.getParameterName()));
        ValidatorUtils.validateTarget(binder, methodParameter);
        if (binder.getBindingResult().hasErrors()) {
            throw new MethodArgumentNotValidException(methodParameter, binder.getBindingResult());
        }
        return dateParam;
    }

}
