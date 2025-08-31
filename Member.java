package Library_Management_System;


import java.util.regex.Pattern;

public class Member {
    private long id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private double penaltyAmount;
    private int borrowedCount; // şuan ödünç alınan kitap sayısı
    private static final Pattern EMAIL_PATTERN = Pattern.compile("[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");//regrex kullanımı

    public Member(long id, String name, String surname, String email, String phone, double penaltyAmount, int borrowedCount) {
     setId(id);
     setName(name);
     setSurname(surname);
     setEmail(email);
     setPhone(phone);
     setPenaltyAmount(penaltyAmount);
     setBorrowedCount(borrowedCount);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        if(id <= 0){
            throw new IllegalArgumentException("Üye kimliği pozitif olmalı.");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null ){
            throw new IllegalArgumentException("İsim boş olamaz!!");
        }
        String nameTrim = name.trim();
        if(nameTrim.isEmpty()){
            throw new IllegalArgumentException("İsim boş olamaz");
        }
        this.name = nameTrim;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        if(surname == null ){
            throw new IllegalArgumentException("Soyisim boş olamaz!!");
        }
        String surnameTrim = surname.trim();
        if(surnameTrim.isEmpty()){
            throw new IllegalArgumentException("Soyisim boş olamaz");
        }
        this.surname = surnameTrim;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email boş olamaz");
        }
        String emailTrim = email.trim().toLowerCase();
        if (emailTrim.isEmpty()) {
            throw new IllegalArgumentException("Email boş olamaz");
        }

        // 1) Kapı kontrolü: regex tam eşleşme
        boolean matches = EMAIL_PATTERN.matcher(emailTrim).matches();
        if (!matches) {
            throw new IllegalArgumentException("Geçersiz e-posta formatı");
        }

        // 2) Ancak regex geçtiyse parçala
        int atIndex = emailTrim.indexOf('@'); // verilen string içinde '@' ilk gördüğü indeksi bulur. yukarıda 81-82 de regrex kontrolü yapılmıştı
        String localPart = emailTrim.substring(0, atIndex); //string’in 0. indexten başlayıp @ işaretinin öncesine kadar olan kısmını alır
        String domain = emailTrim.substring(atIndex + 1);//substring(atIndex + 1) → @ işaretinden sonrasını alır.

        // Ek kurallar
        if (emailTrim.contains("..")) {
            throw new IllegalArgumentException("Email’de ardışık nokta kullanılamaz");
        }
        if (localPart.startsWith(".") || localPart.endsWith(".")) {
            throw new IllegalArgumentException("Yerel kısım nokta ile başlayamaz ya da bitemez");
        }

        String[] domainLabels = domain.split("\\.");
        for (String label : domainLabels) {
            if (label.isEmpty()) {
                throw new IllegalArgumentException("Domain etiketleri boş olamaz");
            }
            if (label.startsWith("-") || label.endsWith("-")) {
                throw new IllegalArgumentException("Domain etiketleri tire ile başlayamaz/bitemez");
            }
        }

        if (emailTrim.length() > 254) {
            throw new IllegalArgumentException("E-posta adresi 254 karakteri aşamaz.");
        }

        this.email = emailTrim;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if(phone == null){
            throw new IllegalArgumentException("Telefon numarası boş olamaz");
        }
        String phoneTrim = phone.trim();
        if(phoneTrim.isEmpty()){
            throw new IllegalArgumentException("Telefon numarası boş olamaz");
        }
        String replacePhone = phoneTrim.replaceAll("[^0-9]", "");
        if (replacePhone.length()<10 || replacePhone.length()>15){
            throw new IllegalArgumentException("Telefon numarası 10 ila 15 rakamadan oluşmalıdır");
        }



        this.phone = replacePhone;
    }

    public double getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(double penaltyAmount) {
        if(penaltyAmount<0){
            throw new IllegalArgumentException("Ceza miktarı sıfırdan küçük olamaz");
        }
        this.penaltyAmount = penaltyAmount;
    }

    public int getBorrowedCount() {
        return borrowedCount;
    }

    public void setBorrowedCount(int borrowedCount) {
        if(borrowedCount<0 || borrowedCount>5){
            throw new IllegalArgumentException("Ödünç alınan kitap sayısı negatif olamaz veya 5 ten fazla olmaz!!");
        }
        this.borrowedCount = borrowedCount;
    }
}
