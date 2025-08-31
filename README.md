# -Library-Management-System
Bu proje, nesne yönelimli programlama (OOP) prensiplerini öğrenmek ve uygulamak amacıyla geliştirilmiştir.

🚀 Özellikler

Kitap ekleme, listeleme, arama (ID, isim, yazar, ISBN)

Üye ekleme, listeleme, arama (ID, isim, email)

Kitap ödünç alma (Loan oluşturma)

Kitap iade etme

Gecikme kontrolü ve ceza hesaplama

JSON/CSV dosyaya veri kaydetme ve geri yükleme (opsiyonel geliştirme)

🛠️ Kullanılan Teknolojiler

Java 17+

Collections Framework → ArrayList, HashMap vb.

LocalDate → Ödünç ve teslim tarihleri

Exception Handling → Hatalı girişlerin kontrolü

(Opsiyonel) JSON / CSV dosya işlemleri

Library_Management_System/
│
├── App.java            # Ana uygulama çalıştırıcısı
├── Library.java        # Tüm işlemlerin yönetildiği merkez sınıf
├── ConsoleUI.java      # Kullanıcı ile etkileşim (menü, input)
├── Storage.java        # Dosya okuma/yazma işlemleri
│
├── models/
│   ├── Book.java       # Kitap bilgileri (id, isim, yazar, stok)
│   ├── Member.java     # Üye bilgileri (id, isim, email, telefon)
│   ├── Loan.java       # Ödünç kayıtları (kitap, üye, tarih)
│   └── Genre.java      # Enum: Kitap türleri
│
└── README.md           # Proje dökümantasyonu

📌 Gelecek Geliştirmeler

 Swing/JavaFX GUI desteği

 Veritabanı (JDBC, PostgreSQL) entegrasyonu

 Spring Boot ile REST API versiyonu

 Kullanıcı giriş/rol yönetimi (admin, user)

 Çoklu dil desteği

🧑‍🏫 Öğrenme Amaçları

Bu proje sayesinde şunları pratik ettim:

OOP prensipleri (Encapsulation, Inheritance, Polymorphism)

Koleksiyonlar ve generics

Hata yönetimi (try-catch, exception fırlatma)

Tarih ve zaman API’si (LocalDate)

Temiz kod yazma ve sınıflar arası sorumluluk dağılımı
