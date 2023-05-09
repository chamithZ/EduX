package com.ck.tutorialxv1.activities.chamith

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.ck.tutorialxv1.R
import com.ck.tutorialxv1.activities.sehan.getStart
import com.ck.tutorialxv1.databinding.ActivityCourseDetailsBinding
import com.ck.tutorialxv1.databinding.ActivityEnrollCourseviewBinding
import com.ck.tutorialxv1.databinding.ActivityStdCourseOverviewBinding
import com.ck.tutorialxv1.models.chamith.StdCourseModel
import com.ck.tutorialxv1.models.chamith.UserModel
import com.ck.tutorialxv1.models.courseModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class EnrollCourseview : AppCompatActivity() {

    private lateinit var binding: com.ck.tutorialxv1.databinding.ActivityEnrollCourseviewBinding
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnrollCourseviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setValuesToViews()

        binding.unenroll.setOnClickListener {
            unenroll(intent.getStringExtra("courseId").toString())
        }

        binding.home4.setOnClickListener{
            val intent= Intent(this, StudentHome::class.java)
            startActivity(intent)
        }

    }


    // course delete


    private fun setValuesToViews() {


        binding.subjectE.text = intent.getStringExtra("subject")
        binding.dateE.text = intent.getStringExtra("date")
        binding.timeE.text = intent.getStringExtra("time")
        binding.zoomE.text = intent.getStringExtra("zoomLink")
        binding.gradeE.text= intent.getStringExtra("grade")


    }


    private fun getUserId(): String {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        return userId ?: ""
    }


    private fun unenroll(courseId: String) {
        db = FirebaseDatabase.getInstance().getReference("std")
        val query = db.orderByChild("courseId").equalTo(courseId)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    snapshot.ref.removeValue()
                }
                Toast.makeText(this@EnrollCourseview, "Unenrolled from course!", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@EnrollCourseview, "Failed to unenroll from course: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
        val intent = Intent(this, Enroll_courses::class.java)
        finish()
        startActivity(intent)
    }
}

