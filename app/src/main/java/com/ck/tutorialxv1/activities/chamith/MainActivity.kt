package com.ck.tutorialxv1.activities.chamith

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ck.tutorialxv1.R
import com.ck.tutorialxv1.activities.sehan.Add_Quiz_Info
import com.ck.tutorialxv1.activities.sehan.ViewAllQuiz
import com.ck.tutorialxv1.activities.teacherAccount
import com.ck.tutorialxv1.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        binding.addclass.setOnClickListener {
            val intent = Intent(this, AddClass::class.java)
            startActivity(intent)
        }

        binding.textView8.setOnClickListener {
            val intent = Intent(this, FetchingCourse::class.java)
            startActivity(intent)
        }
        binding.account.setOnClickListener {
            val intent = Intent(this, teacherAccount::class.java)
            startActivity(intent)
        }
        binding.textView12.setOnClickListener {
            val intent = Intent(this,Add_Quiz_Info::class.java)
            startActivity(intent)
        }
        binding.showQuizes.setOnClickListener {
            val intent = Intent(this,ViewAllQuiz::class.java)
            startActivity(intent)
        }

        val userName = sharedPreferences.getString("name", "")
        System.out.println(userName)
        if (!userName.isNullOrEmpty()) {
            val currentTime =
                Calendar.getInstance().apply { timeInMillis = System.currentTimeMillis() }
            val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)

            val greeting = when (currentHour) {
                in 6..11 -> "Good morning,"
                in 12..16 -> "Good afternoon,"
                else -> "Good evening,"
            }

            binding.userName .text = " $greeting $userName!"
        }
    }
}
