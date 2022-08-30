package com.jerry.globalpayments_spacex.jsondataclasses

import com.google.gson.annotations.SerializedName


data class LaunchFailureDetails (

  @SerializedName("time"     ) var time     : Int?    = null,
  @SerializedName("altitude" ) var altitude : String? = null,
  @SerializedName("reason"   ) var reason   : String? = null

)