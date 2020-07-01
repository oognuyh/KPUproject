package com.fleecyclouds.flipthetripover.planner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.fleecyclouds.flipthetripover.R
import com.fleecyclouds.flipthetripover.planner.data.Content
import com.fleecyclouds.flipthetripover.planner.data.Itinerary
import com.fleecyclouds.flipthetripover.planner.data.SubItinerary
import com.fleecyclouds.flipthetripover.planner.subpages.ItineraryFragment
import com.fleecyclouds.flipthetripover.planner.subpages.ItineraryListener
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPoint
import kotlinx.android.synthetic.main.fragment_add_itinerary.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

/* json arch e.g.
 * {
 *     "title" : "제주도로 떠나는 여행(1)",
 *     "startDate" : "2020-04-27",
 *     "endDate" : "2020-04-29",
 *     "subItinerary" : [
 *         {
 *             "date" : "2020-04-27",
 *             "contents" : [
 *                 {
 *                     "name" : "제주공항",
 *                     "location" : {
 *                         "longitude" : 126.126126,
 *                         "latitude" : 36.363636
 *                     }
 *                 }
 *             ]
 *         },
 *         {
 *             "date" : "2020-04-28",
 *             "contents" : [
 *                 {
 *                     "name" : "자매국수",
 *                     "location" : {
 *                         "longitude" : 126.126126,
 *                         "latitude" : 36.363636
 *                     }
 *                 }
 *             ]
 *         }
 *     ]
 * }
 */
class AddItineraryFragment : Fragment(), ItineraryListener, AddContentListener {
    val args = navArgs<AddItineraryFragmentArgs>()
    private lateinit var adapter: ItineraryViewPagerAdapter
    private lateinit var dates: ArrayList<LocalDate>
    private lateinit var searchFragment: AddContentFragment

    lateinit var itinerary: Itinerary

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_itinerary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add_itinerary_tmap.setSKTMapApiKey(getString(R.string.TMAP_API_KEY))
        add_itinerary_tmap.zoomLevel = 7

        getDateArguments()

        setupViewPager()
    }

    private fun getDateArguments() {
        dates = ArrayList<LocalDate>()

        val startDate = LocalDate.parse(convertLongToDate(args.value.startDate), DateTimeFormatter.ofPattern("yyyy.MM.dd"))
        val endDate = LocalDate.parse(convertLongToDate(args.value.endDate), DateTimeFormatter.ofPattern("yyyy.MM.dd"))
        val subItineraries = ArrayList<SubItinerary>()

        var current = startDate
        while (current.isBefore(endDate.plusDays(1))) {
            val subItinerary = SubItinerary(current.toString(), ArrayList<Content>())
            subItineraries.add(subItinerary)

            dates.add(current)
            current = current.plusDays(1)
        }

        itinerary = Itinerary(null, startDate.toString(), endDate.toString(), subItineraries)
    }

    private fun setupButtonClicked() {
        add_itinerary_ftb.setOnClickListener{

        }
    }

    private fun setupViewPager() {
        adapter = ItineraryViewPagerAdapter(childFragmentManager)
        dates.forEach {
            val itineraryFragment = ItineraryFragment(it.toString())
            adapter.addFragment(itineraryFragment)
        }
        add_itinerary_viewpager.adapter = adapter
    }

    private fun convertLongToDate(longTypeDate: Long): String {
        val date = Date(longTypeDate)
        val format = SimpleDateFormat("yyyy.MM.dd", Locale.KOREAN)
        return format.format(date)
    }

    override fun showSearchFragment(date: String) {
        searchFragment = AddContentFragment(date)
        childFragmentManager.beginTransaction().add(R.id.add_itinerary_main, searchFragment).commit()
    }

    override fun removeSearchFragment(date: String, content: Content) {
        childFragmentManager.beginTransaction().remove(searchFragment).commit()
        adapter.saveContent(date, content)
    }
    // TODO: show markers in subItinerary using Listner adapter.save()

    fun createMarkers(data: ArrayList<Content>) {
        data.map {
            val tMapMarkerItem = TMapMarkerItem()
            tMapMarkerItem.setPosition(0.5f, 1.0f)
            tMapMarkerItem.tMapPoint = TMapPoint(it.latitude!!, it.longitude!!)
            tMapMarkerItem.name = it.name

            add_itinerary_tmap.addMarkerItem(it.name , tMapMarkerItem)
        }
    }

    fun removeMarkers() {
        add_itinerary_tmap.removeAllMarkerItem()
    }

    override fun showMarkers(date: String) {
        removeMarkers()

        itinerary.subItinerary?.forEach {
            if (it.date == date) {
                Log.d("additi", it.contents.toString())
                createMarkers(it.contents!!)
            }
        }
    }
}

