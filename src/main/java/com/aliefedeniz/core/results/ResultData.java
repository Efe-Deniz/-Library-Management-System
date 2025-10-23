package com.aliefedeniz.core.results;

import lombok.Getter;

@Getter
public class ResultData<T> extends Result {

    private T data;

    public ResultData(boolean success, String message, String code, T data) {
        super(success, message, code);
        this.data = data;
    }
}
