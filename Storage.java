package Library_Management_System;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class Storage {

    private final ObjectMapper mapper;

    public Storage() {
        mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule()) // LocalDate desteği
                .enable(SerializationFeature.INDENT_OUTPUT)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public void saveToFile(Library lib, String path) {
        Path target = Path.of(path);
        Path tmp = Path.of(path + ".tmp");
        try {
            Snapshot snapshot = Snapshot.fromLibrary(lib);
            // önce geçici dosyaya yaz
            mapper.writeValue(tmp.toFile(), snapshot);
            // sonra atomik biçimde asıl dosyanın üstüne taşı
            Files.move(tmp, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            System.out.println("Kaydedildi: " + path);
        } catch (Exception e) {
            try {
                Files.deleteIfExists(tmp);
            } catch (Exception ignored) {}
            System.out.println("Kaydetme başarısız: " + e.getMessage());
            System.out.println("Geçici dosya temizlendi.");
        }
    }

    public void loadFromFile(Library lib, String path) {
        try {
            Snapshot snapshot = mapper.readValue(new File(path), Snapshot.class);

            // (İLERİ ADIM) Burada snapshot doğrulamaları yapılabilir:
            // - ID / email / ISBN benzersizlikleri
            // - Loan referansları (bookId, memberId) var mı
            // - Tarih kuralları vs.
            // Şimdilik doğrudan uygula.

            snapshot.applyTo(lib);

            System.out.println("Yüklendi: " + path + " — kitap: " + snapshot.books.size() +
                    ", üye: " + snapshot.members.size() +
                    ", loan: " + snapshot.loans.size());
        } catch (Exception e) {
            System.out.println("Yükleme başarısız: " + e.getMessage());
        }
    }

    // --- DTO / Snapshot ---
    static class Snapshot {
        public List<Book> books;
        public List<Member> members;
        public List<Loan> loans;
        public double dailyRate;

        static Snapshot fromLibrary(Library lib) {
            Snapshot snap = new Snapshot();
            // Library içindeki mevcut isimlerle (…List) eriş
            snap.books     = lib.getBookList();
            snap.members   = lib.getMemberList();
            snap.loans     = lib.getLoanList();
            snap.dailyRate = lib.getDailyRate();
            return snap;
        }

        void applyTo(Library lib) {
            // Library içindeki mevcut isimlerle (…List) set et
            lib.setBookList(books);
            lib.setMemberList(members);
            lib.setLoanList(loans);
            lib.setDailyRate(dailyRate);
        }
    }
}
