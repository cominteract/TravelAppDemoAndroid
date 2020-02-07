package com.ainsigne.travelappdemo.data


data class VenueExploreData (
    val response: VenueExploreResponse
)

data class VenueExploreResponse (
    val query : String,
    val totalResults : Int,
    val groups: List<VenueGroups>
)

data class VenueGroups (
    val type : String,
    val name  : String,
    val items : List<VenueItem>? = null
)

data class VenueData (
    val response: VenueResponse
)

data class VenueResponse (
    val venue : VenueDetails
)