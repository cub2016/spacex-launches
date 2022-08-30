package com.jerry.globalpayments_spacex.jsondataclasses

import com.google.gson.annotations.SerializedName


data class Timeline (

  @SerializedName("webcast_liftoff" ) var webcastLiftoff : Int? = null

)