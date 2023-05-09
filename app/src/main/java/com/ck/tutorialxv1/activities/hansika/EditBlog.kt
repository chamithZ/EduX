package com.ck.tutorialxv1.activities.hansika

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.ck.tutorialxv1.R
import com.google.firebase.database.FirebaseDatabase

class EditBlog : AppCompatActivity() {

    private lateinit var topic: TextView
    private lateinit var content: TextView
    private lateinit var author: TextView
    private var recordId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_blog)

        // Initialize the TextViews
        topic = findViewById(R.id.e_topic)
        content = findViewById(R.id.e_blog)
        author = findViewById(R.id.e_author)

        // Initialize the Buttons
        var submitButton = findViewById<Button>(R.id.editBtn)

        // Get a reference to your Firebase database
        val database = FirebaseDatabase.getInstance().reference

        // Query the database to retrieve the last row of data
        database.child("blogs").orderByKey().limitToLast(1).get()
            .addOnSuccessListener { dataSnapshot ->
                // Update the values of that row with the new data
                // Get the first child node
                val lastChild = dataSnapshot.children.last()
                // Store the record ID as a class-level variable
                recordId = lastChild.key
                // Get the values of the child nodes and convert them to strings
                val Topic = lastChild.child("title").value?.toString()
                val Content = lastChild.child("content").value?.toString()
                val Author = lastChild.child("author").value?.toString()

                // Set the values of the TextViews
                topic.text = Topic
                content.text = Content
                author.text = Author

                // Set up the submit button onClick listener
                submitButton.setOnClickListener {
                    // Get the updated input values
                    val TOPIC = topic.text.toString()
                    val CONTENT = content.text.toString()
                    val AUTHOR = author.text.toString()

                    // Update the income record with the new values
                    lastChild.ref.updateChildren(
                        mapOf(
                            "title" to TOPIC,
                            "content" to CONTENT,
                            "author" to AUTHOR,
                        )
                    )
                    // Show a toast message indicating that the record was updated
                    Toast.makeText(this@EditBlog, "Record updated successfully", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, ReadBlog::class.java)
                    startActivity(intent) // Start the new activity
                }
            }
            .addOnFailureListener { exception ->
                // Handle any errors that occur
            }
    }
}
