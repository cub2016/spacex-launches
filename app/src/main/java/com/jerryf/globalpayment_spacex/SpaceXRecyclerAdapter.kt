package com.jerryf.globalpayment_spacex

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.jerry.globalpayments_spacex.jsondataclasses.SpaceX
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

class SpaceXRecyclerAdapter : RecyclerView.Adapter<SpaceXRecyclerAdapter.ViewHolder> {

    private val context: Context
    private var spaceXDataModels: List<SpaceX>?
    private val layoutManager: RecyclerView.LayoutManager?
    private val missionDetailTextView: AppCompatTextView?

    constructor(
        context: Context,
        spaceXDataModels: List<SpaceX>,
        layoutManager: RecyclerView.LayoutManager?
    ) {
        this.context = context
        this.spaceXDataModels = spaceXDataModels
        this.layoutManager = layoutManager
        missionDetailTextView = null
        initData()
    }

    constructor(
        context: Context,
        spaceXDataModels: List<SpaceX>?,
        layoutManager: RecyclerView.LayoutManager?,
        missionDetailTextView: AppCompatTextView?
    ) {
        this.context = context
        this.spaceXDataModels = spaceXDataModels
        this.layoutManager = layoutManager
        this.missionDetailTextView = missionDetailTextView
        initData()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false)
        return ViewHolder(view)
    }

    private val descending = object : Comparator<SpaceX> {
        override fun compare(o1: SpaceX?, o2: SpaceX?): Int {
            return o2?.launchDateUnix?.compareTo(o1?.launchDateUnix ?: 0) ?: 0
        }
    }

    private val ascending = object : Comparator<SpaceX> {
        override fun compare(o1: SpaceX?, o2: SpaceX?): Int {
            return o1?.launchDateUnix?.compareTo(o2?.launchDateUnix ?: 0) ?: 0
        }
    }

    private fun initData() {
        if (missionDetailTextView != null) {
           spaceXDataModels?.sortedWith(descending).also { spaceXDataModels = it }
        } else {
            spaceXDataModels?.sortedWith(ascending).also { spaceXDataModels = it }
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {

        val spaceXDataModel = spaceXDataModels?.get(i)

        viewHolder.mission_name.text = spaceXDataModel?.missionName
        viewHolder.rocket_name.text = spaceXDataModel?.rocket?.rocketName
        viewHolder.site_name.text = spaceXDataModel?.launchSite?.siteNameLong
        viewHolder.launchDateUtc.text = spaceXDataModel?.launchDateUtc
        viewHolder.missionDetails?.text = spaceXDataModel?.details ?: "Details Not Available"

        val picasso = Picasso.get()
        var requestCreator: RequestCreator
        if (spaceXDataModel?.links?.missionPatch != null) {
            requestCreator = picasso.load(spaceXDataModel.links?.missionPatch)
        } else {
            requestCreator = picasso.load(R.drawable.download)
        }
        requestCreator.resize(100, 100)
            .into(viewHolder.mission_patch)
    }

    override fun getItemCount(): Int {
        return spaceXDataModels?.size ?: 0
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val mission_patch: AppCompatImageView
        val mission_name: AppCompatTextView
        val rocket_name: AppCompatTextView
        val site_name: AppCompatTextView
        val launchDateUtc: AppCompatTextView
        val missionDetails: AppCompatTextView?
        var itemSelected: Boolean = false

        init {
            mission_patch = itemView.findViewById(R.id.mission_patch)
            mission_name = itemView.findViewById(R.id.mission_name)
            rocket_name = itemView.findViewById(R.id.rocket_name)
            site_name = itemView.findViewById(R.id.site_name)
            launchDateUtc = itemView.findViewById(R.id.launch_date)
            missionDetails = itemView.findViewById(R.id.mission_details)

            val onClickListenerP = View.OnClickListener { v ->
//                v?.findViewById<AppCompatTextView>(R.id.mission_details) ?: return

                for (i in 0..(layoutManager?.childCount ?: 0)) {
                    val view = layoutManager?.getChildAt(i)
                        ?.findViewById<AppCompatTextView>(R.id.mission_details) ?: continue
                    if (!layoutManager.getChildAt(i)!!.equals(v)) view.visibility = View.GONE
                }
                v.findViewById<AppCompatTextView>(R.id.mission_details).let {
                    it.visibility = when (it.visibility) {
                        View.GONE -> {
                            View.VISIBLE
                        }
                        else -> {
                            View.GONE
                        }
                    }
                }
            }

            val onClickListenerL = View.OnClickListener { v ->
                itemSelected = !itemSelected

                if (itemSelected) {
                    var index: Int
                    for (i: Int in 0..(layoutManager?.childCount ?: 0)) {
                        val view = layoutManager?.getChildAt(i)

                        if (layoutManager?.getChildAt(i)?.equals(v) ?: false) {
                            missionDetailTextView?.text =
                                spaceXDataModels?.get(i)?.details ?: "Details not available"
                            break
                        }
                    }

                    for (i in 0..(layoutManager?.childCount ?: 0)) {
                        val view = layoutManager?.getChildAt(i)
                        view?.setBackground(ColorDrawable(0xffffffff.toInt()))
                    }

                    v?.setBackground(ColorDrawable(0xff777777.toInt()))
                } else {
                    missionDetailTextView?.text = ""
                    v.background = ColorDrawable(0xffffffff.toInt())
                }
            }

            if (this@SpaceXRecyclerAdapter.missionDetailTextView == null) {
                itemView.setOnClickListener(onClickListenerP)
            } else {
                itemView.setOnClickListener(onClickListenerL)
            }
        }
    }
}