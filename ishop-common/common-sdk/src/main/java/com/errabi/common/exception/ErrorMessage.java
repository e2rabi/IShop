package com.errabi.common.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage {
    private String requestId ;
    private String code;
    private String description;
    private String time ;
    private Integer status;
}
