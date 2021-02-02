package com.teracode.medihelp.toolsmodule.framework.presentation.bmi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.teracode.medihelp.databinding.FragmentBmiBinding
import com.teracode.medihelp.framework.presentation.common.*
import com.teracode.medihelp.framework.presentation.common.centimeterToInch
import com.teracode.medihelp.toolsmodule.framework.presentation.bmi.state.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@AndroidEntryPoint
@FlowPreview
class BmiFragment : BaseFragment<FragmentBmiBinding>() {

    private val viewModel: BmiViewModel by viewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBmiBinding =
        FragmentBmiBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    private fun setupListeners() {

        binding.bmiFragmentCalculateBtn.setOnClickListener {
            getResult()
        }
        binding.bmiFragmentClearBtn.setOnClickListener {
            clearFields()
        }

        binding.bmiFragmentUnitToggle.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                viewModel.setBmiUnit(Imperial())
            } else {
                viewModel.setBmiUnit(Metric())
            }


        }
        binding.bmiFragmentHeight.editText?.addTextChangedListener {
            validateHeightField()
        }

        binding.bmiFragmentWeight.editText?.addTextChangedListener {
            validateWeightField()
        }
    }

    private fun clearFields() {

        viewModel.setBmiFields(BmiFields())
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, { state ->
            state.bmiFields?.let { fields ->
                setFields(height = fields.height, weight = fields.weight)
            }


            state.unitStatus.let { unitStatus ->
                setFieldSuffix(unitStatus)
                convertFieldValues(unitStatus)

            }
        })

    }

    private fun convertFieldValues(unitStatus: BmiUnitStatus) {


        when (unitStatus) {

            is Imperial -> {
                var height: String? = null
                var weight: String? = null
                binding.bmiFragmentHeight.editText?.text?.toString()?.let { value ->
                    if (value.isNotEmpty()) {
                        height = value.toFloat().centimeterToInch().toString()
                    }
                }
                binding.bmiFragmentWeight.editText?.text?.toString()?.let { value ->
                    if (value.isNotEmpty()) {
                        weight = value.toFloat().kilogramToPound().toString()
                    }
                }
                setFields(height = height, weight = weight)
            }
            is Metric -> {

                var height: String? = null
                var weight: String? = null
                binding.bmiFragmentHeight.editText?.text?.toString()?.let { value ->

                    if (value.isNotEmpty()) {
                        height = value.toFloat().inchToCentimeter().toString()
                    }

                }
                binding.bmiFragmentWeight.editText?.text?.toString()?.let { value ->
                    if (value.isNotEmpty()) {
                        weight = value.toFloat().poundToKilogram().toString()
                    }
                }
                setFields(height = height, weight = weight)
            }

        }


    }


    private fun setFields(height: String?, weight: String?) {
        binding.bmiFragmentHeight.editText?.text = height?.toEditable()
        binding.bmiFragmentWeight.editText?.text = weight?.toEditable()
    }

    private fun setFieldSuffix(
        status: BmiUnitStatus,

        ) {

        binding.bmiFragmentHeight.suffixText = status.heightSuffix
        binding.bmiFragmentWeight.suffixText = status.weightSuffix

    }

    private fun getResult() {
        if (validateFields()) {
            val heightField: Float =
                binding.bmiFragmentHeight.editText?.text?.toString()?.toFloat()!!
            val weightField: Float =
                binding.bmiFragmentWeight.editText?.text?.toString()?.toFloat()!!

            val bmi = calculateBmi(height = (heightField / 100), weight = weightField)


            Toast.makeText(context, bmi.toString(), Toast.LENGTH_SHORT).show()


        }
    }


    private fun validateFields(): Boolean {
        clearFieldErrors()


        val heightField: String? = binding.bmiFragmentHeight.editText?.text?.toString()
        val weightField: String? = binding.bmiFragmentWeight.editText?.text?.toString()
        var result = true

        if (heightField == null || heightField.isEmpty()) {
            binding.bmiFragmentHeight.error = VALUE_NULL_ERROR
            binding.bmiFragmentHeight.editText?.requestFocus()
            result = false
        } else if (!(heightField.toFloat() > 0)) {
            binding.bmiFragmentHeight.error = VALUE_ZERO_ERROR
            result = false
        }


        if (weightField == null || weightField.isEmpty()) {
            binding.bmiFragmentWeight.error = VALUE_NULL_ERROR
            result = false
        } else if (!(weightField.toFloat() > 0)) {
            binding.bmiFragmentWeight.error = VALUE_ZERO_ERROR
            result = false
        }
        return result
    }

    private fun validateHeightField() {
        binding.bmiFragmentHeight.error = null

        val heightField: String? = binding.bmiFragmentHeight.editText?.text?.toString()

        when {
            heightField == null || heightField.isEmpty() -> {

            }
            heightField.toFloat() <= 0 -> {
                binding.bmiFragmentHeight.error = VALUE_ZERO_ERROR
            }
        }

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveFields()
    }


    private fun saveFields() {
        viewModel.setBmiFields(BmiFields(
            height = binding.bmiFragmentHeight.editText?.text.toString(),
            weight = binding.bmiFragmentWeight.editText?.text.toString()
        ))
    }

    private fun validateWeightField() {
        binding.bmiFragmentWeight.error = null

        val weightField: String? = binding.bmiFragmentWeight.editText?.text?.toString()

        when {
            weightField == null || weightField.isEmpty() -> {

            }
            weightField.toFloat() <= 0 -> {
                binding.bmiFragmentWeight.error = VALUE_ZERO_ERROR
            }
        }

    }

    private fun clearFieldErrors() {
        binding.bmiFragmentHeight.error = null
        binding.bmiFragmentWeight.error = null
    }


    /** Calculate BMI
     * [height] should be in meters
     * [weight] should be in kilograms
     */
    private fun calculateBmi(height: Float, weight: Float) = weight / (height * height)


    companion object {
        private const val VALUE_ZERO_ERROR = "value must be over 0"
        private const val VALUE_NULL_ERROR = "value cannot be empty"
    }


}