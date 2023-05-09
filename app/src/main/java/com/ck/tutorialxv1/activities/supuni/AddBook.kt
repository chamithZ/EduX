package com.ck.tutorialxv1.activities.supuni

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ck.tutorialxv1.R
import com.ck.tutorialxv1.models.supuni.Book
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddBook : AppCompatActivity() {
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        // Get a reference to your Firebase Realtime Database
        databaseRef = FirebaseDatabase.getInstance().getReference("books")

        // Get references to the EditText views
        val titleEditText = findViewById<EditText>(R.id.i_title)
        val authorTypeEditText = findViewById<EditText>(R.id.i_author)
        val onofbookEditText = findViewById<EditText>(R.id.i_noofBooks)
        val contactEditText = findViewById<EditText>(R.id.i_contact)


        // Set an onClickListener for the submit button
        val submitButton = findViewById<Button>(R.id.addbook)
        submitButton.setOnClickListener {
            // Get the values from the EditTexts
            val title = titleEditText.text.toString().trim()
            val author = authorTypeEditText.text.toString().trim()
            val noOfbook = onofbookEditText.text.toString().trim()
            val contact = contactEditText.text.toString().trim()



            // Get the current user id
            val userId = FirebaseAuth.getInstance().currentUser?.uid

            // Create a new Blog object with the values and the user id
            val books =  Book(userId.toString(), title, author, noOfbook.toString(), contact.toString())

            // Add the Blog object to the Firebase Realtime Database
            databaseRef.push().setValue(books)
                .addOnSuccessListener {
                    // Show a success message to the user
                    Toast.makeText(this, "Book added successfully", Toast.LENGTH_LONG).show()

                    // Clear the EditText fields
                    titleEditText.text.clear()
                    authorTypeEditText.text.clear()
                    onofbookEditText.text.clear()
                    contactEditText.text.clear()


                    // Navigate to the read activity
                    val intent = Intent(this, ReadBook::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    // Show an error message to the user
                    Toast.makeText(this, "Failed to add book", Toast.LENGTH_LONG).show()
                }
        }




    }
}