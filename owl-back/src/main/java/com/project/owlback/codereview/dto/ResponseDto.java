package com.project.owlback.codereview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    private Integer code;
    private HttpStatus httpStatus;
    private String message;
    private Integer count; // result 사이즈
    private List<?> result;

    public static ResponseDto create(HttpStatus httpStatus, String message, List<?> result) {
        return ResponseDto.builder()
                .httpStatus(httpStatus)
                .code(httpStatus.value())
                .message(message)
                .count(result.size())
                .result(result)
                .build();
    }
}
