package com.ck.tutorialxv1.activities.sehan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.ck.tutorialxv1.R
import com.google.firebase.database.FirebaseDatabase

class View_Quiz_Details : AppCompatActivity() {

    private lateinit var quizId : TextView
    private lateinit var quizName : TextView
    private lateinit var quizCourse : TextView
    private lateinit var quizGrade : TextView
    private lateinit var btnUpdate : Button
    private lateinit var btnDelete : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_quiz_details)

        quizId = findViewById(R.id.viewQuizId)
        quizName = findViewById(R.id.viewQuizName)
        quizGrade = findViewById(R.id.viewGrade)
        quizCourse = findViewById(R.id.viewCourse)
        btnDelete = findViewById(R.id.deleteQuizBtn)
        btnUpdate = findViewById(R.id.updateQuizBtn)


        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("id").toString(),
                intent.getStringExtra("title").toString()
            )

        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("id").toString()
            )
        }

    }

    private fun deleteRecord(id: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Quizes").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Quiz is deleted",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ViewAllQuiz::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener {err ->
            Toast.makeText(this,"deleting error $err",Toast.LENGTH_SHORT).show()
        }
    }

    private fun openUpdateDialog(id: String, title: String) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_dialog_quiz_info,null)

        mDialog.setView(mDialogView)

        val etQuizNAme = mDialogView.findViewById<EditText>(R.id.etQuizNAme)
        val etQuizGrade = mDialogView.findViewById<EditText>(R.id.etQuizGrade)
        val etQuizCourse =mDialogView.findViewById<EditText>(R.id.etQuizCourse)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etQuizNAme.setText(intent.getStringExtra("title").toString())
        etQuizCourse.setText(intent.getStringExtra("grade").toString())
        etQuizGrade.setText(intent.getStringExtra("course").toString())

        mDialog.setTitle("Updating $title Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateQuizData(
                id,
                etQuizNAme.text.toString(),
                etQuizCourse.text.toString(),
                etQuizGrade.text.toString()
            )
            Toast.makeText(applicationContext,"Quiz data updated", Toast.LENGTH_LONG).show()

            quizName.text = etQuizNAme.text.toString()
            quizCourse.text = etQuizCourse.text.toString()
            quizGrade.text = etQuizGrade.text.toString()
            alertDialog.dismiss()

        }





    }

    private fun updateQuizData(id: String, title: String, grade: String, course: String) {

        val dbRef = FirebaseDatabase.getInstance().getReference("Quizes").child(id)

        // Create a map to hold the updated fields
        val updates = mutableMapOf<String, Any>()
        updates["title"] = title
        updates["grade"] = grade
        updates["course"] = course

        // Use updateChildren() to update only the specified fields
        dbRef.updateChildren(updates).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(applicationContext, "Quiz data updated", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, "Failed to update quiz data", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initView() {

    }
    private fun setValuesToViews(){
        quizId.text = intent.getStringExtra("id")
        quizName.text = intent.getStringExtra("title")
        quizCourse.text = intent.getStringExtra("grade")
        quizGrade.text = intent.getStringExtra("course")


    }
}