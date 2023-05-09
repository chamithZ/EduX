package com.ck.tutorialxv1.activities.chamith

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.ck.tutorialxv1.R
import com.ck.tutorialxv1.models.chamith.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_std_progress.*

class Std_progress : AppCompatActivity() {

    private lateinit var db: DatabaseReference
    private lateinit var tvProgress: TextView
    private lateinit var level: TextView
    private lateinit var enroll: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_std_progress)

        tvProgress = findViewById(R.id.level4)
        level = findViewById(R.id.levelType)
        enroll = findViewById(R.id.enrollNo)

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val userRef = FirebaseDatabase.getInstance().getReference("users").child(userId!!)

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.getValue(UserModel::class.java)
                    val progress = user?.eduPoints as? Long ?: 0
                    val levelText = getLevel(progress)
                    val courseCount = user?.coursesEnrolled as? Long ?: 0

                    val enrollments = progress.toInt()


                    tvProgress.text = "$progress"
                    level.text = levelText
                    enroll.text = courseCount.toString()

                    // Update user's level in the database
                    userRef.child("level").setValue(levelText)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    private fun getLevel(progress: Long): String {
        return when (progress) {
            in 0..20 -> "Beginner \uD83E\uDD49 "
            in 20..60 -> "Intermediate \uD83E\uDD48"
            in 60..200 -> "Advanced \uD83E\uDD47"
            else -> "Expert \uD83C\uDF96️"
        }
    }
}
