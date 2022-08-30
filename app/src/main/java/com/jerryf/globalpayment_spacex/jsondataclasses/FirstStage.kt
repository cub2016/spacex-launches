package com.jerry.globalpayments_spacex.jsondataclasses

import com.google.gson.annotations.SerializedName
import com.jerry.globalpayments_spacex.jsondataclasses.Cores


data class FirstStage (

  @SerializedName("cores" ) var cores : ArrayList<Cores> = arrayListOf()

)