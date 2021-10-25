package eu.balticit.copyrightly.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import eu.balticit.copyrightly.MyApp
import eu.balticit.copyrightly.data.AppRepositoryManager
import android.util.Log
import com.google.firebase.auth.*


class LoginViewModel : ViewModel() {

    private val repositoryManager: AppRepositoryManager = MyApp.repositoryManager

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
                if (it is FirebaseAuthInvalidUserException ){
                    if (it.errorCode == "ERROR_USER_NOT_FOUND") {
                        Log.d("LoginViewModelTAG", "User Not Found")
                        return@OnFailureListener
                    }
                    Log.d("LoginViewModelTAG", it.message.toString())
                    return@OnFailureListener
                }
                if (it is FirebaseAuthInvalidCredentialsException){
                    Log.d("LoginViewModelTAG", "Invalid Password")
                    return@OnFailureListener
                }
                if (it is FirebaseAuthUserCollisionException){
                    Log.d("LoginViewModelTAG", "Email is already used")
                    return@OnFailureListener
                }
                Log.d("LoginViewModelTAG", it.message.toString())
            })
    }
}