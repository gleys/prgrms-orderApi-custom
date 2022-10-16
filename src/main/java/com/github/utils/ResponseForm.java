package com.github.utils;

import lombok.Data;
import org.springframework.http.HttpStatus;

public class ResponseForm {

    //성공
    public static<T> Result<T> success(T response) {
        return new Result<>(true, response, null);
    }

    //잘못된 요청
    public static Result error(Throwable throwable, HttpStatus status) {
        return new Result(false, null, new ResponseError(throwable.getMessage(), status.value()));
    }

    //요청에 해당하는 결과 없음
    public static<T> Result<T> error(String message, HttpStatus status) {
        return new Result<>(false, null, new ResponseError(message, status.value()));
    }

    @Data
    public static class ResponseError {
        private final String message;
        private final int status;
    }

    @Data
    public static class Result<T> {
        private final boolean success;
        private final T response;
        private final ResponseError error;
    }

}
