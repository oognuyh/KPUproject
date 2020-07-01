package com.fleecyclouds.flipthetripover.planner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fleecyclouds.flipthetripover.R
import com.fleecyclouds.flipthetripover.planner.data.Content
import com.fleecyclouds.flipthetripover.planner.data.Itinerary
import com.fleecyclouds.flipthetripover.planner.data.SubItinerary
import com.fleecyclouds.flipthetripover.utils.SwipeToDeleteCallback
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.skt.Tmap.TMapPoint
import kotlinx.android.synthetic.main.fragment_planner.*
import java.util.*
import kotlin.collections.ArrayList

/* TODO :
 * RecyclerView를 통해서 내 일정을 모두 출력하여 관리할 수 있게 함
 * FloatingActionButton을 통해서 새로운 여정을 추가함
 * RecyclerView의 Item은 Swipe를 통해서 delete할 수 있게 구현
 */
class PlannerFragment : Fragment() {
    private lateinit var picker: MaterialDatePicker<androidx.core.util.Pair<Long, Long>>
    private lateinit var uid: String
    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var databaseReference: DatabaseReference = firebaseDatabase.reference
    private var itineraries = ArrayList<Itinerary>()
    private lateinit var adapter: PlannerRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_planner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 사용자의 uid 가져오기
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) this.uid = user.uid

        // load itineraries
        addChildEvent()

        adapter = PlannerRecyclerViewAdapter(itineraries)
        val layoutManager = LinearLayoutManager(requireContext())

        planner_recyclerview.layoutManager = layoutManager
        planner_recyclerview.setHasFixedSize(true)
        planner_recyclerview.adapter = adapter

        // setup recyclerview swipe
        enableSwipe()

        // setup DatePicker Dialog
        setupDatePickerDialog()

        // TODO: DATEPICKERDIALOG 라이브러리 변경 예정 - 너무 느리고 쓰기 불편 -> 고민 중
        // builder.setTitleText()

        picker.addOnPositiveButtonClickListener {
            val action = PlannerFragmentDirections.actionFragmentPlannerToAddItineraryFragment(it.first!!, it.second!!)
            findNavController().navigate(action)
            picker.dismiss()
        }
        picker.addOnNegativeButtonClickListener { picker.dismiss() }

        // setup FloatingActionButton OnclickListener
        planner_fab_add.setOnClickListener {
            // java.lang.IllegalStateException: Fragment already added 방지
            if (!picker.isAdded)
                picker.show(requireActivity().supportFragmentManager, toString())
        }
    }

    private fun setupDatePickerDialog() {
        val builder = MaterialDatePicker.Builder.dateRangePicker()
        val constraintsBuilder = CalendarConstraints.Builder()
        val calendar = Calendar.getInstance()

        // 이전 날짜 선택 제한 & 이번달로부터 2년까지만 출력하도록 설정
        constraintsBuilder.setValidator(DateValidatorPointForward.now())
        constraintsBuilder.setStart(calendar.timeInMillis)
        calendar.roll(Calendar.YEAR, 2)
        constraintsBuilder.setEnd(calendar.timeInMillis)

        builder.setCalendarConstraints(constraintsBuilder.build())

        picker = builder.build()
    }

    private fun enableSwipe() {
        val swipeHandler = object : SwipeToDeleteCallback(context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = planner_recyclerview.adapter as PlannerRecyclerViewAdapter
                adapter.removeItinerary(viewHolder.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(planner_recyclerview)
    }

    private fun addChildEvent() {
        databaseReference.child("itinerary").child(uid).addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val itinerary = p0.getValue(Itinerary::class.java)
                itineraries.add(itinerary!!)
                adapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("Not yet implemented")
            }

        })
    }

}

