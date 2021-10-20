package eu.balticit.copyrightly

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import eu.balticit.copyrightly.data.AppRepositoryManager
import eu.balticit.copyrightly.data.RepositoryManager
import eu.balticit.copyrightly.data.firebase.AppFirebaseHelper
import eu.balticit.copyrightly.data.firebase.FirebaseHelper
import eu.balticit.copyrightly.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var repositoryManager: AppRepositoryManager =
        AppRepositoryManager(this, AppFirebaseHelper())

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

        val userId = repositoryManager.getFirebaseUserId().toString()

        //TODO:logout
        navView.menu.findItem(R.id.nav_logout).setOnMenuItemClickListener { MenuItem ->
            when (MenuItem!!.itemId) {
                R.id.nav_logout -> {
                    Log.d("MainActivityTAG", userId)
                }
            }
            true
        }

        //TODO:show Login Logout menu item based on Firebase Auth_State
        if (false) {
            navView.menu.findItem(R.id.nav_login).isVisible = true
            navView.menu.findItem(R.id.nav_logout).isVisible = false
        } else {
            navView.menu.findItem(R.id.nav_login).isVisible = false
            navView.menu.findItem(R.id.nav_logout).isVisible = true
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