package com.fleecyclouds.flipthetripover.searcharound

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fleecyclouds.flipthetripover.R
import com.fleecyclouds.flipthetripover.data.AreaBasedContent
import com.fleecyclouds.flipthetripover.data.DetailCommonContent
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_detail_recyclerview_item.view.*
import java.lang.Exception

class ContentDetailsRecycelrViewAdapter(val contents: ArrayList<DetailCommonContent>) : RecyclerView.Adapter<ContentDetailsRecycelrViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_detail_recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contents.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val content = contents[position]
        holder.bind(content)
    }

    fun addContent(content: DetailCommonContent){
        contents.add(content)
        notifyDataSetChanged()
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView = view.content_details_rcv_item_card
        val title = view.content_details_rcv_item_title
        val background = view.content_details_rcv_item_image
        val progressBar = view.content_details_progress

        fun bind(content: DetailCommonContent) {
            title.text = content.title
            Picasso.get().load(content.firstimage).error(R.drawable.no_image).fit().centerCrop().into(background, object : Callback {
                override fun onSuccess() {
                    progressBar.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    progressBar.visibility = View.VISIBLE
                }

            })
        }
    }
}