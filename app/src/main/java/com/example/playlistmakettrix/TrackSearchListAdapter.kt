package com.example.playlistmakettrix

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmakettrix.databinding.SearchListItemBinding

class TrackSearchListAdapter (private val trackList: List<Track>) : RecyclerView.Adapter<TrackSearchListAdapter.TrackSearchListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackSearchListViewHolder {
        val itemBinding = SearchListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackSearchListViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    override fun onBindViewHolder(holder: TrackSearchListViewHolder, position: Int) {
        holder.bind(trackList[position])
    }

    class TrackSearchListViewHolder(private val itemBinding: SearchListItemBinding) : RecyclerView.ViewHolder(itemBinding.root){

        fun bind(item : Track){
            itemBinding.trackName.text = item.trackName
            itemBinding.artistName.text = item.artistName
            itemBinding.trackTime.text = item.trackTime

            Glide.with(itemBinding.root)
                .load(item.artworkUrl100)
                .placeholder(R.drawable.ic_placeholder)
                .centerCrop()
                .transform(RoundedCorners(10))
                .into(itemBinding.trackImage)
        }
    }
}