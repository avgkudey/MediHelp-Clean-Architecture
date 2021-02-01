package com.teracode.medihelp.toolsmodule.framework.presentation.bmi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.teracode.medihelp.R
import com.teracode.medihelp.framework.presentation.common.BaseFragment
import com.teracode.medihelp.framework.presentation.common.hideKeyboard
import com.teracode.medihelp.framework.presentation.common.onDone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_bmi.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@AndroidEntryPoint
@FlowPreview
class BmiFragment : BaseFragment(R.layout.fragment_bmi) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bmi_fragment_weight.editText?.onDone {
            calculate()
        }
        bmi_fragment_calculate_btn.setOnClickListener {
            calculate()
        }
    }


    fun calculate() {
        view?.hideKeyboard()

        


        Toast.makeText(context, "Clicked", Toast.LENGTH_LONG).show()
    }

}