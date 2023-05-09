package com.ck.tutorialxv1.activities.sehan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.ck.tutorialxv1.R
import com.ck.tutorialxv1.models.Question
import com.ck.tutorialxv1.models.sehan.Quiz
import com.google.firebase.database.*
import com.google.gson.Gson


class SingleQuizStd : AppCompatActivity() {

    private lateinit var quiz: Quiz
    private lateinit var questionTextView: TextView
    private lateinit var option1Button: Button
    private lateinit var option2Button: Button
    private lateinit var option3Button: Button
    private lateinit var option4Button: Button
    private lateinit var nextButton: Button
    private lateinit var dbRef: DatabaseReference
    private var currentQuestionNumber: Int = 1
    private var correctAnswerCount: Int = 0
    private var clickedAnswer : String = "0"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_quiz_std)

        val quizId = intent.getStringExtra("id")

        dbRef = FirebaseDatabase.getInstance().getReference("Quizes/$quizId")

        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    quiz = snapshot.getValue(Quiz::class.java)!!

                    // Store the Quiz object in Shared Preferences
                    val sharedPrefs = getSharedPreferences("QuizPrefs", MODE_PRIVATE)
                    sharedPrefs.edit().putString("quiz", Gson().toJson(quiz)).apply()

                    // Initialize the UI elements
                    questionTextView = findViewById(R.id.viewQuestion)
                    option1Button = findViewById(R.id.opt01)
                    option2Button = findViewById(R.id.opt02)
                    option3Button = findViewById(R.id.opt03)
                    option4Button = findViewById(R.id.opt04)
                    nextButton = findViewById(R.id.button)

                    // Display the first question on the screen
                    displayQuestion(currentQuestionNumber)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun displayQuestion(questionNumber: Int) {
        val question = quiz.questions[questionNumber-1]

        // Display the question to the user (e.g. set text for a TextView)
        val questionTextView: TextView = findViewById(R.id.viewQuestion)
        questionTextView.text = question.question

        // Display the answer options
        option1Button.text = question.option1

        option2Button.text = question.option2

        option3Button.text = question.option3

        option4Button.text = question.option4


            // Check whether the user has selected an answer
        option1Button.setOnClickListener {
               clickedAnswer="1"
               Toast.makeText(this,"Option 01 selected",LENGTH_SHORT).show()
           }

            option2Button.setOnClickListener {
                clickedAnswer="2"
                Toast.makeText(this,"Option 02 selected",LENGTH_SHORT).show()
            }
            option3Button.setOnClickListener {
                clickedAnswer="3"
                Toast.makeText(this,"Option 03 selected",LENGTH_SHORT).show()
            }
            option4Button.setOnClickListener {
                clickedAnswer="4"
                Toast.makeText(this,"Option 04 selected",LENGTH_SHORT).show()
            }



        nextButton.setOnClickListener {
            if(clickedAnswer==question.userAnswer){
                correctAnswerCount++
                Toast.makeText(this,"checked",LENGTH_SHORT).show()
            }
            if(currentQuestionNumber<=quiz.questions.size){
                currentQuestionNumber++
                if(currentQuestionNumber>quiz.questions.size){
                    val perc = (correctAnswerCount/quiz.questions.size) * 100
                    val result = "$perc%"
                    // Move to the quiz result activity
                    val intent = Intent(this, QuizResultActivity::class.java)
                    intent.putExtra("result", result)
                    startActivity(intent)
                }else{
                    displayQuestion(currentQuestionNumber)
                }

            }
        }



//                // Move to the next question or finish the quiz
//                if (currentQuestionIndex < quiz.questions.size - 1) {
//                    currentQuestionIndex++
//                    currentQuestion = quiz.questions[currentQuestionIndex]
//                    currentQuestionNumber++
//                    displayQuestion(currentQuestionNumber)
//                } else {
//                    // Calculate and display the quiz result
//                    val result = "$correctAnswerCount/${quiz.questions.size}"
//
//                    // Move to the quiz result activity
//                    val intent = Intent(this, QuizResultActivity::class.java)
//                    intent.putExtra("result", result)
//                    startActivity(intent)
//                }
        }


}