package com.ck.tutorialxv1.activities.supuni

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.ck.tutorialxv1.R
import com.ck.tutorialxv1.activities.chamith.MainActivity
import com.ck.tutorialxv1.models.supuni.Book
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ReadBook : AppCompatActivity() {

    private lateinit var title: TextView
    private lateinit var author: TextView
    private lateinit var noofBook:  TextView
    private lateinit var contact:  TextView

    private var recordId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_book)

        // Get references to the TextView views
        title = findViewById<TextView>(R.id.r_title)
        author = findViewById<TextView>(R.id.r_author)
        noofBook = findViewById<TextView>(R.id.r_noOfBook)
        contact = findViewById<TextView>(R.id.r_contact)

        val databaseRef = FirebaseDatabase.getInstance().getReference("books")
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        val query = databaseRef.orderByChild("userId").equalTo(userId).limitToLast(1)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get the last blog added by the current user
                val blogs = mutableListOf<Book>()
                for (snapshot in dataSnapshot.children) {
                    val blog = snapshot.getValue(Book::class.java)
                    blog?.userId = snapshot.key // store the record ID as a class-level variable

                    blog?.let { blogs.add(it) }
                }

                if (blogs.isNotEmpty()) {
                    // Display the last blog added by the current user
                    title.text = blogs[0].title
                    author.text = blogs[0].author
                    noofBook.text = blogs[0].quantity
                    contact.text = blogs[0].contact
                    recordId = blogs[0].userId
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle the error
            }
        })

        val deleteButton: Button = findViewById(R.id.deletebook)
        deleteButton.setOnClickListener {
            val databaseReference = FirebaseDatabase.getInstance().getReference("books")
            val recordReference = databaseReference.child(recordId ?: "")

            Log.d("Delete Book", "Deleting record with ID: $recordId")

            // Remove the record from Firebase
            recordReference.removeValue()

            val intent = Intent(this@ReadBook, MainActivity::class.java)
            startActivity(intent)

        }

        val editButton: Button = findViewById(R.id.editBtn)
        editButton.setOnClickListener {
            val intent = Intent(this@ReadBook, EditBook::class.java)
            intent.putExtra("recordId", recordId)
            startActivity(intent)
        }

    }
}
