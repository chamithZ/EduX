package com.ck.tutorialxv1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.ck.tutorialxv1.R
import com.ck.tutorialxv1.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth



class login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth:FirebaseAuth
    private lateinit var btnsave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        btnsave=findViewById(R.id.btn_login)
        firebaseAuth= FirebaseAuth.getInstance()



        binding.signup.setOnClickListener {
            val intent= Intent(this, register::class.java)
            startActivity(intent)
        }



        binding.btnLogin.setOnClickListener{

            val email=binding.email.text.toString()
            val pass= binding.password.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty())
            {

                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                    }

                }
            }


            else{
                Toast.makeText(this,"Empty feilds not allowed!!", Toast.LENGTH_SHORT).show()

            }

        }





    }
}