package com.ck.tutorialxv1.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ck.tutorialxv1.R
import com.ck.tutorialxv1.models.courseModel
import com.ck.tutorialxv1.databinding.ActivityAddClassBinding

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

        binding.btnAddClass.setOnClickListener{
            saveClassData()
        }
    }

    private fun saveClassData(){
        val grade = binding.grade.text.toString()
        val zoomLink = binding.zoomLink.text.toString()
        val subject = binding.subject.text.toString()
        val date = binding.date.text.toString()
        val time = binding.time.text.toString()

        if(subject.isEmpty()){
            binding.subject.error="Please enter the subject"
        }
        if(grade.isEmpty()){
            binding.grade.error="Please enter the grade"
        }
        if(zoomLink.isEmpty()){
            binding.zoomLink.error="Please enter the zoom link"
        }
        if(date.isEmpty()){
            binding.date.error="Please enter the date"
        }
        if(time.isEmpty()){
            binding.time.error="Please enter the time"
        }
        val courseId=db.push().key!!

        val course= courseModel(subject,grade, zoomLink, date, time)

        db.child(courseId).setValue(course)
            .addOnCompleteListener{
                Toast.makeText(this,"Data inserted sycessfully",Toast.LENGTH_LONG).show()
                binding.subject.setText("")
                binding.grade.setText("")
                binding.zoomLink.setText("")
                binding.date.setText("")
                binding.time.setText("")


            }.addOnFailureListener { err->
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_LONG).show()
            }




    }
}