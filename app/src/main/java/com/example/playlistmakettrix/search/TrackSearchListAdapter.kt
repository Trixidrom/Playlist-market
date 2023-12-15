package com.example.playlistmakettrix.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmakettrix.R
import com.example.playlistmakettrix.databinding.SearchListItemBinding
import com.example.playlistmakettrix.search.models.Track
import java.text.SimpleDateFormat
import java.util.Locale

class TrackSearchListAdapter (private val trackList: List<Track>, val itemOnClickListener: ItemOnClickListener = ItemOnClickListener {  }) : RecyclerView.Adapter<TrackSearchListAdapter.TrackSearchListViewHolder>() {

    fun interface ItemOnClickListener{
        fun onClick(track: Track)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackSearchListViewHolder {
        val itemBinding = SearchListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackSearchListViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    override fun onBindViewHolder(holder: TrackSearchListViewHolder, position: Int) {
        holder.bind(trackList[position])
        holder.itemView.setOnClickListener {
            itemOnClickListener.onClick(trackList[position])
        }
    }

    class TrackSearchListViewHolder(private val itemBinding: SearchListItemBinding) : RecyclerView.ViewHolder(itemBinding.root){

        fun bind(item : Track){
            itemBinding.trackName.text = item.trackName
            itemBinding.artistName.text = item.artistName
            itemBinding.trackTime.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(item.trackTimeMillis)

            Glide.with(itemBinding.root)
                .load(item.artworkUrl100)
                .placeholder(R.drawable.ic_placeholder)
                .centerCrop()
                .transform(RoundedCorners(10))
                .into(itemBinding.trackImage)
        }
    }
}