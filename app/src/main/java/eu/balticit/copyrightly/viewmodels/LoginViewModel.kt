package eu.balticit.copyrightly.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import eu.balticit.copyrightly.MyApp
import eu.balticit.copyrightly.R
import eu.balticit.copyrightly.data.AppRepositoryManager


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

    fun signOutUser() {
        repositoryManager.signOutFirebaseUser()
        _user.value = repositoryManager.getFirebaseUser()
    }

    fun setFirebaseUserName(userName: String) {
        repositoryManager.setFirebaseUserName(userName)?.addOnSuccessListener {
            _user.value = repositoryManager.getFirebaseUser()
        }
    }

    fun signInFirebaseUser(email: String, password: String) {
        repositoryManager.signInFirebaseUser(email, password).addOnSuccessListener {
            Log.d("LoginViewModelTAG", "1 " + it.user?.displayName.toString())
            _user.value = repositoryManager.getFirebaseUser()
        }.addOnFailureListener {
            when (it) {
                is FirebaseAuthInvalidUserException -> {
                    if (it.errorCode == "ERROR_USER_NOT_FOUND") {
                        _errorMessage.value = R.string.login_email_not_exist
                    } else {
                        _errorMessage.value = R.string.login_some_error
                    }
                }
                is FirebaseAuthInvalidCredentialsException ->
                    _errorMessage.value = R.string.login_invalid_password
                is FirebaseAuthUserCollisionException ->
                    _errorMessage.value = R.string.login_email_already_used
                else -> _errorMessage.value = R.string.login_some_error
            }
        }
    }

    fun createFirebaseUser(email: String, password: String, name: String, surname: String) {
        repositoryManager.createFirebaseUser(email, password)
            .addOnSuccessListener {
                repositoryManager.setFirebaseUserName("$name $surname")?.addOnSuccessListener {
                    _user.value = repositoryManager.getFirebaseUser()
                }?.addOnFailureListener {
                    _errorMessage.value = R.string.register_some_error
                }
            }
            .addOnFailureListener {
                when (it) {
                    is FirebaseAuthUserCollisionException -> _errorMessage.value =
                        R.string.register_email_already_used
                    else -> _errorMessage.value = R.string.register_some_error
                }
            }
    }
}