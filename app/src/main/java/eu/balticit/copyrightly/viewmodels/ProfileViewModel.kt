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

    private val _user = MutableLiveData<User>().apply {
        repositoryManager.getFirestoreUser(repositoryManager.getFirebaseUserId()!!).addOnSuccessListener {
            val user = it.toObject(User::class.java)
            value = user
        }.addOnFailureListener{
            Log.w(TAG, "Error: " + it.message.toString())

        }
    }
    val user: LiveData<User> = _user
}