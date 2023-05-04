package com.ck.tutorialxv1.activities.chamith

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ck.tutorialxv1.activities.register
import com.ck.tutorialxv1.activities.teacherRegister
import com.ck.tutorialxv1.databinding.ActivitySignViewBinding

class SignView : AppCompatActivity() {

    private lateinit var binding: ActivitySignViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signS.setOnClickListener {
            val intent = Intent(this, register::class.java)
            startActivity(intent)
        }
        binding.signT.setOnClickListener {
            val intent = Intent(this, teacherRegister::class.java)
            startActivity(intent)
        }
        binding.login.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }

    }
}