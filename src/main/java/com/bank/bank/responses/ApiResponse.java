package com.bank.bank.responses;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private T data;
    private List<T> dataList;
    private HttpStatus status;
    private String message;
    private String requestId;
    private boolean error;
    private LocalDateTime timestamp = LocalDateTime.now();
    private PageInfoDTO pageInfo;

    // Constructor with data, dataList, status, message, requestId, and error
    public ApiResponse(T data, List<T> dataList, HttpStatus status, String message, String requestId, boolean error,
                       LocalDateTime timestamp) {
        this.data = data;
        this.dataList = dataList;
        this.status = status;
        this.message = message;
        this.requestId = requestId;
        this.error = error;
        this.timestamp = timestamp;
    }

    public ApiResponse(T data, List<T> dataList, HttpStatus status, String message, boolean error,
                       LocalDateTime timestamp, PageInfoDTO pageInfo) {
        this.data = data;
        this.dataList = dataList;
        this.status = status;
        this.message = message;
        this.error = error;
        this.timestamp = timestamp;
        this.pageInfo = pageInfo;
    }

    public ApiResponse(T data, HttpStatus status, String message, boolean error, LocalDateTime timestamp) {
        this.data = data;
        this.status = status;
        this.message = message;
        this.error = error;
        this.timestamp = timestamp;
    }

    public ApiResponse(List<T> dataList, HttpStatus status, String message, boolean error, LocalDateTime timestamp) {
        this.dataList = dataList;
        this.status = status;
        this.message = message;
        this.error = error;
        this.timestamp = timestamp;
    }
}