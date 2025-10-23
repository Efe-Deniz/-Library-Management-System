package com.aliefedeniz.core.results;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Result {
    private boolean success;
    private String message;
    private String code;
}
