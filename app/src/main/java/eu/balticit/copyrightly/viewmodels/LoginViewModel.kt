package eu.balticit.copyrightly.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
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

    fun signOutUser() {
        repositoryManager.signOutFirebaseUser()
        _user.value = repositoryManager.getFirebaseUser()
    }

    fun setFirebaseUserName(userName: String){
        repositoryManager.setFirebaseUserName(userName)?.addOnSuccessListener {
            _user.postValue(repositoryManager.getFirebaseUser())
        }
    }

    fun signInFirebaseUser(email: String, password: String) {
        repositoryManager.signInFirebaseUser(email, password).addOnSuccessListener(
            OnSuccessListener {
                //TODO: observer is not triggered from there
                Log.d("LoginViewModelTAG", it.user?.displayName.toString())
                _user.value = repositoryManager.getFirebaseUser()
                Log.d("LoginViewModelTAG", _user.value.toString())
                Log.d("LoginViewModelTAG", user.value.toString())

            }
        ).addOnFailureListener(
            OnFailureListener {
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
            })
    }
}