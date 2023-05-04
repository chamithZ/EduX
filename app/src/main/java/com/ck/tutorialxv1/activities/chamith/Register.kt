package com.ck.tutorialxv1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ck.tutorialxv1.databinding.ActivityRegisterBinding
import com.ck.tutorialxv1.activities.chamith.login
import com.ck.tutorialxv1.models.chamith.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class register : AppCompatActivity() {

        private lateinit var binding: ActivityRegisterBinding
        private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth= FirebaseAuth.getInstance()



        binding.login3.setOnClickListener(){ // register
            val name = binding.name.text.toString()
            val email = binding.email.text.toString()
            val pass = binding.password.text.toString()
            val repass = binding.rePassword.text.toString()
            val contactNum = binding.phone.text.toString()
            val age = binding.age.text.toString()
            val grade = binding.editTextNumberSigned.text.toString()

            if (binding.checkBox.isChecked) { // Check if the checkbox is ticked
                if (email.isNotEmpty() && pass.isNotEmpty()) {
                    if (pass == repass) {
                        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener{
                            if(it.isSuccessful) {
                                val user = UserModel(name, email, contactNum, age, grade)
                                FirebaseDatabase.getInstance().reference.child("users").child(firebaseAuth.currentUser?.uid ?: "")
                                    .setValue(user)
                                val intent = Intent(this, login::class.java)
                                startActivity(intent)

                            } else {
                                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please agree to the terms and conditions to continue", Toast.LENGTH_SHORT).show()
            }
        }

    }
    }
