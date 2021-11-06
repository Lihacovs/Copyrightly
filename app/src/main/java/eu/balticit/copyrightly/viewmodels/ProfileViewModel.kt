package eu.balticit.copyrightly.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eu.balticit.copyrightly.MyApp
import eu.balticit.copyrightly.data.AppRepositoryManager
import eu.balticit.copyrightly.data.firebase.model.User

class ProfileViewModel : ViewModel() {

    private val TAG = "Profile"
    private val repositoryManager: AppRepositoryManager = MyApp.repositoryManager


    private val _text = MutableLiveData<String>().apply {
        value = "User Profile"
    }
    val text: LiveData<String> = _text

    fun getUserProfile(){
        repositoryManager.getFirestoreUser(repositoryManager.getFirebaseUserId()!!).addOnSuccessListener {
            val user = it.toObject(User::class.java)
            _text.value = "User: " + user?.userName.toString()
        }.addOnFailureListener{
            Log.w(TAG, "Error: " + it.message.toString())

        }
    }
}