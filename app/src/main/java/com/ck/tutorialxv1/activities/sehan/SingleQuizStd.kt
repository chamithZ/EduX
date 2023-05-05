package com.ck.tutorialxv1.activities.sehan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.ck.tutorialxv1.R



class SingleQuizStd : AppCompatActivity() {

    private lateinit var viewQuestion : TextView
    private lateinit var Option01 : Button
    private lateinit var Option02 : Button
    private lateinit var Option03 : Button
    private lateinit var Option04 : Button
    var i =1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_quiz_std)
        viewQuestion = findViewById(R.id.viewQuestion)
        Option01 = findViewById(R.id.Option01)
        Option02 = findViewById(R.id.option02)
        Option03 = findViewById(R.id.option03)
        Option04 = findViewById(R.id.option04)

//        var myQuestions = intent.getSerializableExtra("QuestionList") as? ArrayList<Question>
//        viewQuestion.text=myQuestions[i].question
//        Option01.text = myQuestions[].option1
//        Option02.text = myQuestions

    }


}