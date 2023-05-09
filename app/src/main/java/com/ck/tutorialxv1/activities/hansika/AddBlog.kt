package com.ck.tutorialxv1.activities.hansika



import Blog
//import com.ck.tutorialxv1.models.hansika.blog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.ck.tutorialxv1.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddBlog : AppCompatActivity() {

    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_blog)

        // Get a reference to your Firebase Realtime Database
        databaseRef = FirebaseDatabase.getInstance().getReference("blogs")

        // Get references to the EditText views
        val topicEditText = findViewById<EditText>(R.id.i_topic)
        val blogTypeEditText = findViewById<EditText>(R.id.i_blog)
        val authorEditText = findViewById<EditText>(R.id.i_author)

        // Set an onClickListener for the submit button
        val submitButton = findViewById<Button>(R.id.addblogbtn)
        submitButton.setOnClickListener {
            // Get the values from the EditTexts
            val topic = topicEditText.text.toString().trim()
            val blogE = blogTypeEditText.text.toString().trim()
            val author = authorEditText.text.toString().trim()

//            // Check if the input is valid
//            if (topic.isEmpty() || blogE.isEmpty() || author == null) {
//                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_LONG).show()
//                return@setOnClickListener
//            }

            // Get the current user id
            val userId = FirebaseAuth.getInstance().currentUser?.uid

            // Create a new Blog object with the values and the user id
            val blogs =  Blog(userId.toString(), topic, blogE, author)

            // Add the Blog object to the Firebase Realtime Database
            databaseRef.push().setValue(blogs)
                .addOnSuccessListener {
                    // Show a success message to the user
                    Toast.makeText(this, "Blog added successfully", Toast.LENGTH_LONG).show()

                    // Clear the EditText fields
                    topicEditText.text.clear()
                    blogTypeEditText.text.clear()
                    authorEditText.text.clear()

                    // Navigate to the read activity
                    val intent = Intent(this, ReadBlog::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    // Show an error message to the user
                    Toast.makeText(this, "Failed to add blog", Toast.LENGTH_LONG).show()
                }
        }

//
//        val myButton = findViewById<Button>(R.id.reddblog)
//        myButton.setOnClickListener {
//            val intent = Intent(this, ReadBlog::class.java)
//            startActivity(intent)
//
//        }

    }
}
