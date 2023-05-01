package com.ck.tutorialxv1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ck.tutorialxv1.R
import com.ck.tutorialxv1.databinding.ActivityMainBinding
import com.ck.tutorialxv1.databinding.ActivityRegisterBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnInsertData.setOnClickListener {
            val intent = Intent(this, AddClass::class.java)
            startActivity(intent)
        }
        binding.btnFetchData.setOnClickListener {
            val intent =Intent(this, FetchingCourse::class.java)
            startActivity(intent)
        }
    }
}