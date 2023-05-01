package com.ck.tutorialxv1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ck.tutorialxv1.databinding.ActivityRegisterBinding
import com.ck.tutorialxv1.activities.login
import com.google.firebase.auth.FirebaseAuth


class register : AppCompatActivity() {

        private lateinit var binding: ActivityRegisterBinding
        private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth= FirebaseAuth.getInstance()



        binding.btnRegsiter.setOnClickListener(){
            val email=binding.email.text.toString()
            val pass= binding.password.text.toString()
            val repass=binding.repassword.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty())
            {
                if(pass==repass){
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                        if(it.isSuccessful ){
                            val intent= Intent(this, login::class.java)
                            startActivity(intent)

                        }
                        else{
                            Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }

                    }
                }
                else{
                    Toast.makeText(this,"password is not matching", Toast.LENGTH_SHORT).show()
                    }
            }
            else{
                Toast.makeText(this,"EMpty feilds not allowed!!", Toast.LENGTH_SHORT).show()

            }


        }

    }
}