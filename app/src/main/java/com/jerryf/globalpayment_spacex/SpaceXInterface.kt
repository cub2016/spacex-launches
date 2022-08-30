package com.jerryf.globalpayment_spacex

import com.jerry.globalpayments_spacex.jsondataclasses.SpaceX
import retrofit2.Call
import retrofit2.http.GET

interface SpaceXInterface {
    @get:GET("/v2/url?u=https-3A__api.spacexdata.com_v3_launches&d=DwIGAg&c=BioHiDP8cpFFEOWoiyRJQw&r=M5ovAwQStAGwFvmbILaMZblwXN9t1CC7uqcrgIglzP0&m=822nQLa2kSDzy_Ix9UUqXp_XtbpVjoxLBeBHzmhf1952grao08ZFPYk2huF0M1JD&s=0ngZ4vLtmB_Dp_ZVgOoRq0M1W0jizQ7lUobWqZ2U9U4&e=")
    val flightList: Call<List<SpaceX>>?
}