package com.fleecyclouds.flipthetripover.searcharound.subpages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fleecyclouds.flipthetripover.R
import com.fleecyclouds.flipthetripover.searcharound.SearchAroundListener
import com.fleecyclouds.flipthetripover.searcharound.SearchAroundRecyclerViewAdapter
import com.fleecyclouds.flipthetripover.searcharound.SearchAroundFragment
import kotlinx.android.synthetic.main.search_around_leports.*

class SearchAroundLeports : Fragment() {
    private val TAG = "SearchAroundLeports"
    private lateinit var searchAroundListener: SearchAroundListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_around_leports, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchAroundListener = parentFragment as SearchAroundListener
    }

    override fun onResume() {
        super.onResume()
        searchAroundListener.showMarkers("leports")
        Log.d(TAG, (parentFragment as SearchAroundFragment).leports.toString())

        val longitude = (parentFragment as SearchAroundFragment).longitude
        val latitude = (parentFragment as SearchAroundFragment).latitude
        val adapter = SearchAroundRecyclerViewAdapter(requireContext(), (parentFragment as SearchAroundFragment).leports, longitude, latitude)

        val layoutManager = LinearLayoutManager(context)

        search_around_leports_recyclerView.layoutManager = layoutManager
        search_around_leports_recyclerView.setHasFixedSize(true)
        search_around_leports_recyclerView.adapter = adapter
    }

    override fun onPause() {
        super.onPause()
    }
}