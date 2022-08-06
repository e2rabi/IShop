package com.errabi.common.exception;

import lombok.Data;

@Data
public class IShopNotFoundException extends RuntimeException{
    private String errorCode;
    private String errorDescription;
    public IShopNotFoundException(String errorCode) {
        super(errorCode);
    }
}
