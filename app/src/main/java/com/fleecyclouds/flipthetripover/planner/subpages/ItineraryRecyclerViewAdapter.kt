package com.fleecyclouds.flipthetripover.planner.subpages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fleecyclouds.flipthetripover.R
import com.fleecyclouds.flipthetripover.planner.data.Content
import com.github.vipulasri.timelineview.TimelineView
import kotlinx.android.synthetic.main.itinerary_cardview.view.*
import kotlinx.android.synthetic.main.itinerary_cardview_footer.view.*

// TODO: 코드 개선
class ItineraryRecyclerViewAdapter(val date: String, val listener: ItineraryListener, private var contents: ArrayList<Content>)  : RecyclerView.Adapter<ItineraryRecyclerViewAdapter.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if(position != contents.size) TimelineView.getTimeLineViewType(position, itemCount - 1) else R.layout.itinerary_cardview_footer
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.itinerary_cardview, parent, false)
        if (viewType == R.layout.itinerary_cardview_footer)
            view = LayoutInflater.from(parent.context).inflate(R.layout.itinerary_cardview_footer, parent, false)

        return ViewHolder(view, viewType)
    }

    override fun getItemCount(): Int {
        return contents.size + 1 // to create add cardview
    }

    fun addContent(content: Content) {
        contents.add(content)
        notifyItemInserted(contents.size)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position != contents.size) {
            val content = contents[position]
            holder.bind(content)
        }
        else {
            holder.add.setOnClickListener {
                listener.showSearchFragment(date)
            }
        }
    }

    class ViewHolder(view: View, viewType: Int) : RecyclerView.ViewHolder(view) {
        val name = view.itinerary_card_name
        val timeline = view.timeline
        val add = view.itinerary_card_add

        init {
            if (viewType != R.layout.itinerary_cardview_footer)
                timeline.initLine(viewType)
        }

        fun bind(content: Content) {
            name.text = content.name
        }
    }
}