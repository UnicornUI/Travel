package com.edu.travel.ui.presenration.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.travel.repository.DetailRepository
import com.edu.travel.route.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class DetailViewModel(
    private val detailRepository: DetailRepository,
    private val routeParams: Route.Detail
): ViewModel() {

    private val _mState = MutableStateFlow(DetailState())

    var state = _mState.onStart {
        initData()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _mState.value
    )

    private suspend fun initData(){
        if (routeParams.isPopular) {
            val fav = detailRepository.getPopularById(routeParams.id)
            _mState.update { it.copy(popularEntity = fav) }
        }else {
            val item = detailRepository.getItemById(routeParams.id)
            _mState.update { it.copy(itemEntity = item) }
        }
    }
}