package com.ck.tutorialxv1.activities.sehan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ck.tutorialxv1.R
import com.ck.tutorialxv1.adapter.sehan.quizAdaptor
import com.ck.tutorialxv1.models.sehan.Quiz
import com.google.firebase.database.*

class ViewAllQuizStd : AppCompatActivity() {

    private lateinit var recycleView1 : RecyclerView
    private lateinit var quizList : ArrayList<Quiz>
    private lateinit var dbRef : DatabaseReference
    private lateinit var tvloading : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all_quiz_std)

        recycleView1 = findViewById(R.id.recycleView2)
        recycleView1.layoutManager = LinearLayoutManager(this)
        recycleView1.setHasFixedSize(true)
        tvloading = findViewById(R.id.tvLoadingData1)

        quizList = arrayListOf<Quiz>()




        getQuizData()





    }
    private fun getQuizData() {
        recycleView1.visibility = View.GONE
        tvloading.visibility= View.VISIBLE

        //database reference
        dbRef = FirebaseDatabase.getInstance().getReference("Quizes")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                quizList.clear()
                if(snapshot.exists()){
                    for(quizSnap in snapshot.children){
                        val quizData = quizSnap.getValue(Quiz::class.java)
                        quizList.add(quizData!!)
                    }

                    val mAdapter = quizAdaptor(quizList)
                    recycleView1.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : quizAdaptor.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@ViewAllQuizStd, SingleQuizStd::class.java)

                            intent.putExtra("id",quizList[position].id)
                            intent.putExtra("title",quizList[position].title)
                            intent.putExtra("grade",quizList[position].grade)
                            intent.putExtra("course",quizList[position].course)
                           // intent.putExtra("QuestionList",quizList[position].questions)
                            startActivity(intent)

                        }

                    })
                    recycleView1.visibility = View.VISIBLE
                    tvloading.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })




    }
}