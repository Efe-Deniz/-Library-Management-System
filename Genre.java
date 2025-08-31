package Library_Management_System;

public enum Genre {

    ROMAN("Roman"),
    COCUK("Çocuk"),
    BILIM("Bilim"),
    TARIH("Tarih"),
    FANTASTIK("Fantastik");

    private final String displayName;

    Genre(String displayName) {
        this.displayName = displayName;
    }
}
