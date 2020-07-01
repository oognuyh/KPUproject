package com.fleecyclouds.flipthetripover.planner.subpages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fleecyclouds.flipthetripover.R
import com.fleecyclouds.flipthetripover.planner.AddContentFragment
import com.fleecyclouds.flipthetripover.planner.AddItineraryFragment
import com.fleecyclouds.flipthetripover.planner.data.Content
import kotlinx.android.synthetic.main.fragment_itinerary.*

/*
 * It will be added to the viewpager of AddItineraryFragment
 */

// TODO: RecyclerView & CardView
class ItineraryFragment() : Fragment() {
    lateinit var date: String
    lateinit var adapter: ItineraryRecyclerViewAdapter
    private var contents = ArrayList<Content>()

    constructor(date: String) : this() {
        this.date = date
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_itinerary, container, false)
    }
    //(parentFragment as AddItineraryFragment).itinerary
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itinerary_date.text = date

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = ItineraryRecyclerViewAdapter(date, parentFragment as AddItineraryFragment, contents)

        val layoutManager = LinearLayoutManager(requireContext())

        itinerary_recyclerview.setHasFixedSize(true)
        itinerary_recyclerview.layoutManager = layoutManager
        itinerary_recyclerview.adapter = adapter
    }

    fun saveSubItinerary() {
        (parentFragment as AddItineraryFragment).itinerary.subItinerary!!.forEach {
            if (it.date == date) {
                it.contents = contents
            }
        }
    }

    fun addContent(content: Content) {
        adapter.addContent(content)
        (parentFragment as AddItineraryFragment).itinerary.subItinerary!!.forEach {
            if (it.date == date) {
                it.contents?.add(content)
            }
        }
        (parentFragment as ItineraryListener).showMarkers(date)
    }

    override fun onResume() {
        super.onResume()
        (parentFragment as ItineraryListener).showMarkers(date)
    }
}