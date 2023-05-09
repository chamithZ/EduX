package com.ck.tutorialxv1.models.sehan

import com.ck.tutorialxv1.models.Question

class Quiz (
    var id :String = "",
    var userId : String ="",
    var title :String ="",
    var grade : String ="",
    var course : String="",
    var questions: MutableList<Question> = mutableListOf<Question>()
){
    constructor(id: String,userId : String, title: String, grade: String, course: String) : this(id,userId, title, grade, course, mutableListOf<Question>())
}