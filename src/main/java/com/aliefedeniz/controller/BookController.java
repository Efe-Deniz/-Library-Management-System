package com.aliefedeniz.controller;

import com.aliefedeniz.core.results.ResultData;
import com.aliefedeniz.dto.request.BookSaveRequest;
import com.aliefedeniz.dto.request.BookUpdateRequest;
import com.aliefedeniz.dto.request.UserSaveRequest;
import com.aliefedeniz.dto.response.BookResponse;
import com.aliefedeniz.dto.response.UserResponse;
import com.aliefedeniz.sevice.abstracts.IBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/books")
public class BookController {

    private final IBookService bookService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<BookResponse> saveBook(@Valid @RequestBody BookSaveRequest bookSaveRequest){
        return this.bookService.saveBook(bookSaveRequest);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<BookResponse> updateBook(@PathVariable("id") Long id, @Valid @RequestBody BookUpdateRequest bookUpdateRequest){
        return this.bookService.updateBook(bookUpdateRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<BookResponse>



}
