package Library_Management_System;

import java.text.Collator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;



public class Library {

    private List<Book> bookList = new ArrayList<>();
    private List<Member> memberList = new ArrayList<>();
    private List<Loan> loanList = new ArrayList<>();

    private double dailyRate = 0.50;

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public List<Member> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }

    public List<Loan> getLoanList() {
        return loanList;
    }

    public void setLoanList(List<Loan> loanList) {
        this.loanList = loanList;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    private void printBookRow(Book book) {
        String authors = (book.getAuthors() == null || book.getAuthors().isEmpty())
                ? "yazar yok"
                : String.join(", ", book.getAuthors());

        if (authors.length() > 20) {
            authors = authors.substring(0, 17) + "...";
        }

        System.out.printf("%-5d %-25s %-20s %-10s %-6d %-15s %-6d %-10s\n",
                book.getId(), book.getTitle(), authors, book.getGenre(),
                book.getPublicationYear(), book.getIsbn(), book.getStock(),
                book.isActive() ? "Aktif" : "Pasif");
    }

    private void printMemberRow(Member member){
        System.out.printf("%-5d %-15s %-15s %-25s %-15s %-10.2f %-10d\n",
                member.getId(),
                member.getName(),
                member.getSurname(),
                member.getEmail(),
                member.getPhone(),
                member.getPenaltyAmount(),
                member.getBorrowedCount()
        );
    }

    public void printHeader(){
        System.out.printf("%-5s %-25s %-20s %-10s %-6s %-15s %-6s %-10s\n",
                "ID", "Başlık", "Yazar(lar)", "Tür", "Yıl", "ISBN", "Stok", "Durum");
        System.out.println("-------------------------------------------------------------------------------------------");
    }

    public void printMemberHeader(){
        System.out.printf("%-5s %-15s %-15s %-25s %-15s %-10s %-10s\n",
                "ID", "Ad", "Soyad", "E-Posta", "Telefon", "Ceza", "Ödünç");

        System.out.println("--------------------------------------------------------------------------------------------");
    }

    public boolean  add(Book book){
        for(Book b : bookList){
            if(b.getIsbn().equals(book.getIsbn())){
                throw new IllegalArgumentException("Bu ISBN zaten kayıtlı");
            }
        }
        bookList.add(book);
        System.out.println("Kitap başarılı şekilde eklendi");
        return true;

    }

    public void bookList() {
        if (bookList.isEmpty()) {
            System.out.println("Kütüphanede henüz kitap yok.");
            return;
        }

        List<Book> sorted = new ArrayList<>(bookList);


        /*
         Kitapları veya üyeleri Türkçe alfabeye göre sıralamak
         Collator: Java’da metinleri alfabetik olarak karşılaştırmak için kullanılan özel bir sınıf.
         İşte Collator.getInstance(new Locale("tr", "TR")) derken: türkçe dil kurallarına uygun bir sıralama yapıyoruz
         ------------------------------------------------------------------------------------------------------------
         collator.setStrength(Collator.PRIMARY);

        setStrength → Collator’un karşılaştırma duyarlılığını (sensitivity) ayarlar.
        PRIMARY seviyesi:
        Büyük/küçük harf farkını önemsemez.
        Aksanlı/aksan­sız farkını da görmez.
        Yani "a" = "A" = "â" kabul edilir.

        ---------------------------------------------------------------------------------------------------------------
        “Karşılaştırma duyarlılığı (sensitivity)” şu demek:
        Bir metin karşılaştırılırken veya sıralanırken hangi farkların önemli,
        hangi farkların önemsiz sayılacağını belirleyen kuraldır.
        Collator’da Duyarlılık (Strength) Seviyeleri
        1-PRIMARY
        Harfin temel şekline bakar.
        Büyük/küçük harf farkını görmez.
        Aksanları, şapkalı harfleri yok sayar.
        "a", "A", "â" hepsi aynı kabul edilir.

        2-SECONDARY
       Harfin temel şekline + aksan farkına bakar.
       "a" ile "â" farklı kabul edilir.
        Ama hala "a" ile "A" aynı kabul edilir.

       3- TERTIARY
        Harfin temel şekline, aksan farkına ve büyük/küçük harfe baka
        "a" ≠ "A"
        "a" ≠ "â"

        4-IDENTICAL
        En hassas seviye.

         */
        Collator collator = Collator.getInstance(new Locale("tr" , "TR"));
        collator.setStrength(Collator.PRIMARY);

        sorted.sort((b1, b2) -> {
            String t1 = b1.getTitle();
            String t2 = b2.getTitle();

            // 1. Null kontrolü
            if (t1 == null && t2 == null) {
                return Long.compare(b1.getId(), b2.getId()); // ikisi de null → id kıyasla
            }
            if (t1 == null) return 1;  // b1 null → sona
            if (t2 == null) return -1; // b2 null → sona

            // 2. Trim
            String trim1 = t1.trim();
            String trim2 = t2.trim();

            // 3. Boş string kontrolü
            if (trim1.isEmpty() && trim2.isEmpty()) {
                return Long.compare(b1.getId(), b2.getId());
            }
            if (trim1.isEmpty()) return 1;   // b1 boş → sona
            if (trim2.isEmpty()) return -1;  // b2 boş → sona

            // 4. Collator ile karşılaştır
            int cmp = collator.compare(trim1, trim2);
            if (cmp != 0) {
                return cmp; // farklıysa sonucu dön
            }

            // 5. Başlık eşitse id’ye bak
            return Long.compare(b1.getId(), b2.getId());
        });


        printHeader();

        // Kitaplar
        for (Book book : sorted) {
            printBookRow(book);
        }

        // Özet
        System.out.println("Toplam " + sorted.size() + " kitap listelendi.");
    }

    public Book findBookById(long id){
        if(bookList.isEmpty()){
            System.out.println("Kütüphanede henüz kitap yok");
            return null;
        }
        if(id<0){
            throw new IllegalArgumentException("Geçersiz ID");
        }

        for (Book book : bookList){
            if(book.getId() == id){
                printHeader();
                printBookRow(book);
                return book;
            }
        }
        System.out.println("Kitap bulunamadı (ID: \" + id + \")");
        return null;

    }

    public List<Book> findBookByTitle(String title){
        if(bookList.isEmpty()){
            System.out.println("Kütüphanede henüz kitap yok");
            return new ArrayList<>();
        }

        if (title == null || title.isBlank()){
            throw new IllegalArgumentException("Geçersiz başlık");
        }

        String searchTitle = title.trim().toLowerCase();
        List<Book> resultList = new ArrayList<>();
        for(Book book : bookList){
            if(book.getTitle() != null && book.getTitle().trim().toLowerCase().equals(searchTitle)){
                resultList.add(book);
                //Eğer kitabın başlığı boş değilse, boşlukları temizlendikten ve
                // küçük harfe çevrildikten sonra aramadaki kelimeye eşitse → o kitabı sonuç listesine ekle (resultList).
            }
        }
        if(resultList.isEmpty()){
            System.out.println("Bu başlığa ait kitap bulunamadı: " + title);
            return new ArrayList<>(); // null döndürmek NullPointerException hatası almamıza sebebp olabilir
            //-> for(Book b : findBookByTitle("Sefiller")) böyle bir döngüde mesela
        }else{
            System.out.println("Bulunan kitap(lar): " + resultList.size());

            for (Book b : resultList) {
                System.out.println("ID: " + b.getId() + ", Başlık: " + b.getTitle());
            }
        }

        printHeader();

        for (Book book : resultList) {
            printBookRow(book);
        }

        System.out.println("Toplam: " + resultList.size() + " sonuç listelendi. " + "Aranan: " + title);

        return resultList;
    }

    public boolean addMember(Member member){

        if(member == null){
            throw new IllegalArgumentException("Üye nesnesi null olamaz");
        }
        if(member.getId() <=0 ){
            throw new IllegalArgumentException("Üye ID'si pozitif olmalıdır");
        }
        if(member.getName() == null || member.getName().trim().isBlank()){
            throw new IllegalArgumentException("Üye adı boş olamaz");
        }
        if(member.getSurname() == null || member.getSurname().trim().isBlank()){
            throw new IllegalArgumentException("Soy isim boş olamaz");
        }
        if(member.getEmail() == null || member.getEmail().isBlank()){
            throw new IllegalArgumentException("E-posta adresi boş olamaz");
        }
        String candidateEmail = member.getEmail().trim().toLowerCase();

        for(Member member1 : memberList){
            String existing =(member1.getEmail() == null) ? "":
                    member1.getEmail().trim().toLowerCase();
            if(member1.getId() == member.getId()){
                throw new IllegalArgumentException("Bu ID zaten kayıtlı");
            }
            if(existing.equals(candidateEmail)){
                throw new IllegalArgumentException("Bu e-posta zaten kayıtlı");
            }
        }
        memberList.add(member);
        System.out.println("Üye eklendi. Ad-Soyad:  " + member.getName() + " " + member.getSurname() + " E-posta adresi: " + member.getEmail());
        return true;
    }

    public void listMember(){
        if(memberList.isEmpty()){
            System.out.println("Henüz üye yok");
            return;
        }

        List<Member> sortedMember = new ArrayList<>(memberList);
        Collator collator = Collator.getInstance(new Locale("tr","TR"));
        collator.setStrength(Collator.PRIMARY);

        sortedMember.sort((a1, a2) -> {
            String n1 = (a1.getName() == null) ? "" : a1.getName().trim();
            String n2 = (a2.getName() == null) ? "" : a2.getName().trim();

            int cmpName = collator.compare(n1, n2);
            if (cmpName != 0) return cmpName;

            String s1 = (a1.getSurname() == null) ? "" : a1.getSurname().trim();
            String s2 = (a2.getSurname() == null) ? "" : a2.getSurname().trim();

            int cmpSurname = collator.compare(s1, s2);
            if (cmpSurname != 0) return cmpSurname;

            return Long.compare(a1.getId(), a2.getId());
        });

        printMemberHeader();
        for(Member member : sortedMember){
            printMemberRow(member);
        }
        System.out.println("Toplam " + sortedMember.size() + " üye listelendi.");
    }

    public Member findMemberById(long id){
        if(id<=0){
            throw new IllegalArgumentException("ID numarası pozitif olmalıdır");
        }

        if(memberList.isEmpty()){
            System.out.println("Henüz ye yok");
        }

        for(Member member: memberList){
            if(member.getId() == id){
                printMemberHeader();
                printMemberRow(member);
                return member;

            }
        }
        System.out.println("Üye bulunamadı");
        return null;
    }

    public Member findMemberByEmail(String email){
        if(email == null || email.isBlank()){
            System.out.println("E-posta boş olamaz");
        }

        if(memberList.isEmpty()){
            System.out.println("Henüz üye yok");
        }

        String searchEmail = email.trim().toLowerCase();
        for(Member member : memberList){
            String existing = (member.getEmail() == null) ? "" :
                    member.getEmail().trim().toLowerCase();

            if(existing.equals(searchEmail)){
                printMemberHeader();
                printMemberRow(member);
                return member;
            }
        }
        System.out.println("Bu e-posta ile üye bulunamadı: " + email);
        return null;
    }
    public List<Member> findMemberByName(String name) {
        if (name == null || name.trim().isBlank()) {
            throw new IllegalArgumentException("Geçersiz isim");
        }

        if (memberList.isEmpty()) {
            System.out.println("Henüz üye yok");
            return new ArrayList<>();
        }

        String searchName = name.trim().toLowerCase();
        List<Member> resultList = new ArrayList<>();

        for (Member member : memberList) {
            if (member.getName() != null &&
                    member.getName().trim().toLowerCase().contains(searchName)) {
                resultList.add(member);
            }
        }

        if (resultList.isEmpty()) {
            System.out.println("Aranan isim için üye bulunamadı: " + name);
        } else {
            printMemberHeader();
            for (Member m : resultList) {
                printMemberRow(m);
            }
            System.out.println("Toplam " + resultList.size() +
                    " üye bulundu. Aranan: " + name);
        }

        return resultList;
    }
    public Loan borrowBook(long bookId, long memberId, LocalDate borrowDate, int days) {
        if (bookId <= 0) {
            throw new IllegalArgumentException("Kitap ID pozitif olmalıdır");
        }
        if (memberId <= 0) {
            throw new IllegalArgumentException("Üye ID'si pozitif olmalıdır");
        }
        if (days <= 0) {
            throw new IllegalArgumentException("Gün pozitif olmalıdır");
        }
        if (borrowDate == null || borrowDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Ödünç alma tarihi ileri bir tarih olamaz");
        }

        // Kitap bulma
        Book foundBook = null;
        for (Book book : bookList) {
            if (book.getId() == bookId) {
                foundBook = book;
                break;
            }
        }
        if (foundBook == null) {
            throw new IllegalArgumentException("Bu ID'ye kayıtlı kitap bulunmadı: " + bookId);
        }

        // Üye bulma
        Member foundMember = null;
        for (Member member : memberList) {
            if (member.getId() == memberId) {
                foundMember = member;
                break;
            }
        }
        if (foundMember == null) {
            throw new IllegalArgumentException("Bu ID'ye kayıtlı üye bulunamadı: " + memberId);
        }

        // Teslim tarihi
        LocalDate dueDate = borrowDate.plusDays(days); //.plusDays(days) → bu tarihin üzerine belirtilen gün sayısını ekler.
        //barrowDate = kitabın ödünç alındığı tarih
        if (dueDate.isBefore(borrowDate)) {
            throw new IllegalArgumentException("Hatalı teslim tarihi hesaplandı!");
        }


        for(Loan loan : loanList){
            if(loan.getBookId() == bookId && loan.getMemberId() == memberId && loan.getReturnDate() == null ){
                throw new IllegalStateException("Bu kitabı zaten ödünç aldınız (açık kayıt var)");
            }
        }

        // Stok kontrol
        if (foundBook.getStock() <= 0) {
            throw new IllegalStateException("Kitap stoğu kalmamış");
        }
        foundBook.setStock(foundBook.getStock() - 1);

        // Borrowed count kontrol
        if (foundMember.getBorrowedCount() >= 5) {
            throw new IllegalStateException("En fazla 5 kitap ödünç alınabilir");
        }
        foundMember.setBorrowedCount(foundMember.getBorrowedCount() + 1);

        // Ceza kontrol
        if (foundMember.getPenaltyAmount() > 0) {
            throw new IllegalStateException("Ceza puanınız sebebiyle ödünç kitap verilemez");
        }

        // ödünç alma kaydını saklamak için yeni Loan oluşturuyoruz
        Loan newLoan = new Loan(bookId, memberId, borrowDate, dueDate, null);
        loanList.add(newLoan);

        return newLoan;
    }

    public Loan returnBook(long bookId, long memberId, LocalDate returnDate) {
        Loan openLoan = null;
        Book foundBook = null;
        Member foundMember = null;

        // --- 1) Girdi kontrolleri ---
        if (bookId <= 0) {
            throw new IllegalArgumentException("Kitap ID pozitif olmalıdır");
        }
        if (memberId <= 0) {
            throw new IllegalArgumentException("Üye ID pozitif olmalıdır");
        }
        if (returnDate == null) {
            throw new IllegalArgumentException("Teslim tarihi boş olamaz");
        }
        if (returnDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Teslim tarihi ileri bir tarih olamaz");
        }

        // --- 2) Kitap bul ---
        for (Book book : bookList) {
            if (book.getId() == bookId) {
                foundBook = book;
                break;
            }
        }
        if (foundBook == null) {
            throw new IllegalArgumentException("Kitap bulunamadı");
        }

        // --- 3) Üye bul ---
        for (Member member : memberList) {
            if (member.getId() == memberId) {
                foundMember = member;
                break;
            }
        }
        if (foundMember == null) {
            throw new IllegalArgumentException("Üye bulunamadı");
        }

        // --- 4) Açık loan kontrolü ---
        //yani aynı üye aynı kitabı ödünç almışsa ve hâlâ geri getirmemişse yeni bir kayıt açmayı engellemek
        int counter = 0;
        for (Loan loan : loanList) {
            if (loan.getBookId() == bookId && loan.getMemberId() == memberId && loan.getReturnDate() == null) {
                openLoan = loan;
                counter++;
            }
        }
        if (counter == 0) {
            throw new IllegalStateException("Bu üyenin bu kitap için açık loan kaydı yok");
        }
        if (counter > 1) {
            throw new IllegalStateException("Bu üye/kitap için birden fazla açık kayıt var");
        }

        // --- 5) Tarih kontrolü --- barrowDate = ödünç alma tarihi
        LocalDate borrowDate = openLoan.getBorrowDate();
        if (returnDate.isBefore(borrowDate)) {
            throw new IllegalArgumentException("İade tarihi ödünç alma tarihinden önce olamaz");
        }

        // --- 6) Loan güncelle ---
        openLoan.setReturnDate(returnDate);

        // --- 7) Ceza hesaplama ---
        LoanCalculator loanCalculator = new LoanCalculator();
        int lateDays = loanCalculator.calculateLateDays(openLoan);
        double penalty = loanCalculator.calculatePenalty(openLoan, dailyRate);

        if (penalty > 0) {
            foundMember.setPenaltyAmount(foundMember.getPenaltyAmount() + penalty);
        }

        // --- 8) Stok ve borrowedCount güncelle ---
        foundBook.setStock(foundBook.getStock() + 1);
        if (foundMember.getBorrowedCount() < 1) {
            throw new IllegalStateException("Üyenin borrowedCount değeri zaten 0, azaltılamaz!");
        }
        foundMember.setBorrowedCount(foundMember.getBorrowedCount() - 1);

        // --- 9) Tablo formatlı çıktı ---
        String format = "%6d %-22s %8d %-18s %-10s %-10s %-10s %5d %6.2f %8.2f %8d %9d%n";

        String shortTitle = foundBook.getTitle().length() > 22
                ? foundBook.getTitle().substring(0, 19) + "..."
                : foundBook.getTitle();

        String fullName = foundMember.getName() + " " + foundMember.getSurname();
        String shortName = fullName.length() > 18
                ? fullName.substring(0, 15) + "..."
                : fullName;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String borrowStr = borrowDate.format(formatter);
        String dueStr = openLoan.getDueDate().format(formatter);
        String returnStr = (returnDate != null) ? returnDate.format(formatter) : "—";

        System.out.printf(format,
                foundBook.getId(),
                shortTitle,
                foundMember.getId(),
                shortName,
                borrowStr,
                dueStr,
                returnStr,
                lateDays,                // Late gün sayısı
                dailyRate,               // Günlük ceza oranı
                penalty,                 // Toplam ceza
                foundBook.getStock(),    // Güncel stok
                foundMember.getBorrowedCount() // Güncel borrowed
        );

        return openLoan;
    }

    public void listLoans(String filter) {
        if (filter == null || filter.isBlank()) {
            throw new IllegalArgumentException("Filtre boş olamaz (ALL/OPEN/OVERDUE)");
        }

        if (loanList.isEmpty()) {
            System.out.println("Henüz loan kaydı yok.");
            return;
        }

        String f = filter.trim().toUpperCase();
        List<Loan> filtered = new ArrayList<>();

        // kayıtlı tüm ödünç (loan) verilerini filtreleyerek kullanıcıya göstermek.
        LocalDate today = LocalDate.now();
        for (Loan loan : loanList) {
            switch (f) {
                case "ALL": //Tüm kayıtlar olduğu gibi filtered listesine ekleniyor.
                    filtered.add(loan);
                    break;
                case "OPEN"://Yalnızca henüz iade edilmemiş kayıtlar ekleniyor.
                    if (loan.getReturnDate() == null) {
                        filtered.add(loan);
                    }
                    break;
                case "OVERDUE"://gecikmiş kitaplar ekleniyor
                    if (loan.getReturnDate() == null && loan.getDueDate().isBefore(today)) {
                        filtered.add(loan);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Geçersiz filtre: " + filter + " (ALL/OPEN/OVERDUE)");
            }
        }

        if (filtered.isEmpty()) {
            System.out.println("Bu filtreye uygun loan kaydı yok: " + f);
            return;
        }

        // --- 2) Sırala (dueDate artan) ---
        filtered.sort(Comparator.comparing(Loan::getDueDate));

        // --- 3) Tablo başlığı ---
        System.out.printf("%6s %-22s %8s %-18s %-10s %-10s %-10s %5s %6s %8s %8s %9s%n",
                "BookID", "Title", "MemberID", "Name",
                "Borrow", "Due", "Return", "Late", "Rate",
                "Penalty", "Stock", "Borrowed");
        System.out.println("-------------------------------------------------------------------------------------------");

        // --- 4) Satırlar ---
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        for (Loan loan : filtered) {
            // kitap & üye bul
            Book foundBook = null;
            for (Book b : bookList) {
                if (b.getId() == loan.getBookId()) {
                    foundBook = b;
                    break;
                }
            }
            Member foundMember = null;
            for (Member m : memberList) {
                if (m.getId() == loan.getMemberId()) {
                    foundMember = m;
                    break;
                }
            }

            // güvenlik
            if (foundBook == null || foundMember == null) {
                continue; // bozuk kayıt varsa atla
            }

            // metin kısaltma
            String shortTitle = foundBook.getTitle().length() > 22
                    ? foundBook.getTitle().substring(0, 19) + "..."
                    : foundBook.getTitle();

            String fullName = foundMember.getName() + " " + foundMember.getSurname();
            String shortName = fullName.length() > 18
                    ? fullName.substring(0, 15) + "..."
                    : fullName;

            // tarih stringleri
            String borrowStr = loan.getBorrowDate().format(formatter);
            String dueStr = loan.getDueDate().format(formatter);
            String returnStr = (loan.getReturnDate() != null) ? loan.getReturnDate().format(formatter) : "—";

            // gecikme & ceza
            LoanCalculator calc = new LoanCalculator();
            int lateDays = calc.calculateLateDays(loan);
            double penalty = calc.calculatePenalty(loan, dailyRate);

            // satırı bas
            System.out.printf("%6d %-22s %8d %-18s %-10s %-10s %-10s %5d %6.2f %8.2f %8d %9d%n",
                    foundBook.getId(),
                    shortTitle,
                    foundMember.getId(),
                    shortName,
                    borrowStr,
                    dueStr,
                    returnStr,
                    lateDays,
                    dailyRate,
                    penalty,
                    foundBook.getStock(),
                    foundMember.getBorrowedCount());
        }

        // --- 5) Özet ---
        System.out.println("Toplam " + filtered.size() + " kayıt listelendi. (Filtre: " + f + ")");
    }


}
