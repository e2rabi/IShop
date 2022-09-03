package com.errabi.common.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IShopExceptionAuth extends RuntimeException {
    private String errorCode;
    private String errorDescription;

}
