package com.fleecyclouds.flipthetripover.searcharound

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.fleecyclouds.flipthetripover.data.LocationBasedContent
import com.fleecyclouds.flipthetripover.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.search_around_cardview.view.*
import java.lang.Exception

class SearchAroundRecyclerViewAdapter(val context: Context, val contents: List<LocationBasedContent>, val longitude: Double, val latitude: Double) : RecyclerView.Adapter<SearchAroundRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_around_cardview, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = contents.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val content = contents[position]
        holder.bind(content)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView = view.search_around_cardview
        val title = view.search_around_cardview_title
        val background = view.search_around_cardview_background
        var progressBar = view.search_around_cardview_progressbar

        fun bind(content: LocationBasedContent) {
            title.text = content.title

            // TODO 피카소 라이브러리 프로그레스바 및 디폴트 이미지 개선 필요
            if (content.firstimage == null) {
                background.setImageResource(R.drawable.no_image)
                progressBar.visibility = View.GONE
            }
            else {
                Picasso.get().load(content.firstimage).error(R.drawable.no_image).fit().centerCrop().into(background, object: Callback {
                    override fun onSuccess() {
                        progressBar.visibility = View.GONE
                    }
                    override fun onError(e: Exception?) {
                        progressBar.visibility = View.VISIBLE
                    }
                })
            }

            // TODO 콘텐츠 클릭 시 콘텐츠 상세정보 프레그먼트로 이동
            cardView.setOnClickListener {
                it.findNavController().navigate(R.id.action_searchAroundFragment_to_contentDetails,
                                                bundleOf("contentId" to content.contentid.toString(),
                                                            "contentTypeId" to content.contenttypeid.toString(),
                                                            "longitude" to longitude.toString(),
                                                            "latitude" to latitude.toString()))
            }
        }
    }
}