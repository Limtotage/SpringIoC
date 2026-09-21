# 🛒 E-Ticaret Sitesi

Spring Boot ve Angular kullanılarak geliştirilmiş full-stack bir e-ticaret uygulamasıdır. Projede kullanıcı, ürün, kategori, sepet ve rol tabanlı yetkilendirme gibi temel e-ticaret özellikleri bulunmaktadır.

## 🚀 Kullanılan Teknolojiler

### Backend

* Java
* Spring Boot
* Spring Data JPA
* Spring Security
* JWT Authentication
* Hibernate
* Maven
* PostgreSQL
* Docker

### Frontend

* Angular
* TypeScript
* HTML
* SCSS
* npm

## 📁 Proje Yapısı

```text
E-Ticaret/
│
├── E-Ticaret-Backend/
│   ├── src/
│   ├── pom.xml
│   ├── mvnw
│   └── ...
│
└── AdminPanel/
    ├── src/
    ├── package.json
    └── ...
```

## ⚙️ Gereksinimler

Projeyi çalıştırmadan önce aşağıdaki araçların sistemde kurulu olması gerekir:

* Java
* Node.js
* npm
* Docker
* PostgreSQL / Docker üzerinden PostgreSQL

Kurulu sürümleri kontrol etmek için:

```bash
java -version
node -v
npm -v
docker --version
```

---

# ▶️ Projeyi Çalıştırma

Proje iki ayrı bölümden oluşmaktadır:

* **Backend:** Spring Boot
* **Frontend:** Angular

Backend ve frontend **ayrı terminallerde** çalıştırılmalıdır.

## 1. Backend'i Başlatma

Öncelikle backend klasörüne geçin:

```bash
cd E-Ticaret-Backend
```

Ardından Spring Boot uygulamasını Maven Wrapper üzerinden başlatın:

```bash
./mvnw spring-boot:run
```

Backend başarıyla başladıktan sonra Spring Boot uygulaması varsayılan olarak:

```text
http://localhost:8080
```

adresinde çalışır.

> Linux/macOS sistemlerde `./mvnw` çalışmazsa dosyaya çalıştırma izni vermek için:

```bash
chmod +x mvnw
```

ardından tekrar:

```bash
./mvnw spring-boot:run
```

komutu çalıştırılabilir.

---

## 2. Frontend'i Başlatma

Backend çalışırken **yeni bir terminal** açın ve frontend klasörüne geçin:

```bash
cd AdminPanel
```

Gerekli npm paketleri daha önce yüklenmediyse:

```bash
npm install
```

Ardından Angular uygulamasını başlatın:

```bash
npm start
```

Frontend genellikle:

```text
http://localhost:4200
```

adresinde açılır.

---

# 🔐 Kullanıcı Rolleri

Uygulamada rol tabanlı yetkilendirme bulunmaktadır.

Temel roller:

* **ADMIN** — Yönetim işlemleri
* **CUSTOMER** — Müşteri işlemleri
* **SELLER** — Satıcı işlemleri

Kullanıcının sahip olduğu role göre erişebileceği işlemler değişmektedir.

## 🛍️ Temel Özellikler

### Kullanıcı Yönetimi

* Kullanıcı kayıt ve giriş işlemleri
* JWT tabanlı kimlik doğrulama
* Rol tabanlı yetkilendirme
* Admin, Customer ve Seller rolleri

### Ürün Yönetimi

* Ürün ekleme
* Ürün listeleme
* Ürün güncelleme
* Ürün silme
* Ürün stok yönetimi
* Kategori bazlı ürün işlemleri

### Kategori Yönetimi

* Kategori oluşturma
* Kategori listeleme
* Kategori onay işlemleri
* Onaylanmış kategorilerin satıcılara sunulması

### Sepet

* Ürünleri sepete ekleme
* Sepetten ürün çıkarma
* Sepet ürünlerinin yönetimi
* Stok kontrolü

### Güvenlik

* Spring Security
* JWT Authentication
* Role-based authorization
* Yetkisiz endpoint erişiminin engellenmesi

---

# 🗄️ Veritabanı

Proje PostgreSQL kullanmaktadır.

Docker ile PostgreSQL çalıştırılması gerekiyorsa backend içerisindeki Docker yapılandırması kullanılabilir.

Örnek bağlantı bilgileri:

```text
Host: localhost
Port: 5432
Database: StoreDB
Username: myuser
Password: secret
```

Veritabanını incelemek veya yönetmek için **DBeaver** gibi bir PostgreSQL istemcisi kullanılabilir.

---

# 🔄 Çalışma Sırası

Projeyi çalıştırırken önerilen sıra:

```text
1. Docker / PostgreSQL
        ↓
2. E-Ticaret-Backend
        ↓
3. ./mvnw spring-boot:run
        ↓
4. Yeni terminal
        ↓
5. Frontend klasörü
        ↓
6. npm start
        ↓
7. http://localhost:4200
```

Backend'in çalışıyor olması frontend'in API isteklerini gerçekleştirebilmesi için gereklidir.

---

# 🛠️ Geliştirme

Backend:

```bash
cd E-Ticaret-Backend
./mvnw spring-boot:run
```

Frontend:

```bash
cd AdminPanel
npm start
```

Frontend geliştirme sırasında Angular değişiklikleri otomatik olarak algılar ve uygulamayı yeniden derler.

---

## 📌 Not

Bu proje eğitim ve geliştirme amacıyla oluşturulmuş full-stack bir e-ticaret uygulamasıdır. Backend ve frontend ayrı uygulamalar olarak çalışmaktadır.

**Backend:** Spring Boot
**Frontend:** Angular
**Database:** PostgreSQL

