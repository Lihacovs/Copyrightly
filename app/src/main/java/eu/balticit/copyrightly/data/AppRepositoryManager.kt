package eu.balticit.copyrightly.data

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import eu.balticit.copyrightly.data.firebase.AppFirebaseHelper
import eu.balticit.copyrightly.data.firebase.FirebaseHelper
import eu.balticit.copyrightly.data.firebase.model.User


/**
 * It is the one point of contact for any data related operation in the application.
 * Delegates all the operations specific to any Helper.
 */
class AppRepositoryManager() : RepositoryManager {

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
        return mFirebaseHelper.getFirebaseUserName()
    }

    override fun getFirebaseUserEmail(): String? {
        return mFirebaseHelper.getFirebaseUserEmail()
    }

    override fun getFirebaseUserImageUrl(): String {
        return mFirebaseHelper.getFirebaseUserImageUrl()
    }

    override fun setFirebaseUserName(userName: String): Task<Void>? {
        return mFirebaseHelper.setFirebaseUserName(userName)
    }

    override fun setFirebaseUserEmail(userEmail: String): Task<Void>? {
        return mFirebaseHelper.setFirebaseUserEmail(userEmail)
    }

    override fun setFirebaseUserImageUrl(userImageUrl: String): Task<Void>? {
        return mFirebaseHelper.setFirebaseUserImageUrl(userImageUrl)
    }

    override fun setFirebaseUserProfile(userName: String, userPhotoUrl: String?): Task<Void>? {
        return setFirebaseUserProfile(userName, userPhotoUrl)
    }

    override fun saveFirestoreUser(user: User): Task<Void> {
        return mFirebaseHelper.saveFirestoreUser(user)
    }

    override fun updateFirestoreUser(user: User): Task<Void> {
        return mFirebaseHelper.updateFirestoreUser(user)
    }

    override fun getFirestoreUser(userId: String): Task<DocumentSnapshot> {
        return mFirebaseHelper.getFirestoreUser(userId)
    }

    override fun getTypesQuery(): Query {
        return mFirebaseHelper.getTypesQuery()
    }

    override fun getLawsQuery(): Query {
        return mFirebaseHelper.getLawsQuery()
    }

    override fun getMaterialsQuery(): Query {
        return mFirebaseHelper.getMaterialsQuery()
    }

    override fun getTypeDetailsQuery(typeId: String): Query {
        return mFirebaseHelper.getTypeDetailsQuery(typeId)
    }
    //private val mNetworkHelper: NetworkHelper? = null
}