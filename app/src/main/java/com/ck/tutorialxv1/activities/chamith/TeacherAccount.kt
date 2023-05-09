package com.ck.tutorialxv1.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ck.tutorialxv1.R
import com.ck.tutorialxv1.activities.chamith.MainActivity
import com.ck.tutorialxv1.activities.chamith.login
import com.ck.tutorialxv1.databinding.ActivityTeacherAccountBinding

class teacherAccount : AppCompatActivity() {
    private lateinit var binding: ActivityTeacherAccountBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_account)
        binding = ActivityTeacherAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)



        binding.logout.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
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
