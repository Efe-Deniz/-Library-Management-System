package com.aliefedeniz.sevice.abstracts;

import com.aliefedeniz.core.results.Result;
import com.aliefedeniz.core.results.ResultData;
import com.aliefedeniz.dto.request.BookSaveRequest;
import com.aliefedeniz.dto.request.BookUpdateRequest;
import com.aliefedeniz.dto.response.BookResponse;

import java.util.List;

public interface IBookService {

    ResultData<BookResponse> saveBook(BookSaveRequest bookSaveRequest);
    ResultData<BookResponse> updateBook(BookUpdateRequest bookUpdateRequest);
    ResultData<List<BookResponse>> findByName(String name);
    ResultData<BookResponse> findByBookId(Long id);
    ResultData<List<BookResponse>> findAll();
    ResultData<List<BookResponse>> findByAuthorName(String authorName);
    ResultData<List<BookResponse>> findByPublisherName(String publisherName);
    Result deleteBook(Long id);


}
