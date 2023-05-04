package com.ck.tutorialxv1.activities.chamith

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ck.tutorialxv1.R
import com.ck.tutorialxv1.activities.teacherAccount
import com.ck.tutorialxv1.databinding.ActivityStudentHomeBinding

class StudentHome : AppCompatActivity() {
    private lateinit var binding: ActivityStudentHomeBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_home)
        binding = ActivityStudentHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        binding.viewCourse .setOnClickListener {
            val intent = Intent(this, FetchingAllCourse::class.java)
            startActivity(intent)
        }

        binding.viewQuizzes .setOnClickListener {
            val intent = Intent(this, FetchingAllCourse::class.java)
            startActivity(intent)
        }
        binding.Saccount .setOnClickListener {
            val intent = Intent(this, teacherAccount::class.java)
            startActivity(intent)
        }

        val userName = sharedPreferences.getString("name","")

        System.out.println(userName)

        if (!userName.isNullOrEmpty()) {
            binding.userName.text = "  $userName!"
        }
    }
}
