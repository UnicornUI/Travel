package com.edu.travel.route

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {

    @Serializable
    data object Dashboard: Route()

    @Serializable
    data object Splash : Route()

    @Serializable
    data class Detail(val id: Int, val isPopular: Boolean = false): Route()

    @Serializable
    data class Ticket(val id: Int, val isPopular: Boolean = false): Route()
}