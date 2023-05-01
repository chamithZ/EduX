package com.ck.tutorialxv1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ck.tutorialxv1.R
import com.ck.tutorialxv1.databinding.ActivityRegisterBinding
import com.ck.tutorialxv1.databinding.CourselistBinding
import com.ck.tutorialxv1.models.courseModel


class CourseAdapter (private val courseList: ArrayList<courseModel>) :
    RecyclerView.Adapter<CourseAdapter.ViewHolder>(){







    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.courselist,parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CourseAdapter.ViewHolder, position: Int) {

        val currentCourse=courseList[position]
        holder.tvCourseName.text= currentCourse.subject

        holder.grade.text=currentCourse.grade
                }




    override fun getItemCount(): Int {
        return courseList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val tvCourseName : TextView =itemView.findViewById(R.id.tvCourseName)
            val grade :TextView=itemView.findViewById(R.id.grade)
    }



}