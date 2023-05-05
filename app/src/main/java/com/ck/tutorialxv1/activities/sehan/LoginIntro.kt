package com.ck.tutorialxv1.activities.sehan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.ck.tutorialxv1.R


class LoginIntro : AppCompatActivity() {

    private lateinit var Registerbtn1 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_intro)

//        Registerbtn1 = findViewById(R.id.Registerbtn1)
//        Registerbtn1.setOnClickListener {
//            var intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }
    }
}