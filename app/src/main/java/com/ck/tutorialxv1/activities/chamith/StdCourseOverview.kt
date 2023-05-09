package com.ck.tutorialxv1.activities.chamith

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.ck.tutorialxv1.R
import com.ck.tutorialxv1.activities.register
import com.ck.tutorialxv1.databinding.ActivityCourseDetailsBinding
import com.ck.tutorialxv1.databinding.ActivityStdCourseOverviewBinding
import com.ck.tutorialxv1.models.chamith.StdCourseModel
import com.ck.tutorialxv1.models.chamith.UserModel
import com.ck.tutorialxv1.models.courseModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class StdCourseOverview : AppCompatActivity() {

    private lateinit var binding: ActivityStdCourseOverviewBinding
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStdCourseOverviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setValuesToViews()



        binding.enroll.setOnClickListener {
            enrollInCourse()
        }
        binding.home3.setOnClickListener {
            val intent = Intent(this, StudentHome::class.java)
            startActivity(intent)
        }


    }

    private fun setValuesToViews() {


        binding.textView30.text = intent.getStringExtra("subject")
        binding.textView19.text = intent.getStringExtra("date")
        binding.textView44.text = intent.getStringExtra("time")
        binding.textView46.text = intent.getStringExtra("grade")
        val TId = intent.getStringExtra("userId") ?: ""  //get teacher Id for this course


        val userRef = FirebaseDatabase.getInstance().getReference("users").child(TId)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.getValue(UserModel::class.java)
                    binding.textView22.text = user?.name
                    binding.qText.text=user?.qualification
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }



    private fun getUserId(): String {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        return userId ?: ""
    }

    private fun enrollInCourse() {
        // Get course details from intent extras
        val courseId = intent.getStringExtra("courseId") ?: ""
        val subject = intent.getStringExtra("subject") ?: ""
        val grade = intent.getStringExtra("grade") ?: ""
        val time = intent.getStringExtra("time") ?: ""
        val date = intent.getStringExtra("date") ?: ""
        val zoomLink = intent.getStringExtra("zoomLink") ?: ""
        val TId=intent.getStringExtra("userId") ?: ""

        // Get student ID from Firebase Auth
        val studentId = getUserId()

        db = FirebaseDatabase.getInstance().getReference("std")

        val stdcourse = StdCourseModel(courseId, subject, grade, zoomLink, date, time,TId, studentId)

        val enrollmentRef = db.push()
        enrollmentRef.setValue(stdcourse)
            .addOnSuccessListener {
                // Enrolled in course successfully
                Toast.makeText(this, "Enrolled in course!", Toast.LENGTH_SHORT).show()

                // Update progress level
                val userRef = FirebaseDatabase.getInstance().getReference("users").child(studentId)
                userRef.get().addOnSuccessListener { userSnapshot ->
                    if (userSnapshot.exists()) {
                        val user = userSnapshot.getValue(UserModel::class.java)
                        val formerProgress = user?.eduPoints as? Long ?: 0
                        var courseCount = user?.coursesEnrolled as? Long ?: 0
                        val progress = ((formerProgress/10)+ 1) * 10 // Assuming each course is worth 10% progress
                        userRef.child("eduPoints").setValue(progress)
                        userRef.child("coursesEnrolled").setValue(++courseCount)
                            .addOnSuccessListener {
                                // Progress level updated successfully
                                Toast.makeText(this, "Progress level updated!", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Failed to update progress level: ${it.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to enroll in course: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }


}
