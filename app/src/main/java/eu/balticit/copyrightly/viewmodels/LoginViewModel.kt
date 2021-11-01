package eu.balticit.copyrightly.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import eu.balticit.copyrightly.MyApp
import eu.balticit.copyrightly.data.AppRepositoryManager
import com.google.firebase.auth.*
import eu.balticit.copyrightly.R


class LoginViewModel : ViewModel() {

    private val repositoryManager: AppRepositoryManager = MyApp.repositoryManager


    private val _errorMessage = MutableLiveData<Int>()
    val errorMessage: LiveData<Int> = _errorMessage

    private val _text = MutableLiveData<String>().apply {
        value = "This is Login Fragment"
    }
    val text: LiveData<String> = _text

    private val _user = MutableLiveData<FirebaseUser?>().apply {
        value = repositoryManager.getFirebaseUser()
    }
    val user: LiveData<FirebaseUser?> = _user

    private val _userId = MutableLiveData<String>().apply {
        value = repositoryManager.getFirebaseUserId()
    }
    val userId: LiveData<String> = _userId

    fun signOutUser() {
        repositoryManager.signOutFirebaseUser()
    }

    fun signInFirebaseUser(email: String, password: String) {
        repositoryManager.signInFirebaseUser(email, password).addOnSuccessListener(
            OnSuccessListener {

            }
        ).addOnFailureListener(
            OnFailureListener {
                when (it) {
                    is FirebaseAuthInvalidUserException -> {
                        if (it.errorCode == "ERROR_USER_NOT_FOUND") {
                            _errorMessage.postValue(R.string.login_email_not_exist)
                        } else {
                            _errorMessage.postValue(R.string.login_some_error)
                        }
                    }
                    is FirebaseAuthInvalidCredentialsException ->
                        _errorMessage.postValue(R.string.login_invalid_password)
                    is FirebaseAuthUserCollisionException ->
                        _errorMessage.postValue(R.string.login_email_already_used)
                    else -> _errorMessage.postValue(R.string.login_some_error)
                }
            })
    }
}