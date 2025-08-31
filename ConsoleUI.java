package Library_Management_System;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ConsoleUI {

    private Scanner scanner = new Scanner(System.in);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public void showMenu() {
        System.out.println("""
                
                --- Kütüphane Menü ---
                1. Kitap ekle
                2. Kitapları listele
                3. Kitap ara (başlık)
                4. Üye ekle
                5. Üyeleri listele
                6. Üye ara (isim)
                7. Ödünç ver
                8. İade al
                9. Kayıtları listele
                10. Günlük ceza oranı ayarla
                11. Kaydet
                12. Yükle
                0. Çıkış
                """);
    }

    public int promptMenuChoice() {
        System.out.print("Seçiminiz: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice < 0 || choice > 12) {
                throw new IllegalArgumentException("Seçim 0–12 arasında olmalıdır.");
            }
            return choice;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Sayısal bir değer girilmelidir.");
        }
    }

    public long promptLong(String label) {
        while (true) {
            try {
                System.out.print(label + ": ");
                long val = Long.parseLong(scanner.nextLine().trim());
                if (val <= 0) throw new IllegalArgumentException("Pozitif olmalı");
                return val;
            } catch (Exception e) {
                System.out.println("Hata: " + e.getMessage());
            }
        }
    }

    public int promptInt(String label, int min) {
        while (true) {
            try {
                System.out.print(label + ": ");
                int val = Integer.parseInt(scanner.nextLine().trim());
                if (val < min) throw new IllegalArgumentException("En az " + min);
                return val;
            } catch (Exception e) {
                System.out.println("Hata: " + e.getMessage());
            }
        }
    }

    public double promptDouble(String label) {
        while (true) {
            try {
                System.out.print(label + ": ");
                double val = Double.parseDouble(scanner.nextLine().trim());
                if (val < 0) throw new IllegalArgumentException("Negatif olamaz");
                return val;
            } catch (Exception e) {
                System.out.println("Hata: " + e.getMessage());
            }
        }
    }

    public String promptStringNonBlank(String label) {
        while (true) {
            System.out.print(label + ": ");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("Boş olamaz!");
        }
    }

    public LocalDate promptDate(String label) {
        while (true) {
            try {
                System.out.print(label + " (gg.AA.yyyy): ");
                return LocalDate.parse(scanner.nextLine().trim(), formatter);
            } catch (Exception e) {
                System.out.println("Tarih formatı hatalı.");
            }
        }
    }

    public Genre promptGenre() {
        while (true) {
            try {
                System.out.print("Tür (ROMAN/COCUK/BILIM/TARIH/FANTASTIK): ");
                return Genre.valueOf(scanner.nextLine().trim().toUpperCase());
            } catch (Exception e) {
                System.out.println("Geçersiz tür.");
            }
        }
    }

    public List<String> promptAuthors() {
        while (true) {
            System.out.print("Yazar(lar) (virgülle ayır): ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("En az bir yazar girilmeli.");
                continue;
            }
            List<String> authors = new ArrayList<>();
            for (String a : input.split(",")) {
                String trimmed = a.trim();
                if (!trimmed.isEmpty() && !authors.contains(trimmed.toLowerCase())) {
                    authors.add(trimmed);
                }
            }
            if (!authors.isEmpty()) return authors;
        }
    }

    // --- Akışlar ---
    public void addBookFlow(Library lib) {
        long id = promptLong("Kitap ID");
        String title = promptStringNonBlank("Başlık");
        List<String> authors = promptAuthors();
        Genre genre = promptGenre();
        int year = promptInt("Yayın yılı", 1400);
        String isbn = promptStringNonBlank("ISBN");
        int stock = promptInt("Stok", 0);
        lib.add(new Book(id, title, authors, genre, year, isbn, stock));
    }

    public void searchBookFlow(Library lib) {
        String title = promptStringNonBlank("Aranacak başlık");
        lib.findBookByTitle(title);
    }

    public void addMemberFlow(Library lib) {
        long id = promptLong("Üye ID");
        String name = promptStringNonBlank("Ad");
        String surname = promptStringNonBlank("Soyad");
        String email = promptStringNonBlank("E-posta");
        String phone = promptStringNonBlank("Telefon");
        lib.addMember(new Member(id, name, surname, email, phone, 0, 0));
    }

    public void searchMemberFlow(Library lib) {
        String name = promptStringNonBlank("Aranacak isim");
        lib.findMemberByName(name);
    }

    public void borrowBookFlow(Library lib) {
        long bookId = promptLong("Kitap ID");
        long memberId = promptLong("Üye ID");
        LocalDate borrowDate = promptDate("Ödünç tarihi");
        int days = promptInt("Kaç gün", 1);
        lib.borrowBook(bookId, memberId, borrowDate, days);
    }

    public void returnBookFlow(Library lib) {
        long bookId = promptLong("Kitap ID");
        long memberId = promptLong("Üye ID");
        LocalDate returnDate = promptDate("İade tarihi");
        lib.returnBook(bookId, memberId, returnDate);
    }

    public void listLoansFlow(Library lib) {
        String filter = promptStringNonBlank("Filtre (ALL/OPEN/OVERDUE)");
        lib.listLoans(filter);
    }

    public void setDailyRateFlow(Library lib) {
        double rate = promptDouble("Yeni günlük ceza oranı");
        lib.setDailyRate(rate);
        System.out.println("Yeni oran: " + rate);
    }

    public void saveFlow(Library lib, Storage storage) {
        String path = promptStringNonBlank("Dosya adı");
        storage.saveToFile(lib, path);
    }

    public void loadFlow(Library lib, Storage storage) {
        String path = promptStringNonBlank("Dosya adı");
        storage.loadFromFile(lib, path);
    }
}
