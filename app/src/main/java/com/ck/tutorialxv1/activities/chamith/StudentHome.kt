package com.ck.tutorialxv1.activities.chamith

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ck.tutorialxv1.R
import com.ck.tutorialxv1.activities.hansika.AddBlog
import com.ck.tutorialxv1.activities.hansika.ReadBlog
import com.ck.tutorialxv1.activities.sehan.ViewAllQuiz
import com.ck.tutorialxv1.activities.supuni.AddBook
import com.ck.tutorialxv1.activities.supuni.ReadBook
import com.ck.tutorialxv1.activities.teacherAccount
import com.ck.tutorialxv1.databinding.ActivityStudentHomeBinding
import kotlinx.android.synthetic.main.activity_add_book.*
import java.util.*

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

        binding.yourCourse .setOnClickListener {
            val intent = Intent(this, Enroll_courses::class.java)
            startActivity(intent)
        }
        binding.viewQuizzes .setOnClickListener {
            val intent = Intent(this, ViewAllQuiz::class.java)
            startActivity(intent)
        }
        binding.Saccount .setOnClickListener {
            val intent = Intent(this, StudentAccount::class.java)
            startActivity(intent)
        }

        binding.addblog.setOnClickListener {
            val intent = Intent(this, AddBlog::class.java)
            startActivity(intent)
        }

        binding.viewBlog .setOnClickListener {
            val intent = Intent(this, ReadBlog::class.java)
            startActivity(intent)
        }
        binding.addBook.setOnClickListener {
            val intent = Intent(this, AddBook::class.java)
            startActivity(intent)
        }

        binding.viewBook .setOnClickListener {
            val intent = Intent(this, ReadBook ::class.java)
            startActivity(intent)
        }

        val userName = sharedPreferences.getString("name","")

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

            binding.userName.text = " $greeting $userName! \uD83D\uDE0A"
        }
    }
}
