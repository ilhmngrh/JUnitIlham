package com.my.junitetest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.my.junitetest.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val user = auth.currentUser
        val userEmail = user?.email

        binding.welcomeMessage.text = if (userEmail != null) {
            "Selamat datang, $userEmail!"
        } else {
            "Selamat datang di Dashboard!"
        }

        // Tambahkan tindakan lain sesuai kebutuhan
    }
}
