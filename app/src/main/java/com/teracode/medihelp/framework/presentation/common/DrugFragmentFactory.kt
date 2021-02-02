package com.teracode.medihelp.framework.presentation.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.teracode.medihelp.framework.presentation.druglist.DrugListFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class DrugFragmentFactory : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String)=

        when (className) {
            DrugListFragment::class.java.name -> {
                val fragment=DrugListFragment()
                fragment
            }


            else -> {
                super.instantiate(classLoader, className)
            }
        }

}