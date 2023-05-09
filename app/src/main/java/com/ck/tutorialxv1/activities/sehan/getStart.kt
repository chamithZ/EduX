package com.ck.tutorialxv1.activities.sehan

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ck.tutorialxv1.R
import com.ck.tutorialxv1.activities.chamith.SignView
import com.ck.tutorialxv1.databinding.ActivityLoginBinding
import com.ck.tutorialxv1.databinding.ActivityLoginIntroBinding

class getStart : AppCompatActivity() {

         private lateinit var binding: ActivityLoginIntroBinding
        private lateinit var Registerbtn1 : Button

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding= ActivityLoginIntroBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.Registerbtn1.setOnClickListener {
                    val intent= Intent(this, SignView::class.java)
                    startActivity(intent)
            }


    }
}