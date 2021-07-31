package com.example.myapplication.pickup_check_list

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

import com.example.myapplication.pickup_check_list.PickupCheckListDialogFragment.Companion.CHECKBOX
import com.example.myapplication.pickup_check_list.PickupCheckListDialogFragment.Companion.CHECKBOXES
import com.example.myapplication.pickup_check_list.PickupCheckListDialogFragment.Companion.SENDERS
import org.json.JSONException
import org.json.JSONObject

class PickupCheckListViewModel(
    parameters : String
) : BaseViewModel() {

    val primaryLiveData = MutableLiveData(false)
    val checkBoxListLiveData = MutableLiveData<ArrayList<CheckBoxData>>()
    val applyAllCheckBoxState : MutableLiveData<Boolean?>
            = checkBoxListLiveData.map { checkBoxData -> checkBoxData?.all{ it -> it.applyCheckBoxState.value == true }}

    val checkListListViewModel : MutableLiveData<List<CheckListViewModel>?> = checkBoxListLiveData.map { it ->
        it?.mapIndexed { index, checkBoxData -> CheckListViewModel(checkBoxData)}
    }

    init {
        try {
            val sourceJson: JSONObject = JSONObject(parameters)
            val senders = sourceJson.getJSONArray(SENDERS)
            val senderJson = senders.getJSONObject(0)
            val checkBoxesJsonArray = senderJson.getJSONArray(CHECKBOXES)
            val checkBoxes = ArrayList<CheckBoxData>()
            for (i in 0 until checkBoxesJsonArray.length()) {
                val checkBoxData = CheckBoxData(checkBoxesJsonArray.getJSONObject(i).getString(CHECKBOX), this, applyAllCheckBoxState)
                checkBoxData.checkBox = false
                checkBoxes.add(checkBoxData)
            }
            checkBoxListLiveData.postValue(checkBoxes)
        }
        catch (e: JSONException) {
            Log.e("TAG", "Cannot parse json. ${e.message}")
        }
    }
}

class CheckListViewModel(
    var checkBoxData : CheckBoxData,
) : BaseViewModel() {

    fun onCheckBokClick() {
        checkBoxData.checkBox = !checkBoxData.checkBox!!
    }
}

class CheckBoxData (
    val txt : String,
    val pickupCheckListViewModel : PickupCheckListViewModel,
    val applyAllCheckBoxState : MutableLiveData<Boolean?>
): BaseObservable() {

    @get:Bindable
    var checkBox: Boolean? = null
        set(value) {
            field = value
            applyCheckBoxState.postValue(field)
            applyAllCheckBoxState.postValue(pickupCheckListViewModel.checkBoxListLiveData.value?.all{ it -> it.checkBox == true })
            notifyPropertyChanged(BR.checkBox)
        }

    val applyCheckBoxState = MutableLiveData(false)
}

fun <X, Y> LiveData<X>.map(func: (X?) -> Y?): MutableLiveData<Y?> {
    return MediatorLiveData<Y>().apply {
        addSource(this@map) { x -> value = func(x) }
    }
}