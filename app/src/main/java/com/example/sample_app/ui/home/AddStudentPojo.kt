package com.example.sample_app.ui.home

/**
 * Created by #kannanpvm007 on  16/03/23.
 */
data class AddStudentPojo (
    val name: String? = null,
    val address: String? = null,
    val dob: String? = null,
    val doj: String? = null,
    val profile_url: String? = null,
    val session: String? = null,
    val fatherName: String? = null,
    val motherName: String? = null,
    val mobile: String? = null,
    val email: String? = null,

    /** for tracking*/
    val mgrProvideEmail: String? = null,
    val uid: String? = null,

    /***/
    val busRoot:String?=null,
    val selfUpdateTableForStudent: SelfUpdateTableForStudent?=null

)

data class SelfUpdateTableForStudent(
    val fcmToken:String?=null
)
