package eu.balticit.copyrightly

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
import eu.balticit.copyrightly.databinding.ActivityMainBinding
import eu.balticit.copyrightly.viewmodels.LoginViewModel

class MainActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LoginViewModelTAG", "On Create triggered")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_complain, R.id.nav_learn, R.id.nav_profile,
                R.id.nav_rate, R.id.nav_about, R.id.nav_login
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val headerLayout: View = navView.getHeaderView(0)

        loginViewModel.user.observe(this, Observer {
            Log.d("LoginViewModelTAG", "Observer triggered: " + it?.displayName.toString())
            if (it != null) {
                navView.menu.findItem(R.id.nav_login).isVisible = false
                navView.menu.findItem(R.id.nav_logout).isVisible = true
                headerLayout.findViewById<TextView>(R.id.tv_drawer_header_user_name).text =
                    it.displayName.toString()
                headerLayout.findViewById<TextView>(R.id.tv_drawer_header_user_email).text =
                    it.email.toString()
                navController.navigate(R.id.nav_home)

            } else {
                navView.menu.findItem(R.id.nav_login).isVisible = true
                navView.menu.findItem(R.id.nav_logout).isVisible = false
                navController.navigate(R.id.nav_login)
                headerLayout.findViewById<TextView>(R.id.tv_drawer_header_user_name).text =
                    getString(R.string.drawer_header_user_name)
                headerLayout.findViewById<TextView>(R.id.tv_drawer_header_user_email).text =
                    getString(R.string.drawer_header_user_email)
            }
        })

        /*if (loginViewModel.user.value == null) {
            navView.menu.findItem(R.id.nav_login).isVisible = true
            navView.menu.findItem(R.id.nav_logout).isVisible = false
        } else {
            navView.menu.findItem(R.id.nav_login).isVisible = false
            navView.menu.findItem(R.id.nav_logout).isVisible = true
        }*/

        //TODO:logout
        navView.menu.findItem(R.id.nav_logout).setOnMenuItemClickListener { MenuItem ->
            when (MenuItem!!.itemId) {
                R.id.nav_logout -> {
                    //Log.d("MainActivityTAG", loginViewModel.userId.value.toString())
                    loginViewModel.signOutUser()
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
            }
            true
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}