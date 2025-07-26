package com.hms.user.utility;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ErrorInfo {
    private String errorMassage;
    private Integer errorCode;
    private LocalDateTime timestamp;

}
