package com.ainsigne.travelappdemo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ainsigne.travelappdemo.data.VenueItem
import com.ainsigne.travelappdemo.databinding.ListItemVenueBinding
import com.ainsigne.travelappdemo.ui.VenueItemsFragmentDirections


/**
 * Adapter for the [RecyclerView] in [VenueItemsFragment].
 * * responsible for displaying all the venues from current location
 */
class VenueAdapter : ListAdapter<VenueItem, VenueAdapter.ViewHolder>(VenueDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val venue = getItem(position)
        holder.apply {
            venue.venue?.id?.let {
                bind(createOnClickListener(it), venue)
            }
            itemView.tag = venue
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemVenueBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    private fun createOnClickListener(venueItemId: String): View.OnClickListener {
        return View.OnClickListener {
            val direction = VenueItemsFragmentDirections.actionItemsFragmentToVenueDetailFragment(venueId = venueItemId)
            it.findNavController().navigate(direction)
        }
    }

    class ViewHolder(
        private val binding: ListItemVenueBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: VenueItem) {
            binding.apply {
                clickListener = listener
                venue = item
                executePendingBindings()
            }
        }
    }
}

private class VenueDiffCallback : DiffUtil.ItemCallback<VenueItem>() {

    override fun areItemsTheSame(oldItem: VenueItem, newItem: VenueItem): Boolean {
        return oldItem.venue == newItem.venue
    }

    override fun areContentsTheSame(oldItem: VenueItem, newItem: VenueItem): Boolean {
        return oldItem == newItem
    }
}