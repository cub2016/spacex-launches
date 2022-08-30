package com.jerryf.globalpayment_spacex

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceGenerator {
    var retrofit: Retrofit? = null
        get() {
            gson = GsonBuilder()
                .create()
            if (field == null) {
                field = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return field
        }
        private set
    private var gson: Gson? = null
}