package com.ck.tutorialxv1.activities.chamith

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ck.tutorialxv1.R
import com.ck.tutorialxv1.databinding.ActivityStudentAccountBinding


class StudentAccount : AppCompatActivity() {
    private lateinit var binding: ActivityStudentAccountBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_account)
        binding = ActivityStudentAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        binding.textView16.setOnClickListener{
            val intent = Intent(this, Std_progress::class.java)
            startActivity(intent)
        }
        binding.home.setOnClickListener{
            val intent = Intent(this, StudentHome::class.java)
            startActivity(intent)
        }


        binding.logout.setOnClickListener {
            val editor = sharedPreferences.edit()

            editor.clear()
            editor.apply()

            val intent = Intent(this, login::class.java)
            startActivity(intent)
            finish()
            finishAffinity() // close the  previous activity
        }

    }
}