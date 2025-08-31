package Library_Management_System;

public class App {

    private Library library = new Library();
    private ConsoleUI ui = new ConsoleUI();
    private Storage storage = new Storage();
    private boolean running = true;

    public void run() {
        while (running) {
            try {
                ui.showMenu();
                int choice = ui.promptMenuChoice();
                handleChoice(choice);
            } catch (IllegalArgumentException e) {
                System.out.println("Girdi Hatası: " + e.getMessage());
            } catch (IllegalStateException e) {
                System.out.println("İş Kuralı Hatası: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Beklenmeyen Hata: " + e.getMessage());
            }
        }
    }

    private void handleChoice(int choice) {
        switch (choice) {
            case 0 -> {
                System.out.println("Programdan çıkılıyor...");
                running = false;
            }
            case 1 -> ui.addBookFlow(library);
            case 2 -> library.bookList();
            case 3 -> ui.searchBookFlow(library);
            case 4 -> ui.addMemberFlow(library);
            case 5 -> library.listMember();
            case 6 -> ui.searchMemberFlow(library);
            case 7 -> ui.borrowBookFlow(library);
            case 8 -> ui.returnBookFlow(library);
            case 9 -> ui.listLoansFlow(library);
            case 10 -> ui.setDailyRateFlow(library);
            case 11 -> ui.saveFlow(library, storage);
            case 12 -> ui.loadFlow(library, storage);
            default -> System.out.println("Geçersiz seçim, tekrar deneyin.");
        }
    }

    public static void main(String[] args) {
        new App().run();
    }
}
