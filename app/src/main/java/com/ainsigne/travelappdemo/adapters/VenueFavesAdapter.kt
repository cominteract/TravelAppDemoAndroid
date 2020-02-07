package com.ainsigne.travelappdemo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ainsigne.travelappdemo.R
import com.ainsigne.travelappdemo.data.VenueAndFavorites
import com.ainsigne.travelappdemo.databinding.ListItemVenueFavesBinding
import com.ainsigne.travelappdemo.ui.VenueFavoritesFragmentDirections
import com.ainsigne.travelappdemo.viewmodels.VenueAndFavoritesViewModel

/**
 * Adapter for the [RecyclerView] in [VenueFAvoritesFragment].
 * responsible for displaying added favorites from the user
 */
class VenueFavesAdapter :
    ListAdapter<VenueAndFavorites, VenueFavesAdapter.ViewHolder>(VenueFavesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_venue_faves, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { venueFave->
            with(holder) {
                itemView.tag = venueFave
                bind(createOnClickListener(venueFave.details.id), venueFave)
            }
        }
    }

    /**
     * navigates to [VenueDetailsFragment] on click
     */
    private fun createOnClickListener(venueId: String): View.OnClickListener {
        return View.OnClickListener {
                val direction =
                       VenueFavoritesFragmentDirections.actionItemsFragmentToVenueDetailFragment(venueId = venueId)
                it.findNavController().navigate(direction)
        }
    }

    class ViewHolder(
        private val binding: ListItemVenueFavesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, faves: VenueAndFavorites) {
            with(binding) {
                clickListener = listener
                viewModel = VenueAndFavoritesViewModel(faves)
                executePendingBindings()
            }
        }
    }
}

/**
 * util override whether both elements of venue faves are the same
 */
private class VenueFavesDiffCallback : DiffUtil.ItemCallback<VenueAndFavorites>() {

    override fun areItemsTheSame(
        oldItem: VenueAndFavorites,
        newItem: VenueAndFavorites
    ): Boolean {
        return oldItem.details.id == newItem.details.id
    }

    override fun areContentsTheSame(
        oldItem: VenueAndFavorites,
        newItem: VenueAndFavorites
    ): Boolean {
        return oldItem.details.id == newItem.details.id
    }
}