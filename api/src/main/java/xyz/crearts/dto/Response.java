package xyz.crearts.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    private short code;
    private String message;
}
