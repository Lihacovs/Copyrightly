package eu.balticit.copyrightly.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AlertDialog
import eu.balticit.copyrightly.R
import eu.balticit.copyrightly.utils.AppUtils


/**
 * Base Fragment with common methods
 */
abstract class BaseFragment: Fragment() {

    private lateinit var mLoadingDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLoadingDialog = AppUtils.setProgressDialog(this.requireContext(), "Loading...")
    }

    fun showLoading(){
        hideLoading()
        mLoadingDialog.show()
    }

    fun hideLoading(){
        if (mLoadingDialog.isShowing) mLoadingDialog.dismiss()
    }

    private fun loadingDialog(): AlertDialog {
        val builder = AlertDialog.Builder(requireContext())
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

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}