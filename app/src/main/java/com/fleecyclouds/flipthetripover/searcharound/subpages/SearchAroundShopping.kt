package com.fleecyclouds.flipthetripover.searcharound.subpages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fleecyclouds.flipthetripover.R
import com.fleecyclouds.flipthetripover.searcharound.SearchAroundListener
import com.fleecyclouds.flipthetripover.searcharound.SearchAroundRecyclerViewAdapter
import com.fleecyclouds.flipthetripover.searcharound.SearchAroundFragment
import kotlinx.android.synthetic.main.search_around_shopping.*

class SearchAroundShopping : Fragment() {
    private val TAG = "SearchAroundShopping"
    private lateinit var searchAroundListener: SearchAroundListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_around_shopping, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchAroundListener = parentFragment as SearchAroundListener
    }

    override fun onStart() {
        super.onStart()
        onResume()
    }

    override fun onResume() {
        super.onResume()
        searchAroundListener.showMarkers("shoppings")
        val longitude = (parentFragment as SearchAroundFragment).longitude
        val latitude = (parentFragment as SearchAroundFragment).latitude
        val adapter = SearchAroundRecyclerViewAdapter(requireContext(), (parentFragment as SearchAroundFragment).shoppings, longitude, latitude)

        val layoutManager = LinearLayoutManager(context)

        search_around_shopping_recyclerView.layoutManager = layoutManager
        search_around_shopping_recyclerView.setHasFixedSize(true)
        search_around_shopping_recyclerView.adapter = adapter
    }

    override fun onPause() {
        super.onPause()
    }
}