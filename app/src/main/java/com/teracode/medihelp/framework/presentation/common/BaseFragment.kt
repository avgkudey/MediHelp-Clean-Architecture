package com.teracode.medihelp.framework.presentation.common

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.teracode.medihelp.framework.presentation.MainActivity
import com.teracode.medihelp.framework.presentation.UIController
import com.teracode.medihelp.util.TodoCallback
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.managers.ViewComponentManager

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.lang.ClassCastException

@FlowPreview
@ExperimentalCoroutinesApi
abstract class BaseFragment<VB : ViewBinding>
    : Fragment() {

    private var _binding: ViewBinding? = null

    @Suppress("UNCHECKED_CAST")
    val binding: VB
        get() = _binding as VB
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB


    lateinit var uiController: UIController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)




        return requireNotNull(_binding).root
    }

    fun displayToolbarTitle(textView: TextView, title: String?, useAnimation: Boolean) {
        if (title != null) {
            showToolbarTitle(textView, title, useAnimation)
        } else {
            hideToolbarTitle(textView, useAnimation)
        }
    }

    private fun hideToolbarTitle(textView: TextView, animation: Boolean) {
        if (animation) {
            textView.fadeOut(
                object : TodoCallback {
                    override fun execute() {
                        textView.text = ""
                    }
                }
            )
        } else {
            textView.text = ""
            textView.gone()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToolbarTitle(
        textView: TextView,
        title: String,
        animation: Boolean,
    ) {
        textView.text = title
        if (animation) {
            textView.fadeIn()
        } else {
            textView.visible()
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        setUIController(null) // null in production
    }

    private fun activityContext(): Context? {
        val context = context
        return if (context is ViewComponentManager.FragmentContextWrapper) {
            context.baseContext
        } else context
    }

    fun setUIController(mockController: UIController?) {

        // TEST: Set interface from mock
        if (mockController != null) {
            this.uiController = mockController
        } else { // PRODUCTION: if no mock, get from context
            activity?.let {
                Log.d("DrugListViewModel", "setUIController: ")
                if (it is MainActivity) {
                    try {
                        uiController = activityContext() as UIController
                    } catch (e: ClassCastException) {
                        e.printStackTrace()
                        Log.d("DrugListViewModel", "setUIController: ${e.message}")
                    }
                }
            }
        }
    }

}

















