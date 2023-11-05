package com.my.junitetest

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.my.junitetest.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val emailEditText = binding.emailEditText
        val passwordEditText = binding.passwordEditText
        val registerButton = binding.registerButton
        val loginButton = binding.gotoLogin
        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Registrasi berhasil
                        val user: FirebaseUser? = auth.currentUser
                        val userId = user?.uid ?: ""

                        // Simpan data ke Firebase Database
                        val database = FirebaseDatabase.getInstance()
                        val reference = database.getReference("users").child(userId)
                        reference.setValue(User(email,password))
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Gagal registrasi. Pastikan email belum terdaftar.", Toast.LENGTH_SHORT).show()
                    }
                }
        }


        loginButton.setOnClickListener {
            // Tindakan ketika tombol "Register" diklik
            // Di sini kita akan mengarahkan pengguna kembali ke halaman login (LoginActivity)
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
