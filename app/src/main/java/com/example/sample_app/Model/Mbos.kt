package com.example.sample_app.Model

import com.google.firebase.database.PropertyName
import com.google.firebase.firestore.ServerTimestamp
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by #kannanpvm007 on  26/12/22.
 */
data class MobileNumber(
    @get:PropertyName("country_code")
    @set:PropertyName("country_code")
    var countryCode: String = "",
    var number: String = ""
)

data class UserProfile(
    var uId: String,
    @ServerTimestamp
    var createdAt: Date? = null,
    @ServerTimestamp
    var updatedAt: Date? = null,
    var image: String = "",
    @get:PropertyName("user_name")
    @set:PropertyName("user_name")
    var userName: String = "",
    var token: String = "",
    var mobile: MobileNumber? = null
)
data class SendPushMessage (

    @SerializedName("to"   ) var to   : String? = null,
    @SerializedName("data" ) var data : Data?   = Data()

)

data class Data (

    @SerializedName("body"  ) var body  : String? = null,
    @SerializedName("title" ) var title : String? = null,
    @SerializedName("key_1" ) var key1  : String? = null,
    @SerializedName("key_2" ) var key2  : String? = null

)