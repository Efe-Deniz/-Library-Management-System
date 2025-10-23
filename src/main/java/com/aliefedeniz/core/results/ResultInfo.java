package com.aliefedeniz.core.results;

import com.aliefedeniz.core.util.message.Messages;

public class ResultInfo {

    // Creates a result indicating that an entity has been successfully created.
    public static <T> ResultData<T> created(T data) {
        return new ResultData<>(true, Messages.CREATED, "201", data);
    }

    // Creates a result indicating a data validation error.
    public static <T> ResultData<T> validationError(T data) {
        return new ResultData<>(false, Messages.VALIDATE_ERROR, "400", data);
    }
    // Creates a result indicating that an operation was successful.
    public static <T> ResultData<T> success(T data) {
        return new ResultData<>(true, Messages.OK, "200", data);
    }

    // Creates a generic successful result without associated data.
    public static Result ok() {
        return new Result(true, Messages.OK, "200");
    }
    // ResultInfo.java
    public static Result okMessage(String message) {
        return new Result(true, message, "200");
    }


    // Creates a result indicating a resource not found error with a custom message.
    public static Result notFoundError(String message) {
        return new Result(false, message, "404");
    }

    // Creates a result indicating that a resource is already registered with a custom message.
    public static Result alreadyRegistered(String message) {
        return new Result(false, message, "500");
    }

    // Creates a result indicating a generic error with a custom message.
    public static Result errorMessage(String message) {
        return new Result(false, message, "400");
    }

    public static <T> ResultData<T> conflict(String message,T data) {
        return new ResultData<>(false, message, "500", data);
    }
}
