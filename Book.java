package Library_Management_System;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Book {

    private long id;
    private String title;
    private Genre genre;
    private int publicationYear;
    private String isbn;
    private int stock;
    private List<String> authors;


    public Book(long id, String title, List<String> authors, Genre genre, int publicationYear, String isbn, int stock) {
        setId(id);
        setTitle(title);
        setAuthors(authors);
        setGenre(genre);
        setPublicationYear(publicationYear);
        setIsbn(isbn);
        setStock(stock);
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        if(id<=0){
            throw new IllegalArgumentException("Kitap kimliği 0 ve negatif olamaz");
        }
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(title == null){
            throw new IllegalArgumentException("Kitap başlığı boş olamaz.");
        }
        String blank = title.trim();
        if(blank.isEmpty()){
            throw new IllegalArgumentException("Kitap başlığı boş olamaz.");
        }
        if(blank.length()<2 || blank.length()>200){
            throw new IllegalArgumentException("Kitap başlığı 2 ile 200 karakter arasında olmalıdır.");
        }

        this.title =blank;
    }
    public List<String> getAuthors() {
        return new ArrayList<>(authors);
    }

    public void setAuthors(List<String> authors) {

        if(authors == null){
            throw new IllegalArgumentException("Yazar listesi boş olamaz");
        }
        if(authors.isEmpty()){
            throw new IllegalArgumentException("Yazar listesi boş olamaz");
        }
        List<String> cleanList= new ArrayList<>(); //bir kitabın yazar listesini oluşturuyoruz
        for (String name:authors){
            if (name == null){
                throw new IllegalArgumentException("Yazar adı boş olamaz.");
            }
            String trimName= name.trim().toLowerCase();
            if(trimName.isEmpty()){
                throw new IllegalArgumentException("Yazar adı boş olamaz.");
            }
            if(cleanList.contains(trimName) == false){
                /*
                listedeki yazar isimlerini kontrol ediyoruz
                eğer girilen isim listede yoksa false dönecek ve listeye eklenecek
                 */
                cleanList.add(trimName);
            }

        }


        this.authors = cleanList;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        if(genre == null){
            throw new IllegalArgumentException("Kitap türü boş olamaz.");
        }
        this.genre = genre;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        if(publicationYear<1400 || publicationYear> LocalDate.now().getYear()){
            throw new IllegalArgumentException("Yayın yılı 1400 ile mevcut yıl arasında olmalıdır.");
        }
        this.publicationYear = publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        if (isbn == null) {
            throw new IllegalArgumentException("ISBN numarası boş olamaz.");
        }

        // 1) normalize: tireleri kaldır, büyük harfe çek
        String normalized = isbn.replace("-", "").toUpperCase();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException("ISBN numarası boş olamaz.");
        }

        // 2) uzunluğa göre doğru kuralı uygula
        if (normalized.length() == 10) {
            if (!isValidIsbn10(normalized)) {
                /*
                isValidIsbn10(normalized) → Bu bir kontrol metodu.
                İçinde ISBN-10 kurallarını (ilk 9 karakter rakam, son karakter rakam veya X, checksum algoritması vb.)
                kontrol ediyor.
                Senin isValidIsbn10 metodunda şöyle bir algoritma var (ben sana formülü değil mantığını anlatacağım):

                İlk 9 rakam sırayla belli katsayılarla çarpılır (10, 9, 8 … 2).
                Bunların toplamı alınır.
                Son rakam (10. hane) bu toplamı 11’e tam bölünebilir hale getirmek için seçilir.
                Eğer 10 çıkarsa, özel bir kural olarak X harfi yazılır (çünkü X = 10 demek).
                Yani son hane, bütün numaranın matematiksel doğru olmasını sağlayan bir kontrol mekanizmasıdır.
                Aşağıda söz konusu metot tanımlanmıştır
                 */
                throw new IllegalArgumentException("Geçersiz ISBN-10 (checksum hatası veya biçim hatası).");
            }
            this.isbn = normalized;
        } else if (normalized.length() == 13) {
            if (!isValidIsbn13(normalized)) {
                throw new IllegalArgumentException("Geçersiz ISBN-13 (checksum hatası veya biçim hatası).");
            }
            this.isbn = normalized;
        } else {
            throw new IllegalArgumentException("ISBN numarası 10 veya 13 karakter uzunluğunda olmalıdır.");
        }
    }

    // --- Yardımcılar ---

    private static boolean isValidIsbn10(String s) {
        // İlk 9’u rakam olmalı; son karakter 0-9 veya 'X'
        for (int i = 0; i < 9; i++) { // ilk 9 karakteri kontrol ediyoruz
            if (!Character.isDigit(s.charAt(i))) return false;
            /*
            s.charAt(i)
            → String s’in i numaralı karakterini alır.
            Örneğin s = "12345", s.charAt(2) → '3'.

            Character.isDigit(...)
            → Java’nın hazır (built-in) bir metodu.
            Görevi: Verilen karakter rakam mı (0–9) diye kontrol etmek.

            Eğer rakamsa → true

            Eğer değilse → false
             */
        }
        char last = s.charAt(9);// 10. karakteri alıyoruz. 10. karakterin index numarası 9
        if (!(Character.isDigit(last) || last == 'X')) return false; // last değişkenindeki karakter ya (0-9) olmalı ya da 'X' olöalı

        //hesaplama
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            int weight = 10 - i; // kat sayı
            int val;
            char c = s.charAt(i);
            if (i == 9 && c == 'X') {
                val = 10;
            } else {
                if (!Character.isDigit(c)) return false;
                val = c - '0'; //ASCII değerine göre işlem yapıyoruz
            }
            sum += weight * val;//ilk karakter 10 ile çarpılı daha sonra 9,8, ...
        }
        return sum % 11 == 0;
    }

    private static boolean isValidIsbn13(String s) {
        // Tümü rakam olmalı
        for (int i = 0; i < 13; i++) {
            if (!Character.isDigit(s.charAt(i))) return false;
        }
        int sum = 0;
        for (int i = 0; i < 13; i++) {
            int digit = s.charAt(i) - '0';//ASCII kodunu sayıya çeviriyoruz
            int weight = (i % 2 == 0) ? 1 : 3;
            /*
            Burada ağırlık (katsayı) belirleniyor:

            Çift indexlerde (0, 2, 4, …) → katsayı 1

            Tek indexlerde (1, 3, 5, …) → katsayı 3

            Yani rakamlar sırayla 1 ve 3 ile çarpılır: 1,3,1,3,1,3…
             */
            sum += weight * digit;
        }
        return sum % 10 == 0;
    }



    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if(stock<0){
            throw new IllegalArgumentException("Stok adedi negatif olamaz.");
        }
        this.stock=stock;
    }

    public boolean isActive() {
        return stock > 0;
    }

}
