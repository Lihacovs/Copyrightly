package eu.balticit.copyrightly.data

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import eu.balticit.copyrightly.MyApp
import eu.balticit.copyrightly.data.firebase.AppFirebaseHelper
import eu.balticit.copyrightly.data.firebase.FirebaseHelper


/**
 * It is the one point of contact for any data related operation in the application.
 * Delegates all the operations specific to any Helper.
 */
class AppRepositoryManager : RepositoryManager {

    //private lateinit var mContext: Context

    //private val mPreferencesHelper: PreferencesHelper? = null
    private val mFirebaseHelper: FirebaseHelper = AppFirebaseHelper()


    override fun createFirebaseUser(email: String, password: String): Task<AuthResult> {
        return mFirebaseHelper.createFirebaseUser(email, password)
    }

    override fun signInFirebaseUser(email: String, password: String): Task<AuthResult> {
        return mFirebaseHelper.signInFirebaseUser(email, password)
    }

    override fun signInFirebaseWithCredential(credential: AuthCredential): Task<AuthResult> {
        return mFirebaseHelper.signInFirebaseWithCredential(credential)
    }

    override fun signOutFirebaseUser() {
        mFirebaseHelper.signOutFirebaseUser()
    }

    override fun getFirebaseUser(): FirebaseUser? {
        return mFirebaseHelper.getFirebaseUser()
    }

    override fun getFirebaseUserId(): String? {
        return mFirebaseHelper.getFirebaseUserId()
    }

    override fun getFirebaseUserName(): String? {
        TODO("Not yet implemented")
    }

    override fun getFirebaseUserEmail(): String? {
        TODO("Not yet implemented")
    }

    override fun getFirebaseUserImageUrl(): String? {
        TODO("Not yet implemented")
    }

    override fun setFirebaseUserName(userName: String?) {
        TODO("Not yet implemented")
    }

    override fun setFirebaseUserEmail(userEmail: String?) {
        TODO("Not yet implemented")
    }

    override fun setFirebaseUserImageUrl(userImageUrl: String?) {
        TODO("Not yet implemented")
    }

    override fun setFirebaseUserProfile(userName: String?, userPhotoUrl: String?): Task<Void?>? {
        TODO("Not yet implemented")
    }
    //private val mNetworkHelper: NetworkHelper? = null


}