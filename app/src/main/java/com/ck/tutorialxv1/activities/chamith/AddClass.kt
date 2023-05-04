package com.ck.tutorialxv1.activities.chamith

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ck.tutorialxv1.R
import com.ck.tutorialxv1.models.courseModel
import com.ck.tutorialxv1.databinding.ActivityAddClassBinding
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddClass : AppCompatActivity() {


    private lateinit var binding: ActivityAddClassBinding
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_class)

        db= FirebaseDatabase.getInstance().getReference("course")

        binding = ActivityAddClassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addClass.setOnClickListener{
            saveClassData()
        }
    }

    private fun saveClassData(){
        val grade = binding.gradeTV .text.toString()
        val zoomLink = binding.ZoomLinkTv .text.toString()
        val subject = binding.subjectTv .text.toString()
        val date = binding.dateTv .text.toString()
        val time = binding.timeTv .text.toString()

        if(subject.isEmpty()){
            binding.subjectTv.error="Please enter the subject"
        }
        if(grade.isEmpty()){
            binding.gradeTV.error="Please enter the grade"
        }
        if(zoomLink.isEmpty()){
            binding.ZoomLinkTv.error="Please enter the zoom link"
        }
        if(date.isEmpty()){
            binding.dateTv.error="Please enter the date"
        }
        if(time.isEmpty()){
            binding.timeTv.error="Please enter the time"
        }
        val courseId=db.push().key!!

        val course= courseModel(courseId,subject,grade, zoomLink, date, time, getUserId())

        db.child(courseId).setValue(course)
            .addOnCompleteListener{
                Toast.makeText(this,"Data inserted sycessfully",Toast.LENGTH_LONG).show()
                binding.subjectTv.setText("")
                binding.gradeTV.setText("")
                binding.ZoomLinkTv.setText("")
                binding.dateTv.setText("")
                binding.timeTv.setText("")
            }.addOnFailureListener { err->
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_LONG).show()
            }
    }

    private fun getUserId(): String {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        return userId ?: ""
    }
}