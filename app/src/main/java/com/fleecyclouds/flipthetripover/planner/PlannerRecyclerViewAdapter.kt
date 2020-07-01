package com.fleecyclouds.flipthetripover.planner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fleecyclouds.flipthetripover.R
import com.fleecyclouds.flipthetripover.planner.data.Itinerary
import kotlinx.android.synthetic.main.planner_cardview.view.*

class PlannerRecyclerViewAdapter(val itineraries: MutableList<Itinerary>) : RecyclerView.Adapter<PlannerRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.planner_cardview, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = itineraries.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itinerary = itineraries[position]
        holder.bind(itinerary)
    }

    fun addItinerary(itinerary: Itinerary) {
        itineraries.add(itinerary)
        notifyItemInserted(itineraries.size)
    }

    fun removeItinerary(position: Int) {
        itineraries.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itineraries.size)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.planner_card_image
        val title = view.planner_card_title
        val date = view.planner_card_date

        fun bind(itinerary: Itinerary) {
            val date = "${itinerary.startDate} ~ ${itinerary.endDate}"
            // TODO: load image using Picasso
            // TODO: Clickable

            this.title.text = itinerary.title
            this.date.text = date
        }
    }

}