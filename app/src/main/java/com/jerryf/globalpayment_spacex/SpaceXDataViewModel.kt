package com.jerryf.globalpayment_spacex

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.jerry.globalpayments_spacex.jsondataclasses.SpaceX
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class SpaceXDataViewModel() : ViewModel() {

    private val spaceXDataModels = spaceXLiveData()

    private inner class spaceXLiveData : LiveData<List<SpaceX>>() {
        public override fun setValue(value: List<SpaceX>?) {
            super.setValue(value)
        }

        public override fun postValue(value: List<SpaceX>?) {
            super.postValue(value)
        }
    }

    public fun setObserver(owner: LifecycleOwner, observer: Observer<in List<SpaceX>>) {
        spaceXDataModels.observe(owner, observer)
        getData()
    }

    public fun removeObserver(observer: Observer<in List<SpaceX>>){
        spaceXDataModels.removeObserver(observer)
    }

    private fun getData() {
        val service = ServiceGenerator.retrofit!!.create(
            SpaceXInterface::class.java
        )
        val call = service.flightList

        call!!.enqueue(object : Callback<List<SpaceX>> {
            override fun onResponse(
                call: Call<List<SpaceX>>,
                response: Response<List<SpaceX>>
            ) {
                spaceXDataModels.setValue(response.body())
            }

            override fun onFailure(call: Call<List<SpaceX>>, t: Throwable) {
                Log.e("MainView", "retrofit call failed")
                Executors.newScheduledThreadPool(1).schedule({
                    this@SpaceXDataViewModel.getData()
                }, 30, TimeUnit.SECONDS)
            }
        })
    }
}