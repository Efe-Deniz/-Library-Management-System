package com.aliefedeniz.sevice.concretes;

import com.aliefedeniz.core.results.Result;
import com.aliefedeniz.core.results.ResultData;
import com.aliefedeniz.core.results.ResultInfo;
import com.aliefedeniz.dto.request.BookSaveRequest;
import com.aliefedeniz.dto.request.BookUpdateRequest;
import com.aliefedeniz.dto.response.BookResponse;
import com.aliefedeniz.entity.Book;
import com.aliefedeniz.repository.BookRepository;
import com.aliefedeniz.sevice.abstracts.IBookService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookManager implements IBookService {
    private final ModelMapper modelMaper;
    private final BookRepository bookRepository;


    @Override
    public ResultData<BookResponse> saveBook(BookSaveRequest bookSaveRequest) {

        if(bookRepository.existsByName(bookSaveRequest.getName())){
            throw new IllegalArgumentException("Bu isimde kayıtlı kitap zaten mevcut: " + bookSaveRequest.getName());
        }

        //Dto->Entity dönüşümü
        Book book = modelMaper.map(bookSaveRequest,Book.class);
        //veri tabanına kaydetme
        bookRepository.save(book);
        //Entity ->Response dönüşümü
        BookResponse bookResponse = modelMaper.map(book,BookResponse.class);
        return ResultInfo.created(bookResponse);
    }

    @Override
    public ResultData<BookResponse> updateBook(BookUpdateRequest bookUpdateRequest) {

        Book existing = bookRepository.findById(bookUpdateRequest.getId())
                .orElseThrow(()-> new EntityNotFoundException("Güncellenecek kitap bulunamadı"));
        modelMaper.map(bookUpdateRequest,existing);
        //ModelMapper bazı durumlarda null alanları da overwrite eder. bunu engellemek için
        modelMaper.getConfiguration().setSkipNullEnabled(true);
        bookRepository.save(existing);
        BookResponse bookResponse = modelMaper.map(existing,BookResponse.class);
        return ResultInfo.success(bookResponse);
    }

    @Override
    public ResultData<List<BookResponse>> findByName(String name) {

        List<Book> bookList = bookRepository.findByNameContainingIgnoreCase(name);
        if(bookList.isEmpty()) throw new EntityNotFoundException("Bu isimle kitap bulunamadı " +name);

        List<BookResponse> bookResponses = bookList.stream()
                .map(book -> modelMaper.map(book,BookResponse.class))
                .collect(Collectors.toList());
        return ResultInfo.success(bookResponses);
    }

    @Override
    public ResultData<BookResponse> findByBookId(Long id) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Bu kitap bulunamadı" + id));

        BookResponse bookResponse = modelMaper.map(existing,BookResponse.class);
        return ResultInfo.success(bookResponse);
    }

    @Override
    public ResultData<List<BookResponse>> findAll() {
        List<Book> bookList = bookRepository.findAll();
        List<BookResponse> bookResponses= bookList.stream()
                .map(book -> modelMaper.map(book,BookResponse.class))
                .collect(Collectors.toList());
        return ResultInfo.success(bookResponses);
    }

    @Override
    public ResultData<List<BookResponse>> findByAuthorName(String authorName) {
        List<Book> bookList = bookRepository.findByAuthorNameContainingIgnoreCase(authorName);
        List<BookResponse> bookResponseList = bookList.stream()
                .map(book -> modelMaper.map(book,BookResponse.class))
                .collect(Collectors.toList());
        return ResultInfo.success(bookResponseList);
    }

    @Override
    public ResultData<List<BookResponse>> findByPublisherName(String publisherName) {
        List<Book> bookList = bookRepository.findDistinctByPublishersName(publisherName);
        List<BookResponse> bookResponseList = bookList.stream()
                .map(book -> modelMaper.map(book,BookResponse.class))
                .collect(Collectors.toList());
        return ResultInfo.success(bookResponseList);
    }


    @Override
    public Result deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Silinecek kitap bulunamadı"));
        bookRepository.delete(book);
        return ResultInfo.okMessage("Kitap başarılı bir şekilde silindi");
    }
}
