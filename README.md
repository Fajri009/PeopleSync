# PeopleSync App

PeopleSync adalah aplikasi Android sederhana untuk mengelola data user (nama, email, alamat, kota, gender, dll).  
Aplikasi ini mendukung fitur **fetch data dari API**, **offline mode menggunakan Local Database**, serta fitur **filter, sort, dan search**.

---

# 📱 Cara Penggunaan Aplikasi

1. **Halaman Home**
   - Data user akan otomatis ditampilkan dari database lokal
   - Jika pertama kali membuka aplikasi, data akan diambil dari API lalu disimpan ke database lokal

2. **Search**
   - Ketik nama atau email untuk mencari user secara realtime

3. **Sort**
   - Klik tombol sort untuk mengubah urutan data:
     - Default (tanpa sorting)
     - Ascending (A-Z)
     - Descending (Z-A)

4. **Filter**
   - Filter berdasarkan:
     - Kota
     - Jenis kelamin

5. **Tambah User**
   - Klik tombol (+) di kanan bawah
   - Isi form lalu submit
   - Data akan dikirim ke API

6. **Pull to Refresh**
   - Swipe ke bawah untuk sinkronisasi ulang data dari server
  
---

# 🧰 Teknologi yang Digunakan

- Kotlin
- Jetpack Compose (UI)
- MVVM Architecture
- Hilt (Dependency Injection)
- Retrofit (Networking / API)
- Room Database (Local Storage)
- Moshi (JSON Converter)
- Kotlin Coroutines & Flow (Asynchronous & reactive stream)
- Material 3 Design Components
- Navigation Compose

---

# 🎯 Kenapa Tampilan & Interaksi Dibuat Seperti Ini

1. **Offline First Experience**
   - Aplikasi tetap bisa digunakan tanpa internet karena data disimpan di database lokal
   - UI selalu membaca dari database lokal agar lebih cepat dan stabil

2. **UX Sederhana & Intuitif**
   - Search langsung real-time tanpa tombol tambahan
   - Sort toggle (Default → Asc → Desc) agar tidak membingungkan user
   - Filter bottom sheet agar tidak mengganggu layout utama
   - Floating Action Button digunakan untuk aksi tambah data user

3. **Pull to Refresh**
   - Memberi kontrol kepada user untuk sinkronisasi manual dengan server

4. **Clean Architecture (MVVM)**
   - UI (Compose)
   - ViewModel (logic)
   - Repository (data layer)
