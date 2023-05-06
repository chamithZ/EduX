package com.ck.tutorialxv1.activities.chamith

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ck.tutorialxv1.adapter.chamith.CourseAdapter
import com.ck.tutorialxv1.databinding.ActivityCourseFetchBinding
import com.ck.tutorialxv1.models.courseModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
class FetchingAllCourse : AppCompatActivity() {

    private lateinit var courseList: ArrayList<courseModel>
    private lateinit var binding: ActivityCourseFetchBinding
    private lateinit var db: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseFetchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        userId = auth.currentUser!!.uid

        courseList = arrayListOf<courseModel>()

        binding.rvCourse.layoutManager = LinearLayoutManager(this)

        getCourseData()
    }

    private fun getCourseData() {


        binding.rvCourse.visibility = View.GONE
        binding.tvLoadingData.visibility = View.VISIBLE

        db = FirebaseDatabase.getInstance().getReference("course")

        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                courseList.clear()
                if (snapshot.exists()) {
                    for (courseSnap in snapshot.children) {
                        // check if the data is not null
                        if (courseSnap.value != null) {
                            val courseData = courseSnap.getValue(courseModel::class.java)
                            // check if the courseData is not null
                            if (courseData != null) {
                                courseList.add(courseData)
                            }
                        }
                    }
                    val mAdapter = CourseAdapter(courseList)
                    binding.rvCourse.adapter = mAdapter

                    mAdapter.setOnClickListener(object : CourseAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {

                            val intent = Intent(
                                this@FetchingAllCourse,
                                StdCourseOverview::class.java
                            )
                            intent.putExtra("courseId", courseList[position].courseId)
                            intent.putExtra("subject", courseList[position].subject)
                            intent.putExtra("grade", courseList[position].grade)
                            intent.putExtra("time", courseList[position].time)
                            intent.putExtra("date", courseList[position].date)
                            intent.putExtra("zoomLink", courseList[position].zoomLink)
                            intent.putExtra("userId",courseList[position].userId)
                            startActivity(intent)

                        }

                    })

                    binding.rvCourse.visibility = View.VISIBLE
                    binding.tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }

        })

    }
}
