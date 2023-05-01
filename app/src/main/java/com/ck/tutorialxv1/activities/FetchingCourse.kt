package com.ck.tutorialxv1.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ck.tutorialxv1.R
import com.ck.tutorialxv1.adapter.CourseAdapter
import com.ck.tutorialxv1.databinding.ActivityCourseFetchBinding
import com.ck.tutorialxv1.databinding.ActivityFetchingCourseBinding
import com.ck.tutorialxv1.databinding.ActivityRegisterBinding
import com.ck.tutorialxv1.models.courseModel
import com.google.firebase.database.*

class FetchingCourse : AppCompatActivity() {

    private lateinit var courseList: ArrayList<courseModel>
    private lateinit var binding: ActivityCourseFetchBinding
    private lateinit var db:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseFetchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        courseList = arrayListOf<courseModel>()

        binding.rvCourse.layoutManager =LinearLayoutManager(this)

        getCourseData()
    }
    private fun getCourseData(){
        binding.rvCourse.visibility= View.GONE
        binding.tvLoadingData.visibility=View.VISIBLE

        db=FirebaseDatabase.getInstance().getReference("course")
        db.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                courseList.clear()
                if (snapshot.exists()) {
                    for (courseSnap in snapshot.children) {
                        val courseData=courseSnap.getValue(courseModel::class.java)
                        courseList.add(courseData!!)
                    }
                    val mAdapter=CourseAdapter(courseList)
                    binding.rvCourse.adapter=mAdapter

                    binding.rvCourse.visibility=View.VISIBLE
                    binding.tvLoadingData.visibility=View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}