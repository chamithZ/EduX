package com.ck.tutorialxv1.activities.chamith

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ck.tutorialxv1.R
import com.ck.tutorialxv1.databinding.ActivityCourseFetchBinding
import com.google.firebase.database.DatabaseReference

class CourseFetch : AppCompatActivity() {
    private lateinit var binding: ActivityCourseFetchBinding

    private lateinit var db: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_fetch)

    }
 }