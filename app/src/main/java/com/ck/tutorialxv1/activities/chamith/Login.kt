package com.ck.tutorialxv1.activities.chamith

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.ck.tutorialxv1.R
import com.ck.tutorialxv1.activities.register
import com.ck.tutorialxv1.activities.sehan.getStart
import com.ck.tutorialxv1.databinding.ActivityLoginBinding
import com.ck.tutorialxv1.models.chamith.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var btnsave: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        btnsave=findViewById(R.id.login2)
        firebaseAuth= FirebaseAuth.getInstance()
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        binding.textView4.setOnClickListener {
            val intent= Intent(this, SignView::class.java)
            startActivity(intent)
        }
        binding.textView2 .setOnClickListener{
            val intent= Intent(this, getStart::class.java)
            startActivity(intent)
        }

        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            // If the user is already logged in, navigate to MainActivity
            val grade = sharedPreferences.getString("grade", null)
                if(grade==null){
                    System.out.println("tewahcerrrrrr")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    System.out.println("Studenttttttt")
                    val intent = Intent(this, StudentHome::class.java)
                    startActivity(intent)
                    finish()

                }

        }

        binding.login2.setOnClickListener{

            val email=binding.editTextTextEmailAddress.text.toString()
            val pass= binding.editTextTextPassword.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty())
            {

                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val userId = firebaseAuth.currentUser!!.uid



                            // Retrieve user details from the database
                            val database = FirebaseDatabase.getInstance()
                            val ref = database.getReference("users/$userId")
                            ref.get().addOnSuccessListener { dataSnapshot ->
                                val user = dataSnapshot.getValue(UserModel::class.java)




                                // Save user data and login status to SharedPreferences
                                if (user?.grade == null) {
                                    // User is a teacher
                                    val editor = sharedPreferences.edit()
                                    editor.putString("userId", userId)
                                    editor.putString("email", email)
                                    editor.putString("name", user?.name)
                                    editor.putString("contactNum", user?.contactNum)
                                    editor.putString("age", user?.age)
                                    editor.putString("bankAc", user?.bankAc)
                                    editor.putString("branch", user?.branch)
                                    editor.putString("qualification", user?.qualification)
                                    editor.putBoolean("isLoggedIn", true)
                                    editor.apply()

                                    val intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    System.out.println("studentttt"+user?.grade)
                                    // User is a student
                                    val editor = sharedPreferences.edit()
                                    editor.putString("userId", userId)
                                    editor.putString("email", email)
                                    editor.putString("name", user?.name)
                                    editor.putString("contactNum", user?.contactNum)
                                    editor.putString("age", user?.age)
                                    editor.putString("grade", user?.grade)
                                    editor.putBoolean("isLoggedIn", true)
                                    editor.apply()

                                    val intent = Intent(this, StudentHome::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            }.addOnFailureListener {
                                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                            }


                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                    }

                }
            } else {
                Toast.makeText(this,"Empty fields not allowed!!", Toast.LENGTH_SHORT).show()
            }

        }
    }
}