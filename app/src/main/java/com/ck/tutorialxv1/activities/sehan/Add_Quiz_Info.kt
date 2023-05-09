package com.ck.tutorialxv1.activities.sehan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.ck.tutorialxv1.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class Add_Quiz_Info : AppCompatActivity() {

    private  lateinit var AddQuizbtn : Button
    private lateinit var itQuizName : EditText
    private lateinit var itNoOfQuiz : EditText
    private lateinit var itGrade : EditText
    private lateinit var itQuizCourse: EditText


    private lateinit var dbRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_quiz_info)

        AddQuizbtn = findViewById(R.id.AddQuizbtn)
        itQuizName = findViewById(R.id.itQuizName)
        itNoOfQuiz = findViewById(R.id.itNoOfQuiz)
        itGrade = findViewById(R.id.itGrade)
        itQuizCourse = findViewById(R.id.itQuizCourse)

        AddQuizbtn.setOnClickListener {
            var intent = Intent(this, Add_Single_Quiz::class.java)
            intent.putExtra("itQuizName",itQuizName.text.toString())
            intent.putExtra("itNoOfQuiz",itNoOfQuiz.text.toString())
            intent.putExtra("itGrade",itGrade.text.toString())
            intent.putExtra("itQuizCourse",itQuizCourse.text.toString())
            intent.putExtra("userID",getUserId())
            startActivity(intent)
        }


    }
    private fun getUserId(): String {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        return userId ?: ""
    }
}