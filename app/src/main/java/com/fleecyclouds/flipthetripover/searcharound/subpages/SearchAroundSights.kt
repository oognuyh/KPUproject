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
import kotlinx.android.synthetic.main.search_around_sights.*

// TODO: recyclerview를 통해 관광지 출력시키고 부모 프레그먼트 맵뷰에 마커 생성
class SearchAroundSights : Fragment(){
    private val TAG = "SearchAroundSights"
    private lateinit var searchAroundListener: SearchAroundListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_around_sights, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchAroundListener = parentFragment as SearchAroundListener
    }

    override fun onResume() {
        super.onResume()
        searchAroundListener.showMarkers("sights")
        Log.d(TAG, (parentFragment as SearchAroundFragment).sights.toString())
        val longitude = (parentFragment as SearchAroundFragment).longitude
        val latitude = (parentFragment as SearchAroundFragment).latitude

        val adapter = SearchAroundRecyclerViewAdapter(requireContext(), (parentFragment as SearchAroundFragment).sights, longitude, latitude)

        val layoutManager = LinearLayoutManager(context)

        search_around_sights_recyclerView.layoutManager = layoutManager
        search_around_sights_recyclerView.setHasFixedSize(true)
        search_around_sights_recyclerView.adapter = adapter
    }

    override fun onPause() {
        super.onPause()
    }
}