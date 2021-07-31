package com.example.myapplication

import com.example.myapplication.pickup_check_list.PickupCheckListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val Module = module {

    viewModel { (parameter: String) ->
        PickupCheckListViewModel(parameter)
    }
}
