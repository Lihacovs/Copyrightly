package eu.balticit.copyrightly.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import eu.balticit.copyrightly.MyApp
import eu.balticit.copyrightly.data.AppRepositoryManager

class HomeViewModel : ViewModel() {

    private val repositoryManager: AppRepositoryManager = MyApp.repositoryManager

    private val _currentUserName = MutableLiveData<String?>().apply {
        value = repositoryManager.getFirebaseUserName()
    }
    val mUserName: LiveData<String?> = _currentUserName

    private val _userId = MutableLiveData<String?>().apply {
        value = repositoryManager.getFirebaseUserId()
    }
    val userId: LiveData<String?> = _userId

}