package com.aliefedeniz.controller;

import com.aliefedeniz.sevice.abstracts.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/books")
public class BookController {

    private final IBookService bookService;



}
