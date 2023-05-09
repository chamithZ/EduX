package com.ck.tutorialxv1.activities.sehan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.ck.tutorialxv1.R
import com.ck.tutorialxv1.activities.chamith.StudentHome

class QuizResultActivity : AppCompatActivity() {

    private lateinit var QuizResult  : TextView
    private lateinit var button3 : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)

        QuizResult = findViewById(R.id.QuizResult)
        button3 = findViewById(R.id.button3)
        QuizResult.text = intent.getStringExtra("result")

        button3.setOnClickListener {
            var intent = Intent(this,StudentHome::class.java)
            startActivity(intent)
        }




    }
}