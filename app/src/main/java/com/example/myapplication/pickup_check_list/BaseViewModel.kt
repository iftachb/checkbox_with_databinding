package com.example.myapplication.pickup_check_list

import androidx.lifecycle.ViewModel
import org.koin.core.KoinComponent

abstract class BaseViewModel(vararg useCases: BaseReactiveUseCase) : ViewModel(), KoinComponent {

    private var useCaseList: MutableList<BaseReactiveUseCase> = mutableListOf()

  //  protected val disposeOnClear = CompositeDisposable()

    init {
        useCaseList.addAll(useCases)
    }

    override fun onCleared() {
        super.onCleared()

//        disposeOnClear.dispose()
//        useCaseList.forEach { it.dispose() }
    }

    sealed class Display {
        data class Displayed<T>(val data: T) : Display()
        object Absent : Display()
    }
}
abstract class BaseReactiveUseCase: KoinComponent {

}