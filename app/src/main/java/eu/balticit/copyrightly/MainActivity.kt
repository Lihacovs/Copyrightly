package eu.balticit.copyrightly

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.review.ReviewManagerFactory
import eu.balticit.copyrightly.base.BaseActivity
import eu.balticit.copyrightly.databinding.ActivityMainBinding
import eu.balticit.copyrightly.databinding.NavHeaderMainBinding
import eu.balticit.copyrightly.viewmodels.LoginViewModel

class MainActivity : BaseActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHeaderMainBinding: NavHeaderMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        navHeaderMainBinding = NavHeaderMainBinding.bind(navView.getHeaderView(0))
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_complain, R.id.nav_learn, R.id.nav_profile,
                R.id.nav_about, R.id.nav_login
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        loginViewModel.user.observe(this, {
            //Log.d("LoginViewModelTAG", "Observer triggered: " + it?.displayName.toString())
            if (it != null) {
                navView.menu.findItem(R.id.nav_login).isVisible = false
                navView.menu.findItem(R.id.nav_logout).isVisible = true
                navController.navigate(R.id.nav_home)

            } else {
                navView.menu.findItem(R.id.nav_login).isVisible = true
                navView.menu.findItem(R.id.nav_logout).isVisible = false
                navController.navigate(R.id.nav_login)
            }

            navHeaderMainBinding.apply {
                user = it
                executePendingBindings()
            }
        })

        //TODO:Add onClick through data binding?
        navView.menu.findItem(R.id.nav_logout).setOnMenuItemClickListener { MenuItem ->
            when (MenuItem!!.itemId) {
                R.id.nav_logout -> {
                    loginViewModel.signOutUser()
                    loginViewModel.getGoogleSignInClient(this).signOut()
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
            }
            true
        }

        //TODO:Add onClick through data binding?
        navView.menu.findItem(R.id.nav_rate).setOnMenuItemClickListener { MenuItem ->
            when (MenuItem!!.itemId) {
                R.id.nav_rate -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    openInAppReview()
                }
            }
            true
        }

        //TODO: add to ComplaintFormFrgment
        val extras = intent.extras
        val message = extras!!.getString(Intent.EXTRA_TEXT)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_rate -> {
                openInAppReview()
            }
            R.id.action_settings -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //TODO: test function after App publish, rate only after auth???
    private fun openInAppReview() {
        val manager = ReviewManagerFactory.create(this)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("MainActivity", "openInAppReview: Task Successful")
                // We got the ReviewInfo object
                val reviewInfo = task.result
                val flow = manager.launchReviewFlow(this, reviewInfo)
                flow.addOnCompleteListener { _ ->
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown. Thus, no
                    // matter the result, we continue our app flow.
                    Log.d("MainActivity", "openInAppReview: Flow Completed")

                }
            } else {
                Log.d("MainActivity", "openInAppReview: Task Error")
                // There was some problem, continue regardless of the result.
            }
        }
    }
}