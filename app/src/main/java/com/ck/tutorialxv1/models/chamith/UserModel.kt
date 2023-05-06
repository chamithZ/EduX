package com.ck.tutorialxv1.models.chamith

data class UserModel(
    val name: String = "",
    val email: String = "",
    val contactNum: String = "",
    val age: String = "",
    var grade: String? = null,
    val qualification: String = "",
    val bankAc: String = "",
    val branch: String = "",
    val eduPoints:Long=0,
    val level:String="Silver",
    val qPoints:Long=0,
    val coursesEnrolled:Long=0
    )