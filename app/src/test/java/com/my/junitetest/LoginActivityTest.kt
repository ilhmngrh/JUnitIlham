package com.my.junitetest

// Import kelas-kelas dan komponen yang diperlukan untuk pengujian
import android.content.Intent // Digunakan untuk mengelola intent yang digunakan dalam pengujian
import com.google.firebase.auth.FirebaseAuth // Digunakan untuk melakukan autentikasi dengan Firebase
import com.google.firebase.auth.FirebaseAuthInvalidUserException // Digunakan untuk menangani pengecualian autentikasi Firebase
import org.junit.Before // Digunakan untuk menandai metode setup yang harus dijalankan sebelum pengujian dimulai
import org.junit.Test // Digunakan untuk menandai metode pengujian
import org.junit.runner.RunWith // Digunakan untuk menentukan runner (pemrograman uji) yang digunakan
import org.mockito.Mock // Digunakan untuk membuat objek tiruan (Mock) untuk pengujian
import org.mockito.MockitoAnnotations // Digunakan untuk menginisialisasi objek-objek tiruan (Mock)
import org.mockito.Mockito.`when` // Digunakan untuk mengkonfigurasi perilaku objek tiruan (Mock)
import org.mockito.Mockito.verify // Digunakan untuk memverifikasi panggilan metode pada objek tiruan (Mock)
import com.my.junitetest.LoginActivity // Mengimpor kelas LoginActivity yang akan diuji
import androidx.test.ext.junit.runners.AndroidJUnit4 // Digunakan untuk menjalankan pengujian dengan runner AndroidJUnit4(khusus test instrumental)
import org.junit.runners.JUnit4 // Digunakan untuk menjalankan pengujian dengan runner JUnit4
import org.mockito.Matchers.eq // Digunakan untuk melakukan pemeriksaan yang lebih rinci terhadap objek-objek tiruan (Mock)

@RunWith(JUnit4::class) //test unit menggunakan JUnit4
class LoginActivityTest {
    @Mock
    private lateinit var auth: FirebaseAuth // Objek tiruan (Mock) untuk FirebaseAuth
    @Mock
    private lateinit var intent: Intent // Objek tiruan (Mock) untuk Intent

    private lateinit var activity: LoginActivity // Objek aktivitas yang akan diuji

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this) // Menginisialisasi objek-objek tiruan (Mock)
        activity = LoginActivity() // Membuat objek aktivitas LoginActivity
    }

    @Test
    fun testLoginButtonClicked_Success() {
        // Simulasikan login yang berhasil
        `when`(auth.signInWithEmailAndPassword("", "")).thenReturn(null)
        `when`(auth.currentUser).thenReturn(null)

        // Panggil tindakan yang terjadi saat tombol login diklik
        activity.onLoginButtonClick("example@email.com", "password123")

        // Verifikasi bahwa intent ke DashboardActivity telah dibuat
        verify(intent).putExtra("message", "Pesan apa pun yang ingin Anda kirim")
        verify(activity).startActivity(intent)
    }

    @Test
    fun testLoginButtonClicked_Failure() {
        // Simulasikan login yang gagal
        val exception = FirebaseAuthInvalidUserException("error code", "error message")
        `when`(auth.signInWithEmailAndPassword("", "")).thenThrow(exception)

        // Panggil tindakan yang terjadi saat tombol login diklik
        activity.onLoginButtonClick("example@email.com", "password123")

        // Verifikasi bahwa tampilan pesan kesalahan tampil di layar
        verify(activity).showErrorMessage("Email tidak ditemukan atau tidak terdaftar")
    }
}
