# 🌬️ Air Quality Monitoring System - IoT & Android App
**📍 Studi Kasus: PT. South Pacific Viscose**

## 📖 Abstrak

Pencemaran udara merupakan permasalahan lingkungan yang berdampak signifikan terhadap kesehatan manusia, khususnya dalam meningkatkan risiko penyakit pernapasan. Proyek ini bertujuan untuk merancang dan mengimplementasikan **sistem pemantauan kualitas udara berbasis Internet of Things (IoT)** yang menyajikan data **secara real-time** dan divisualisasikan dalam **aplikasi Android**.

Sistem ini memanfaatkan sensor **MQ135** dan **PMS5003** yang terhubung dengan **ESP32**, untuk mendeteksi berbagai parameter udara: **PM2.5**, **PM10**, **NH₃**, **CO₂**, dan **NOₓ**. Data yang dikumpulkan dikirim melalui jaringan WiFi ke **server backend** yang dibangun menggunakan **Express.js** dan **REST API**, lalu ditampilkan dalam aplikasi Android menggunakan **Retrofit** dan **Room Database**.

Pengujian dilakukan di **PT. South Pacific Viscose** dan menunjukkan bahwa sistem mampu memantau serta menampilkan data kualitas udara dengan **akurasi tinggi** dan **responsivitas real-time**.

---

## 🚀 Fitur Utama

- 🔴 Pemantauan PM2.5, PM10, NOx, NH₃, CO₂
- 📡 Pengiriman data real-time via WiFi (ESP32)
- 📲 Visualisasi data di aplikasi Android (Jetpack Compose)
- 🧠 Backend dengan Express.js & REST API
- 🗃️ Penyimpanan lokal dengan Room Database
- 📊 Riwayat & grafik kualitas udara
- 🔔 Notifikasi kualitas udara buruk (opsional)

---

## 🛠️ Teknologi yang Digunakan

| Komponen     | Teknologi / Alat            |
|--------------|-----------------------------|
| Perangkat Keras | ESP32, MQ135, PMS5003        |
| Backend      | Express.js, Node.js, REST API |
| Frontend     | Android (Kotlin, Jetpack Compose, Retrofit, Room) |
| Database     | SQLite (via Room)             |
| Komunikasi   | WiFi                          |

---

## 🧪 Studi Kasus
**PT. South Pacific Viscose** digunakan sebagai lokasi uji coba implementasi sistem. Perangkat berhasil memantau kualitas udara di lingkungan industri dan memberikan insight penting terkait kondisi udara sekitar pabrik.

---

## 🔍 Kata Kunci
`Internet of Things` • `Kualitas Udara` • `PMS5003` • `Android` • `MQ135`

---

## 👩‍💻 Pengembang
- Nama: **Akbar Nugraha Dimyati**
- Institusi: Universitas Singaperbangsa Karawang
- Tahun: 2025

---

## 📂 Lisensi
Proyek ini dikembangkan untuk tujuan pendidikan dan penelitian. Hubungi pengembang untuk penggunaan lebih lanjut.

---

## ✨ Kontribusi
Pull request, saran, dan masukan sangat diterima untuk pengembangan lebih lanjut!

