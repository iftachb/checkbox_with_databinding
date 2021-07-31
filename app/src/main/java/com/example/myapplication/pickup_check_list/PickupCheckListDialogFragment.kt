package com.example.myapplication.pickup_check_list

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import kotlinx.android.synthetic.main.pickup_check_list_dialog.*
import com.example.myapplication.databinding.PickupCheckListDialogBinding
import org.json.JSONException
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.net.MalformedURLException
import java.net.URL


class PickupCheckListDialogFragment : DialogFragment() {

    companion object {
        val TAG = PickupCheckListDialogFragment::class.java.name
        const val JSON_PARAMETERS = "JSON_PARAMETERS"
        const val SENDERS = "senders"
        const val IMAGE = "image"
        const val HEADER = "header"
        const val SUBTITLE = "subtitle"
        const val BUTTON = "button"
        const val CHECKBOXES = "checkboxes"
        const val CHECKBOX = "checkbox"

        fun newInstance(parameters: String) = PickupCheckListDialogFragment().withArguments(JSON_PARAMETERS to parameters)
        }

    val viewModel : PickupCheckListViewModel by viewModel {
        parametersOf(requireArguments()[JSON_PARAMETERS])
    }

    val checkListAdapter: CheckListAdapter by lazy {
        CheckListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: PickupCheckListDialogBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.pickup_check_list_dialog, container,false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parameters = arguments?.getString(JSON_PARAMETERS)
        try {
            val sourceJson: JSONObject = JSONObject(parameters)
            val senders = sourceJson.getJSONArray(SENDERS)
            val senderJson = senders.getJSONObject(0)
            val image = senderJson.getString(IMAGE)
            if (!image.isNullOrBlank()) {
                val handler = Handler()
                Thread {
                    val url = URL(image)
                    val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                    handler.post { ivNote.setImageBitmap(bmp) }
                }.start()
            }
            tvTitle1.text = senderJson.getString(HEADER)
            tvDescription1.text = senderJson.getString(SUBTITLE)
            btPrimary1.text = senderJson.getString(BUTTON)
        }
        catch (e: JSONException) {
            Log.e("TAG", "fail to parse URL form json ${e.message}")
        }
        catch (e: MalformedURLException) {
            Log.e("TAG", "fail to parse URL form json ${e.message}")
        }

        rvCheckList.adapter = checkListAdapter

        viewModel.primaryLiveData.postValue(true)

        btPrimary1.setOnClickListener {
            dismiss()
        }
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dismiss()
            }
            keyCode == KeyEvent.KEYCODE_BACK
        }

    }

    override fun onStart() {
        super.onStart()

        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(displayMetrics.widthPixels, displayMetrics.heightPixels)
    }
}

@BindingAdapter("app:items")
fun <T> setItems(recyclerView: RecyclerView, items: List<T>?) {
    if (recyclerView.adapter != null) {
        (recyclerView.adapter as ListAdapter<T, *>).submitList(items)
    }
}
//@BindingAdapter("app:items")
//@JvmStatic
//fun <T> setItems(viewPager: ViewPager2, items: List<T>?) {
//    if (viewPager.adapter != null) {
//        (viewPager.adapter as ListAdapter<T, *>).submitList(items)
//        viewPager.adapter?.notifyDataSetChanged()
//    }
//}

fun <T: Fragment> T.withArguments(vararg params: Pair<String, Any?>): T {
    arguments = bundleOf(*params)
    return this
}
