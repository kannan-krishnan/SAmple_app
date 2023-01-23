package com.example.sample_app.Model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * Created by #kannanpvm007 on  02/09/22.
 */
@Keep
class NotificationMbo {


    data class PushNotification (

        @SerializedName("data" ) var data : Data? = Data()

    )
    @Keep
    data class Data (

        @SerializedName("body"      ) var body       : String? = null,
        @SerializedName("date"      ) var date       : String? = null,
        @SerializedName("title"     ) var title      : String? = null,
        @SerializedName("message"   ) var message    : String? = null,
        @SerializedName("msg_type"  ) var msg_type   : String? = null,
        @SerializedName("img_url"   ) var img_url    : String? = null,
        @SerializedName("item_type" ) var item_type  : String? = null,
        @SerializedName("id"        ) var id         : String? = null,
        @SerializedName("meeting_id") var meeting_id : String? = null,
        @SerializedName("doc_photo" ) var doc_photo  : String? = null,
        @SerializedName("doc_name"  ) var doc_name   : String? = null,
        @SerializedName("doc_sp"    ) var doc_sp     : String? = null,

    )
}