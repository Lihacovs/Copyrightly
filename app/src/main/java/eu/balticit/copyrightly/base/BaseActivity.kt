package eu.balticit.copyrightly.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import eu.balticit.copyrightly.R
import eu.balticit.copyrightly.utils.AppUtils

/**
 * Base Activity with common methods
 */
abstract class BaseActivity : AppCompatActivity() {

    private lateinit var mLoadingDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLoadingDialog = AppUtils.setProgressDialog(this, "Loading...")
    }

    fun showLoading() {
        hideLoading()
        mLoadingDialog.show()
    }

    fun hideLoading() {
        if (mLoadingDialog.isShowing) mLoadingDialog.dismiss()
    }

    private fun loadingDialog(): AlertDialog {
        val builder = AlertDialog.Builder(this.baseContext)
        builder.setCancelable(false) // if you want user to wait for some process to finish,
        builder.setView(R.layout.dialog_loading)
        return builder.create()
    }

    fun showSnackbar(resId: Int, view: View) {
        Snackbar.make(view, getString(resId), Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    fun showSnackbar(message: String, view: View) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}