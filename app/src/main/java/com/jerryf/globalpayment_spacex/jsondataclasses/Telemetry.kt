package com.jerry.globalpayments_spacex.jsondataclasses

import com.google.gson.annotations.SerializedName


data class Telemetry (

  @SerializedName("flight_club" ) var flightClub : String? = null

)