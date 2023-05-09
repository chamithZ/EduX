package com.ck.tutorialxv1.adapter.sehan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ck.tutorialxv1.R
import com.ck.tutorialxv1.models.sehan.Quiz


class quizAdaptor (private val quizList :ArrayList<Quiz>) :
    RecyclerView.Adapter<quizAdaptor.ViewHolder>(){

    private lateinit var mListener :onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val quizView = LayoutInflater.from(parent.context).inflate(R.layout.activity_single_quiz,parent,false)
        return ViewHolder(quizView,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentQuiz = quizList[position]
        holder.tvQuizName.text = currentQuiz.title
    }

    override fun getItemCount(): Int {
        return quizList.size
    }

    class ViewHolder(itemView:View,clickListener: onItemClickListener) :RecyclerView.ViewHolder(itemView){
            val tvQuizName : TextView = itemView.findViewById(R.id.tvQuizName)

            init {
                itemView.setOnClickListener {
                    clickListener.onItemClick(adapterPosition)
                }
            }
    }


}