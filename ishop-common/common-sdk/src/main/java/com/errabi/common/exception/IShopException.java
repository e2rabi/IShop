package com.errabi.common.exception;

import lombok.Data;

@Data
public class IShopException extends RuntimeException{
    private String errorCode;
    private String errorDescription;

    public IShopException(){
    }
    public IShopException( String errorCode, String errorDescription) {
        super(errorDescription);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }
    public IShopException( String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}
