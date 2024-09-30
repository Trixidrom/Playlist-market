package com.example.playlistmakettrix.ui.library.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmakettrix.R
import com.example.playlistmakettrix.databinding.TrackListItemBinding
import com.example.playlistmakettrix.domain.search.models.Track
import com.example.playlistmakettrix.ui.player.AudioPlayerScreenActivity
import com.google.gson.Gson

class FavoritesTracksListAdapter : RecyclerView.Adapter<FavoritesTracksListAdapter.FavoritesTracksViewHolder>(){

    private val differ = AsyncListDiffer(this, FavoritesTracksDiffCallback())

    inner class FavoritesTracksViewHolder(private val itemBinding: TrackListItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(track: Track) {
            itemBinding.trackName.text = track.trackName
            itemBinding.artistName.text = track.artistName
            itemBinding.trackTime.text = track.trackTime

            Glide.with(itemBinding.root)
                .load(track.artworkUrl100)
                .placeholder(R.drawable.ic_placeholder)
                .centerCrop()
                .transform(RoundedCorners(10))
                .into(itemBinding.trackImage)

            itemBinding.root.setOnClickListener {
                val intent = Intent(itemBinding.root.context, AudioPlayerScreenActivity::class.java)
                intent.putExtra(Intent.EXTRA_TEXT, Gson().toJson(track))
                itemBinding.root.context.startActivity(intent)
            }
        }
    }

    class FavoritesTracksDiffCallback : DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem.trackId == newItem.trackId
        }

        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesTracksViewHolder {
        val itemBinding = TrackListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesTracksViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavoritesTracksViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    fun update(tracks: List<Track>) {
        differ.submitList(tracks)
    }

}