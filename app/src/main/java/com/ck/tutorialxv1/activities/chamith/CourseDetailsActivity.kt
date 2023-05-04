package com.ck.tutorialxv1.activities.chamith

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.ck.tutorialxv1.R
import com.ck.tutorialxv1.databinding.ActivityCourseDetailsBinding
import com.ck.tutorialxv1.models.courseModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CourseDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCourseDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setValuesToViews()

        // Course Update

        binding.updateClz.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("courseId").toString(),
                intent.getStringExtra("subject").toString(),
                intent.getStringExtra("grade").toString(),
                intent.getStringExtra("date").toString(),
                intent.getStringExtra("time").toString(),
                intent.getStringExtra("zoomLink").toString(),
            )
        }

        binding.removeClz.setOnClickListener {
            deleteCourse(
                intent.getStringExtra("courseId").toString()
            )
        }
    }


    // course delete
    private fun deleteCourse(
        courseId:String
    ){
        val db = FirebaseDatabase.getInstance().getReference("course").child(courseId)
        val courseDelete=db.removeValue()

        courseDelete.addOnSuccessListener {
            Toast.makeText(this,"Course deleted",Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingCourse::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { error->
            Toast.makeText(this,"Deleting error ${error.message}",Toast.LENGTH_LONG).show()

        }
    }




    private fun setValuesToViews() {
        binding.textView30.text = intent.getStringExtra("subject")
        binding.textView19.text = intent.getStringExtra("date")
        binding.textView44.text = intent.getStringExtra("time")
        binding.textView22.text = intent.getStringExtra("zoomLink")
    }

    private fun openUpdateDialog(
        courseId: String,
        subject: String,
        grade: String,
        date: String,
        time: String,
        zoomLink: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_update_class, null)
        mDialog.setView(mDialogView)

        val subjectEditText = mDialogView.findViewById<EditText>(R.id.usubjectTv)
        val gradeEditText = mDialogView.findViewById<EditText>(R.id.ugradeTV)
        val dateEditText = mDialogView.findViewById<EditText>(R.id.udateTv)
        val timeEditText = mDialogView.findViewById<EditText>(R.id.utimeTv)
        val linkEditText = mDialogView.findViewById<EditText>(R.id.uZoomLinkTv)

        System.out.println(subject+courseId+grade)
        subjectEditText.setText(subject)
        gradeEditText.setText(grade)
        dateEditText.setText(date)
        timeEditText.setText(time)
        linkEditText.setText(zoomLink)

        val alertDialog = mDialog.create()
        alertDialog.show()

        val updateBtn=mDialogView.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.updateClass)
        updateBtn.setOnClickListener {
            updateCourseData(
                courseId,
                subjectEditText.text.toString(),
                gradeEditText.text.toString(),
                linkEditText.text.toString(),
                dateEditText.text.toString(),
                timeEditText.text.toString()
            )

            Toast.makeText(applicationContext,"Course updated", Toast.LENGTH_LONG).show()

            binding.textView30.text = subjectEditText.text.toString()
            binding.textView19.text = dateEditText.text.toString()
            binding.textView44.text = timeEditText.text.toString()
            binding.textView22.text = linkEditText.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateCourseData(
        courseId: String,
        subject: String,
        grade: String,
        date: String,
        time: String,
        zoomLink: String
    ) {
        val db = FirebaseDatabase.getInstance().getReference("course").child(courseId)
        val courseInfo = courseModel(courseId,subject, grade, zoomLink, date, time, getUserId())

        db.setValue(courseInfo).addOnCompleteListener {
            Toast.makeText(this, "Data inserted to DB successfully", Toast.LENGTH_LONG).show()
        }.addOnFailureListener { err ->
            Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun getUserId(): String {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        return userId ?: ""
    }
}
