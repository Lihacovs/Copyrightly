package eu.balticit.copyrightly.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eu.balticit.copyrightly.MyApp
import eu.balticit.copyrightly.data.AppRepositoryManager

class RegisterViewModel : ViewModel() {

    private val repositoryManager: AppRepositoryManager = MyApp.repositoryManager

    private val _text = MutableLiveData<String>().apply {
        value = "This is Register Fragment"
    }
    val text: LiveData<String> = _text

    fun createFirebaseUser(email: String, password: String) {
        repositoryManager.createFirebaseUser(email, password)
            .addOnSuccessListener {
                Log.d(
                    "RegisterViewModelTAG",
                    "User created:" + it.user?.email.toString()
                )
            }
            .addOnFailureListener {
                Log.d("RegisterViewModelTAG", "Error:" + it.message.toString())
            }
    }
}