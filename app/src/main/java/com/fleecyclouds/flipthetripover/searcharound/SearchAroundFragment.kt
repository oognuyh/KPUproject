package com.fleecyclouds.flipthetripover.searcharound

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.fleecyclouds.flipthetripover.data.LocationBasedContent
import com.fleecyclouds.flipthetripover.R
import com.fleecyclouds.flipthetripover.data.LocationBasedVO
import com.fleecyclouds.flipthetripover.utils.TourAPIHelper
import com.google.android.gms.location.*
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import kotlinx.android.synthetic.main.fragment_search_around.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: 여기서 api를 한번에 호출하고 contenttypeid를 통해서 해당 섹션에서 visible 설정

class SearchAroundFragment : Fragment(), SearchAroundListener {
    val TAG = "SearchAroundFragment"

    private lateinit var tMapView:TMapView
    private lateinit var fusedLocationClient: FusedLocationProviderClient // 위치를 받기 위한 객체
    private lateinit var searchAroundViewPagerAdapter: SearchAroundViewPagerAdapter
    private lateinit var searchAroundViewPager: ViewPager

    var sights = mutableListOf<LocationBasedContent>()
    var leports = mutableListOf<LocationBasedContent>()
    var shoppings = mutableListOf<LocationBasedContent>()
    var culturalFacilities = mutableListOf<LocationBasedContent>()

    var latitude:Double = 0.0
    var longitude:Double = 0.0

    val PERMISSION_ID = 42

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_around, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        tMapView = view.findViewById(R.id.search_around_tmapview)
        tMapView.setSKTMapApiKey(getString(R.string.TMAP_API_KEY))
        tMapView.setTrackingMode(true)
        tMapView.setSightVisible(true)
        tMapView.zoomLevel = 13

        getLastLocation()

    }

    // TODO 수정 필요 리사이클러 아이템 중복
    private fun allContentListClear() {
        sights.clear()
        leports.clear()
        shoppings.clear()
        culturalFacilities.clear()
    }

    private fun requestAll() {
        allContentListClear()

        val response = TourAPIHelper.getLocationBasedList(pageNo = 1, mapX = longitude, mapY = latitude) // 주변 관광지 요청
        response.enqueue(object : Callback<LocationBasedVO> {
            override fun onFailure(call: Call<LocationBasedVO>, t: Throwable) { // 수신 실패
                Log.d(TAG, t.message)
            }
            override fun onResponse(call: Call<LocationBasedVO>, response: Response<LocationBasedVO>) {
                if (response.isSuccessful) { // 성공적으로 받아왔다면
                    with (response.body()?.response?.body?.box?.contents) {
                        Log.d(TAG, this.toString())
                        this?.map {
                            when (it.contenttypeid.toString()) {
                                "12" ->
                                    sights.add(it)
                                "28" ->
                                    leports.add(it)
                                "38" ->
                                    shoppings.add(it)
                                else -> Log.d(TAG, "pass")
                            }
                        }
                        searchAroundViewPagerAdapter = SearchAroundViewPagerAdapter(childFragmentManager)
                        searchAroundViewPager = view!!.findViewById(R.id.search_around_viewpager)
                        searchAroundViewPager.adapter = searchAroundViewPagerAdapter
                        search_around_tablayout.setupWithViewPager(searchAroundViewPager)
                        Log.d(TAG, "first $sights")
                    }
                }
            }

        })
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Granted. Start getting the location information
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        latitude = location.latitude
                        longitude = location.longitude
                        tMapView.setLocationPoint(longitude, latitude)
                        tMapView.setCenterPoint(longitude, latitude)

                        requestAll()

                    }
                }
            } else {
                Toast.makeText(context, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 10000
        mLocationRequest.fastestInterval = 5000
        mLocationRequest.numUpdates = 1

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.requestLocationUpdates(
            mLocationRequest, locationCallback,
            Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation: Location = locationResult.lastLocation
            latitude = lastLocation.latitude
            longitude = lastLocation.longitude
        }
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        requestNewLocationData()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    fun createMarkers(data: List<LocationBasedContent>) {
        data.map {
            val tMapMarkerItem = TMapMarkerItem()
            tMapMarkerItem.setPosition(0.5f, 1.0f)
            tMapMarkerItem.tMapPoint = TMapPoint(it.mapy!!, it.mapx!!)
            tMapMarkerItem.name = it.title

            tMapView.addMarkerItem(it.contentid.toString() , tMapMarkerItem)
        }
    }

    fun removeMarkers() {
        tMapView.removeAllMarkerItem()
    }

    override fun showMarkers(type: String) {
        removeMarkers()

        when (type) {
            "leports" ->
                createMarkers(leports)
            "shoppings" ->
                createMarkers(shoppings)
            "sights" ->
                createMarkers(sights)
        }
    }
}