package com.ck.tutorialxv1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ck.tutorialxv1.activities.chamith.login
import com.ck.tutorialxv1.databinding.ActivityTeacherRegisterBinding
import com.ck.tutorialxv1.models.chamith.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class teacherRegister : AppCompatActivity() {

    private lateinit var binding: ActivityTeacherRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTeacherRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth= FirebaseAuth.getInstance()

        binding.Tregister.setOnClickListener { // register
            val name = binding.Tname.text.toString()
            val email = binding.Temail.text.toString()
            val pass = binding.Tpassword.text.toString()
            val repass = binding.TrePassword.text.toString()
            val contactNum = binding.Tphone.text.toString()
            val age = binding.Tage.text.toString()
            val qualification = binding.qualification.text.toString()
            val bankAc= binding.bankAc.text.toString()
            val branch=binding.bankBranch.text.toString()

            if (binding.TcheckBox.isChecked) { // Check if the checkbox is ticked
                if (email.isNotEmpty() && pass.isNotEmpty() && contactNum.isNotEmpty()) {
                    if (pass == repass) {
                        if (contactNum.length == 10) {
                            firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener{
                                if(it.isSuccessful) {
                                    val user = UserModel(name, email, contactNum, age, null, qualification, bankAc, branch)
                                    FirebaseDatabase.getInstance().reference.child("users").child(firebaseAuth.currentUser?.uid ?: "")
                                        .setValue(user)
                                    val intent = Intent(this, login::class.java)
                                    startActivity(intent)

                                } else {
                                    Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            Toast.makeText(this, "Please enter valid phone number", Toast.LENGTH_SHORT).show()
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
