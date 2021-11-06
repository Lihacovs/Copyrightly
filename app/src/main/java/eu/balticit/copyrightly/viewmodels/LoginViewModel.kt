package eu.balticit.copyrightly.viewmodels

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.*
import eu.balticit.copyrightly.MyApp
import eu.balticit.copyrightly.R
import eu.balticit.copyrightly.data.AppRepositoryManager
import eu.balticit.copyrightly.data.firebase.model.User
import eu.balticit.copyrightly.utils.AppUtils


/**
 * Login ViewModel used in [eu.balticit.copyrightly.ui.LoginFragment],
 * [eu.balticit.copyrightly.ui.RegisterFragment],
 * [eu.balticit.copyrightly.MainActivity]
 * When user perform login with Google [firebaseAuthWithGoogle]
 * or Facebook [] user data is stored in 2 places:
 * 1. Firebase Authentication system - for user authentication.
 * 2. Firestore DB - general [eu.balticit.copyrightly.data.firebase.model.User] profile storage place.
 */
class LoginViewModel : ViewModel() {

    private val TAG = "Login"
    private val repositoryManager: AppRepositoryManager = MyApp.repositoryManager

    private val _errorMessage = MutableLiveData<Int>()
    val errorMessage: LiveData<Int> = _errorMessage

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
        //Creates Firebase User
        repositoryManager.createFirebaseUser(email, password)
            .addOnSuccessListener { authResult ->

                val user = User(
                    authResult.user!!.uid,
                    email,
                    password,
                    "$name $surname",
                    null,
                    null,
                    null,
                    AppUtils.getCurrentDate(),
                    userPremium = false,
                    userAdmin = false
                )

                //Updates Firebase User name
                repositoryManager.setFirebaseUserName("$name $surname")?.addOnSuccessListener {

                    //Saves User in the Firestore Database
                    repositoryManager.saveFirestoreUser(user).addOnSuccessListener {
                        Log.w(TAG, "User saved in Firestore: ")
                        _user.value = repositoryManager.getFirebaseUser()
                    }.addOnFailureListener {
                        Log.w(TAG, "User error in Firestore: " + it.message.toString())
                        _errorMessage.value = R.string.register_some_error
                    }

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

    fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        //Creates Firebase User with Google ID token
        repositoryManager.signInFirebaseWithCredential(credential).addOnSuccessListener { authResult ->
            Log.w(TAG, "Signed with Google: " + authResult.user?.displayName.toString())
            Log.w(TAG, "Signed with Google: " + authResult.user?.email.toString())
            Log.w(TAG, "Signed with Google: " + authResult.user?.photoUrl.toString())

            val user = User(
                authResult.user!!.uid,
                authResult.user!!.email,
                null,
                authResult.user!!.displayName,
                authResult.user!!.photoUrl.toString(),
                null,
                null,
                AppUtils.getCurrentDate(),
                userPremium = false,
                userAdmin = false
            )

            //Saves User in the Firestore Database
            repositoryManager.saveFirestoreUser(user).addOnSuccessListener {
                Log.w(TAG, "User saved in Firestore: ")
                _user.value = repositoryManager.getFirebaseUser()
            }.addOnFailureListener {
                Log.w(TAG, "User error in Firestore: " + it.message.toString())
                _errorMessage.value = R.string.register_some_error
            }

        }.addOnFailureListener {
            when (it) {
                is FirebaseAuthUserCollisionException -> _errorMessage.value =
                    R.string.register_email_already_used
                else -> _errorMessage.value = R.string.register_some_error
            }
        }
    }

    //Used in LoginFragment and MainActivity to perform Google login/logout action
    fun getGoogleSignInClient(activity: Activity): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.GOOGLE_WEB_CLIENT_ID_TOKEN))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(activity, gso)
    }
}