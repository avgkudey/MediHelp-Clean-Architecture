package com.teracode.medihelp.framework.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.teracode.medihelp.R
import com.teracode.medihelp.framework.presentation.common.gone
import com.teracode.medihelp.framework.presentation.common.visible
import com.teracode.medihelp.framework.presentation.drugdetail.DrugDetailFragment
import com.teracode.medihelp.framework.presentation.druglist.DrugListFragment
import com.teracode.medihelp.framework.presentation.subcategorylist.SubcategoryFragment
import com.teracode.medihelp.quizmodule.framework.presentation.quizstart.QuizStartFragment
import com.teracode.medihelp.quizmodule.framework.presentation.quizdetail.QuizDetailFragment
import com.teracode.medihelp.util.BOTTOM_NAV_BACKSTACK_KEY
import com.teracode.medihelp.util.BottomNavController
import com.teracode.medihelp.util.setUpNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

private const val TAG = "MainActivity"

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class MainActivity : BaseActivity(), BottomNavController.OnNavigationGraphChanged,
    BottomNavController.OnNavigationReselectedListener {

    private lateinit var bottomNavigationView: BottomNavigationView


    private val bottomNavController by lazy(LazyThreadSafetyMode.NONE) {
        BottomNavController(
            this,
            R.id.main_fragments_container,
            R.id.menu_nav_drugs,
            this

        )
    }

    override fun onBackPressed() = bottomNavController.onBackPressed()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setTheme(R.style.Theme_Medihelp)
        setContentView(R.layout.activity_main)
        setupBottomNavigationView(savedInstanceState)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntArray(
            BOTTOM_NAV_BACKSTACK_KEY,
            bottomNavController.navigationBackStack.toIntArray()
        )


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)

    }

    override fun displayProgressBar(isDisplayed: Boolean) {
        if (isDisplayed)
            main_progress_bar.visible()
        else
            main_progress_bar.gone()
    }


    override fun onGraphChange() {

    }

    override fun onReselectNavItem(navController: NavController, fragment: Fragment) {

        when (fragment) {
            is SubcategoryFragment -> {
                navController.navigate(R.id.action_subcategoryFragment_to_drugCategoryFragment)
            }
            is DrugListFragment -> {
                navController.navigate(R.id.action_drugListFragment_to_drugCategoryFragment)
            }
            is DrugDetailFragment -> {
                navController.navigate(R.id.action_drugDetailFragment_to_drugCategoryFragment)
            }

            is QuizDetailFragment -> {
                navController.navigate(R.id.action_quizDetailFragment_to_quizFragment)
            }
            is QuizStartFragment -> {
                navController.navigate(R.id.action_quizStartFragment_to_quizFragment)
            }
            else -> {

            }
        }
    }

    private fun setupBottomNavigationView(savedInstanceState: Bundle?) {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setUpNavigation(bottomNavController, this)
        if (savedInstanceState == null) {
            bottomNavController.setupBottomNavigationBackStack(null)
            bottomNavController.onNavigationItemSelected()
        } else {
            (savedInstanceState[BOTTOM_NAV_BACKSTACK_KEY] as IntArray?)?.let { items ->
                val backstack = BottomNavController.BackStack()
                backstack.addAll(items.toTypedArray())
                bottomNavController.setupBottomNavigationBackStack(backstack)
            }
        }
    }

    private fun navAuthActivity() {

//        TODO navAuthActivity
//        val intent = Intent(this, AuthActivity::class.java)
//        startActivity(intent)
//        finish()
//        (application as BaseApplication).releaseMainComponent()
    }

}