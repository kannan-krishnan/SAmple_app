package com.example.sample_app.service

import com.example.sample_app.Model.SendPushMessage
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Url


/**
 * Created by #kannanpvm007 on  10/01/23.
 */
class ApiClient {
    private val BASE_URL = "https://fcm.googleapis.com/"
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit
    }
}
interface ApiInterface {
    @POST("fcm/send")
    fun sendNotification(@HeaderMap headers: Map<String, String> ,@Body root: SendPushMessage?): Call<JsonElement>?

    @GET
    fun directions(@Url url: String): Call<JsonElement>?
}