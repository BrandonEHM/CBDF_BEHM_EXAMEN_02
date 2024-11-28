package com.upiiz.logs.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LogDTO {
    private Long log_id;
    private LocalDateTime log_date;
    private Long user_id;
    private String action;
}