package com.edu.travel.ui.presenration.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.travel.repository.DashboardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class DashboardViewModel(
    private val dashboardRepository: DashboardRepository
) : ViewModel() {

    private val _mState = MutableStateFlow(DashboardState())
    val state = _mState.onStart {
        initLocations()
        initBanners()
        initCategories()
        initPopulars()
        initRecommends()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _mState.value
    )

    private suspend fun initRecommends() {
        val recommends = dashboardRepository.getRecommends()
        _mState.update { it.copy(recommends = recommends)}
    }

    private suspend fun initLocations() {
        val locations = dashboardRepository.getLocation()
        _mState.update { it.copy(
            locations = locations,
            location = locations.first()
        ) }
    }

    private suspend fun initBanners(){
        val banners = dashboardRepository.getBanners()
        _mState.update { it.copy(banners = banners) }

    }

    private suspend fun initPopulars(){
        val populars = dashboardRepository.getPopulars()
        _mState.update { it.copy(populars = populars)}
    }

    private suspend fun initCategories() {
        val categories = dashboardRepository.getCategories().distinctBy { it.name }
        _mState.update { it.copy(
            categories = categories,
            category = categories.first()
        )}
    }

    fun handleDashboardAction(action: DashboardAction) {
        when(action) {
            is DashboardAction.ChangedLocationAction -> {
                _mState.update{ it.copy(location = action.location)}
            }
            is DashboardAction.ChangedCategoryAction -> {
                _mState.update { it.copy(category = action.category) }
            }
        }
    }
}

