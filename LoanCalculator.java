package Library_Management_System;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LoanCalculator {

    public int calculateLateDays(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("Kayıt bulunmadı");
        }
        if (loan.getDueDate() == null) {
            throw new IllegalArgumentException("Teslim tarihi olmadan hesaplama yapılmaz");
        }

        LocalDate ref = (loan.getReturnDate() == null) ? LocalDate.now() : loan.getReturnDate();
        long diff = ChronoUnit.DAYS.between(loan.getDueDate(), ref);
        return (diff > 0) ? Math.toIntExact(diff) : 0;
        /*
        -> LocalDate ref = (loan.getReturnDate() == null) ? LocalDate.now() : loan.getReturnDate();
        eğer kitabın iade tarihi yoksa yani kitap hala kütüphanede değilse bu günün tarihi kullanılır
        eğer kitap iade edilmiş ise ozaman iade tarihi kullanılır
        ------------------------------------------------------------------------------------------------
        ->long diff = ChronoUnit.DAYS.between(loan.getDueDate(), ref);
        Burada Java’nın Date/Time API’si kullanılıyor.
        ChronoUnit.DAYS.between(tarih1, tarih2) → iki tarih arasındaki gün farkını hesaplar.
        loan.getDueDate() → kitabın teslim edilmesi gereken tarih.
        ref → az önce seçtiğimiz tarih (ya bugün ya da iade günü).
        Sonuç: diff, kitabın kaç gün geç kaldığını (veya erken iade edilip edilmediğini) gösterir.
        Eğer ref dueDate’ten sonra ise → pozitif sayı çıkar (gecikme günleri).
        Eğer önceyse → negatif çıkar (ama birazdan bu düzeltiliyor).

         */
    }

    public double calculatePenalty(Loan loan, double dailyRate) {
        if (loan == null) {
            throw new IllegalArgumentException("Geçerli bir ödünç kaydı gerekiyor");
        }
        if (dailyRate < 0) {
            throw new IllegalArgumentException("Günlük ceza oranı negatif olamaz");
        }
        if (loan.getDueDate() == null) {
            throw new IllegalArgumentException("Teslim tarihi olmadan gecikme hesaplanamaz");
        }

        int lateDays = calculateLateDays(loan);
        return (lateDays <= 0) ? 0.0 : lateDays * dailyRate;
    }
}
