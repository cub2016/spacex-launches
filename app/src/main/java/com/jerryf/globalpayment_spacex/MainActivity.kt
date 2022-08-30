package com.jerryf.globalpayment_spacex

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jerry.globalpayments_spacex.jsondataclasses.SpaceX


const val BASE_URL = "https://urldefense.proofpoint.com/"

class MainActivity : AppCompatActivity(), Observer<List<SpaceX>> {

    private var spaceXDataViewModel: SpaceXDataViewModel? = null
    private var loadProgress: ProgressBar? = null
    private var recyclerView: RecyclerView? = null
    private var recyclerAdapter: SpaceXRecyclerAdapter? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    private var missionDetailsFrame: AppCompatTextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        loadProgress = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerview)

        missionDetailsFrame = findViewById<AppCompatTextView>(R.id.mission_details)

        linearLayoutManager = LinearLayoutManager(this@MainActivity)
        linearLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
        recyclerView!!.layoutManager = linearLayoutManager
        spaceXDataViewModel = ViewModelProvider(this).get(SpaceXDataViewModel::class.java)
        loadProgress!!.visibility = View.VISIBLE
        missionDetailsFrame?.visibility = View.INVISIBLE
    }

    override fun onResume() {
        super.onResume()
        spaceXDataViewModel!!.setObserver(this, this)
    }

    override fun onPause() {
        super.onPause()
        spaceXDataViewModel!!.removeObserver(this)
    }

    override fun onChanged(t: List<SpaceX>?) {
        loadProgress!!.visibility = View.GONE
        missionDetailsFrame?.visibility = View.VISIBLE

        t ?: return

        if (resources.configuration.orientation == ORIENTATION_PORTRAIT) {
            recyclerAdapter = SpaceXRecyclerAdapter(
                this@MainActivity, t,
                recyclerView!!.layoutManager
            )
        } else {
            recyclerAdapter = SpaceXRecyclerAdapter(
                this@MainActivity, t,
                recyclerView!!.layoutManager, missionDetailsFrame
            )
        }
        recyclerView!!.adapter = recyclerAdapter
    }
}
