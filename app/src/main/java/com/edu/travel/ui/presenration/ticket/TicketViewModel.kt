package com.edu.travel.ui.presenration.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.travel.repository.TicketRepository
import com.edu.travel.route.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class TicketViewModel(
    private val ticketRepository: TicketRepository,
    private val routeParams: Route.Ticket
): ViewModel() {

    private val _mState = MutableStateFlow(TicketState())

    val state = _mState.onStart {
        initData()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _mState.value
    )

    private suspend fun initData() {
        if (routeParams.isPopular) {
            val popular = ticketRepository.getPopularById(routeParams.id)
            _mState.update { it.copy( popularEntity = popular) }
        }else {
            val detail = ticketRepository.getItemById(routeParams.id)
            _mState.update { it.copy(itemEntity = detail) }
        }
    }
}