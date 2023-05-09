package com.ck.tutorialxv1.activities.hansika

import Blog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.ck.tutorialxv1.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ReadBlog : AppCompatActivity() {

    private lateinit var topic: TextView
    private lateinit var content: TextView
    private lateinit var author: TextView
    private var recordId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_blog)

        // Get references to the TextView views
        topic = findViewById<TextView>(R.id.r_topic)
        content = findViewById<TextView>(R.id.r_blog)
        author = findViewById<TextView>(R.id.r_author)
        val databaseRef = FirebaseDatabase.getInstance().getReference("blogs")
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        val query = databaseRef.orderByChild("userId").equalTo(userId).limitToLast(1)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get the last blog added by the current user
                val blogs = mutableListOf<Blog>()
                for (snapshot in dataSnapshot.children) {
                    val blog = snapshot.getValue(Blog::class.java)
                    blog?.userId = snapshot.key // store the record ID as a class-level variable

                    blog?.let { blogs.add(it) }
                }

                if (blogs.isNotEmpty()) {
                    // Display the last blog added by the current user
                    topic.text = blogs[0].title
                    content.text = blogs[0].content
                    author.text = blogs[0].author
                    recordId = blogs[0].userId
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle the error
            }
        })


        val deleteButton: Button = findViewById(R.id.deleteblog)
        deleteButton.setOnClickListener {
            val databaseReference = FirebaseDatabase.getInstance().getReference("blogs")
            val recordReference = databaseReference.child(recordId ?: "")

            Log.d("DeleteBlog", "Deleting record with ID: $recordId")

            // Remove the record from Firebase
            recordReference.removeValue()

            val intent = Intent(this@ReadBlog, ReadBlog::class.java)
            startActivity(intent)

        }

        val editButton: Button = findViewById(R.id.editBtn)
        editButton.setOnClickListener {
            val intent = Intent(this@ReadBlog, EditBlog::class.java)
            intent.putExtra("recordId", recordId)
            startActivity(intent)
        }

    }


}
