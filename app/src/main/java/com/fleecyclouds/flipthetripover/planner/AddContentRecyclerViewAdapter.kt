package com.fleecyclouds.flipthetripover.planner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fleecyclouds.flipthetripover.R
import com.fleecyclouds.flipthetripover.planner.data.Content
import kotlinx.android.synthetic.main.add_content_item.view.*

class AddContentRecyclerViewAdapter(val date: String, val contents: ArrayList<Content>, val listener: AddContentListener) : RecyclerView.Adapter<AddContentRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.add_content_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = contents.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val content = contents[position]
        holder.bind(content)
        holder.itemView.setOnClickListener{
            listener.removeSearchFragment(date, content)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.add_content_item_title
        val address = view.add_content_item_address

        fun bind(content: Content) {
            this.title.text = content.name
            this.address.text = content.address
        }
    }
}