package com.errabi.common.handler;

import com.errabi.common.exception.ErrorMessage;
import com.errabi.common.exception.IShopException;
import com.errabi.common.exception.IShopExceptionAuth;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class IShopExceptionHandler {

    @ExceptionHandler(value = {IShopExceptionAuth.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorMessage handleIShopExceptionAuth(IShopExceptionAuth ex, WebRequest request) {
        return ErrorMessage.builder()
                            .code(ex.getErrorCode())
                            .description(ex.getErrorDescription())
                            .requestId(request.getContextPath())
                            .time(LocalDateTime.now().toString())
                            .status(HttpStatus.UNAUTHORIZED.value())
                            .build();
    }

}

