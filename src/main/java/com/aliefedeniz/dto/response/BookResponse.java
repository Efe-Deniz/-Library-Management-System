package com.aliefedeniz.dto.response;

import lombok.Data;

@Data
public class BookResponse {

    private Long id;
    private String name;
    private String authorName;
    private String publisher;

}
