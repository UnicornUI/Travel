package com.edu.travel.di

import com.edu.travel.repository.DashboardRepository
import com.edu.travel.repository.DetailRepository
import com.edu.travel.repository.TicketRepository
import com.edu.travel.room.AppDatabase
import com.edu.travel.ui.presenration.dashboard.DashboardViewModel
import com.edu.travel.ui.presenration.detail.DetailViewModel
import com.edu.travel.ui.presenration.ticket.TicketViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single { AppDatabase.getInstance(get()) }
    singleOf(::DashboardRepository)
    singleOf(::DetailRepository)
    singleOf(::TicketRepository)
    viewModelOf(::DashboardViewModel)
    viewModelOf(::DetailViewModel)
    viewModelOf(::TicketViewModel)
}