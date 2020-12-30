package com.teracode.medihelp.framework.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.teracode.medihelp.R
import com.teracode.medihelp.business.domain.util.DateUtil
import com.teracode.medihelp.business.interactors.druglist.DrugListInteractors
import com.teracode.medihelp.framework.datasource.cache.abstraction.DrugDaoService
import com.teracode.medihelp.framework.datasource.network.abstraction.DrugFirestoreService
import com.teracode.medihelp.framework.presentation.splash.DrugsNetworkSyncManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var fir: DrugFirestoreService

    @Inject lateinit var drugDaoService:DrugDaoService

    @Inject
    lateinit var dateUtil: DateUtil
    @Inject
    lateinit var drugsNetworkSyncManager: DrugsNetworkSyncManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        main_btn.setOnClickListener {
            main_btn.text = dateUtil.getCurrentTimestamp()



          val job=  CoroutineScope(IO).launch {
                drugsNetworkSyncManager.executeDataSync(this)
            }


            CoroutineScope(IO).launch {
                val list = fir.getAllDrugs()

                for (li in list) {
                    println("DRUGS:NETWORK ${li}")
                }
            }
            CoroutineScope(IO).launch {
                val list = drugDaoService.getAllDrugs()

                for (li in list) {
                    println("SyncDrugs:CACHE MAIN ${li}")
                }
//            }



        }
    }


}