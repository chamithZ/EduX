package com.ck.tutorialxv1.activities.sehan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.ck.tutorialxv1.R
import com.ck.tutorialxv1.activities.chamith.MainActivity
import com.ck.tutorialxv1.models.Question
import com.ck.tutorialxv1.models.sehan.Quiz

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Add_Single_Quiz : AppCompatActivity() {

    private  lateinit var nextBtn : Button
    private lateinit var doneBtn : Button
    private lateinit var itQuizName : String
    private lateinit var itNoOfQuiz : String
    private  lateinit var itGrade : String
    private lateinit var itQuizCourse : String
    private lateinit var iQuestion : EditText
    private lateinit var icorrectOp : EditText
    private lateinit var iop01 : EditText
    private lateinit var iop02 : EditText
    private lateinit var iop03 : EditText
    private lateinit var iop04 : EditText
    private  var quizList = mutableListOf<Question>()
    private lateinit var dbRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_single_quiz)

        dbRef = FirebaseDatabase.getInstance().getReference("Quizes")

        nextBtn = findViewById(R.id.nextBtn)
        doneBtn = findViewById(R.id.doneBtn)
        itQuizName = intent.getStringExtra("itQuizName").toString()
        itNoOfQuiz = intent.getStringExtra("itNoOfQuiz").toString()
        itGrade = intent.getStringExtra("itGrade").toString()
        itQuizCourse = intent.getStringExtra("itQuizCourse").toString()
        iQuestion = findViewById(R.id.Question)
        icorrectOp = findViewById(R.id.correctOp)
        iop01 = findViewById(R.id.op01)
        iop02 = findViewById(R.id.op02)
        iop03 = findViewById(R.id.op03)
        iop04 = findViewById(R.id.op04)



        nextBtn.setOnClickListener {
                saveSingleQuestions()
        }
        doneBtn.setOnClickListener {
            saveSingleQuestions()
            saveQuizData()
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun saveSingleQuestions() {
        val TopQuestion = iQuestion.text.toString()
        val correctOp = icorrectOp.text.toString()
        val op01 = iop01.text.toString()
        val op02 = iop02.text.toString()
        val op03 = iop03.text.toString()
        val op04 = iop04.text.toString()

        if(TopQuestion.isEmpty()){
            iQuestion.error ="please enter the Question"
        }
        if(correctOp.isEmpty()){
            icorrectOp.error ="Please enter the Correct Answer"
        }

        val question = Question(TopQuestion,op01,op02,op03,op04,correctOp)
        quizList.add(question)

        iQuestion.text.clear()
        icorrectOp.text.clear()
        iop01.text.clear()
        iop02.text.clear()
        iop03.text.clear()
        iop04.text.clear()

    }

    private fun saveQuizData() {
        val QuizName = itQuizName
        val Grade = itGrade
        val Course = itQuizCourse
        val noOfQuestions = itNoOfQuiz

        val quizId = dbRef.push().key!!

        val quiz = Quiz(quizId,intent.getStringExtra("userID").toString(),QuizName,Grade,Course,quizList)

        dbRef.child(quizId).setValue(quiz)
            .addOnCompleteListener {
                Toast.makeText(this,"Data Inserted",Toast.LENGTH_SHORT).show()
            } .addOnFailureListener {
                err -> Toast.makeText(this,"Data Not Inserted ${err.message}",Toast.LENGTH_SHORT).show()
            }
    }

}