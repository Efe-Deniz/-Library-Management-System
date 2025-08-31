package Library_Management_System;

import java.time.LocalDate;

public class Loan {

    private long bookId;
    private long memberId;
    private LocalDate borrowDate;//Ödünç alındığı tarih
    private LocalDate dueDate;// Bitiş tarihi
    private LocalDate returnDate;//Kitabın getirildiği tarih


    public Loan(long bookId, long memberId, LocalDate borrowDate, LocalDate dueDate, LocalDate returnDate) {
        setBookId(bookId);
        setMemberId(memberId);
        setBorrowDate(borrowDate);
        setDueDate(dueDate);
        setReturnDate(returnDate);

    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        if(bookId<=0){
            throw new IllegalArgumentException("Kitap ID’si pozitif olmalı.");
        }
        this.bookId = bookId;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        if(memberId<=0){
            throw new IllegalArgumentException("Üye numarası pozitif olmalı");
        }
        this.memberId = memberId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        if(borrowDate == null){
            throw new IllegalArgumentException("Kitabın ödünç alındığı tarih boş olamaz!!");
        }
        if(borrowDate.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("Ödünç alma tarihi ileri bir tarih olamaz");
        }


        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        if(dueDate==null){
            throw new IllegalArgumentException("Son teslim tarihi boş olamaz");
        }
        if (borrowDate == null) {
            throw new IllegalStateException("Son teslim tarihi belirlenmeden önce ödünç alma tarihi ayarlanmalıdır");
        }

        if(dueDate.isBefore(borrowDate)){
            throw new IllegalArgumentException("Son teslim tarihi, ödünç alma tarihinden önce olamaz");
        }
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {

        if(returnDate == null){
            this.returnDate = null;
            return;
        }

        if(borrowDate == null){
            throw new IllegalStateException("İade tarihi belirlenmeden önce ödünç alma tarihi ayarlanmalıdır");
        }
        if(returnDate.isBefore(borrowDate)){
            throw new IllegalArgumentException("İade tarihi, ödünç alma tarihinden önce olamaz");
        }

        if (returnDate.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("İade tarihi ileri bir tarih olamaz");
        }
        this.returnDate = returnDate;
    }

}
