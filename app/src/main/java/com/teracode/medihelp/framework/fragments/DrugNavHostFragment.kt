package com.teracode.medihelp.framework.fragments

import android.os.Bundle
import androidx.annotation.NavigationRes
import androidx.navigation.fragment.NavHostFragment

class DrugNavHostFragment:NavHostFragment() {

    companion object{

        const val KEY_GRAPH_ID = "android-support-nav:fragment:graphId"

        @JvmStatic
        fun create(
            @NavigationRes graphId: Int = 0
        ): DrugNavHostFragment {
            var bundle: Bundle? = null
            if(graphId != 0){
                bundle = Bundle()
                bundle.putInt(KEY_GRAPH_ID, graphId)
            }
            val result =
                DrugNavHostFragment()
            if(bundle != null){
                result.arguments = bundle
            }
            return result
        }
    }
}