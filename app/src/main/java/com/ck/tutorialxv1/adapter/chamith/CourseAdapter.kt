package com.ck.tutorialxv1.adapter.chamith

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ck.tutorialxv1.R
import com.ck.tutorialxv1.models.courseModel


class CourseAdapter (private val courseList: ArrayList<courseModel>) :
    RecyclerView.Adapter<CourseAdapter.ViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnClickListener(clickListener: onItemClickListener){
        mListener= clickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.courselist,parent, false)
        return ViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentCourse=courseList[position]
        holder.tvCourseName.text= currentCourse.subject
        holder.grade.text=currentCourse.grade

                }




    override fun getItemCount(): Int {
        return courseList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
            val tvCourseName : TextView =itemView.findViewById(R.id.subject2)
            val grade :TextView=itemView.findViewById(R.id.subject3)


            init{
                itemView.setOnClickListener {
                    clickListener.onItemClick(adapterPosition)
                }
            }
    }

}