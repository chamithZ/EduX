package com.ck.tutorialxv1.activities.supuni

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.ck.tutorialxv1.R
import com.ck.tutorialxv1.activities.chamith.MainActivity
import com.google.firebase.database.FirebaseDatabase

class EditBook : AppCompatActivity() {


    private lateinit var title: TextView
    private lateinit var author: TextView
    private lateinit var quantity: TextView
    private lateinit var contact: TextView

    private var recordId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_book)

        // Initialize the TextViews
        title = findViewById(R.id.e_title)
        author = findViewById(R.id.e_author)
        quantity = findViewById(R.id.e_noofBooks)
        contact = findViewById(R.id.e_contact)


        // Initialize the Buttons
        var submitButton = findViewById<Button>(R.id.Editbook)

        // Get a reference to your Firebase database
        val database = FirebaseDatabase.getInstance().reference

        // Query the database to retrieve the last row of data
        database.child("books").orderByKey().limitToLast(1).get()
            .addOnSuccessListener { dataSnapshot ->
                // Update the values of that row with the new data
                // Get the first child node
                val lastChild = dataSnapshot.children.last()
                // Store the record ID as a class-level variable
                recordId = lastChild.key
                // Get the values of the child nodes and convert them to strings
                val Topic = lastChild.child("title").value?.toString()
                val Author = lastChild.child("author").value?.toString()
                val Quantity = lastChild.child("quantity").value?.toString()
                val Contact = lastChild.child("contact").value?.toString()


                // Set the values of the TextViews
                title.text = Topic
                author.text = Author
                quantity.text = Quantity
                contact.text = Contact


                // Set up the submit button onClick listener
                submitButton.setOnClickListener {
                    // Get the updated input values
                    val TOPIC = title.text.toString()
                    val QUANTITY = quantity.text.toString()
                    val AUTHOR = author.text.toString()
                    val CONTACT = contact.text.toString()


                    // Update the income record with the new values
                    lastChild.ref.updateChildren(
                        mapOf(
                            "userId" to recordId,
                            "title" to TOPIC,
                            "quantity" to QUANTITY,
                            "contact" to CONTACT,
                        )
                    )
                    // Show a toast message indicating that the record was updated
                    Toast.makeText(this@EditBook, "Record updated successfully", Toast.LENGTH_SHORT)
                        .show()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent) // Start the new activity
                }
            }
            .addOnFailureListener { exception ->
                // Handle any errors that occur
            }
    }
}
