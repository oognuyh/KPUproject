package com.fleecyclouds.flipthetripover.planner

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fleecyclouds.flipthetripover.R
import com.fleecyclouds.flipthetripover.planner.data.Content
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPOIItem
import kotlinx.android.synthetic.main.fragment_add_content.*
import java.util.ArrayList

class AddContentFragment(val date: String) : Fragment() {
    private lateinit var adapter: AddContentRecyclerViewAdapter
    private val contents = ArrayList<Content>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        setupClickedEvent()
    }

    private fun setupRecyclerView() {
        Log.d("addc", date)
        adapter = AddContentRecyclerViewAdapter(date, contents, parentFragment as AddContentListener)

        val layoutInflater = LinearLayoutManager(requireContext())
        add_content_recyclerview.layoutManager = layoutInflater
        add_content_recyclerview.setHasFixedSize(true)

        add_content_recyclerview.adapter = adapter
    }

    private  fun setupClickedEvent() {
        add_content_search.setOnClickListener {
            if (!add_content_input.text.isNullOrEmpty()) {
                contents.clear()
                val tMapData = TMapData()
                tMapData.findAllPOI(add_content_input.text.toString(), object : TMapData.FindAllPOIListenerCallback {
                    override fun onFindAllPOI(p0: ArrayList<TMapPOIItem>?) {
                        p0?.forEach {
                            val content = Content(it.poiName, it.poiAddress.replace("null", ""), it.poiPoint.longitude, it.poiPoint.latitude)
                            contents.add(content)
                            Log.d("AddContentFragment", "${it.poiName}, ${it.poiAddress.replace("null", "")}, ${it.poiPoint.longitude}, ${it.poiPoint.latitude}")
                        }
                        activity?.runOnUiThread {
                            add_content_recyclerview.adapter = adapter
                        }
                    }
                })

                // 키보드 내리기
                val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(add_content_input.windowToken, 0)
            }
        }

    }
}