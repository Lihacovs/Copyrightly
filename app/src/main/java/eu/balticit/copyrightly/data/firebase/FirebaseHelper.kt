package eu.balticit.copyrightly.data.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import eu.balticit.copyrightly.data.firebase.model.User


/**
 * Interface decouples any specific implementation of the [AppFirebaseHelper]
 * and hence makes it as plug and play unit
 */
interface FirebaseHelper {

    //=//=// F I R E B A S E  -  A U T H E N T I C A T I O N //=//=//

    fun createFirebaseUser(email: String, password: String): Task<AuthResult>

    fun signInFirebaseUser(email: String, password: String): Task<AuthResult>

    fun signInFirebaseWithCredential(credential: AuthCredential): Task<AuthResult>

    fun signOutFirebaseUser()

    fun getFirebaseUser(): FirebaseUser?

    fun getFirebaseUserId(): String?

    fun getFirebaseUserName(): String?

    fun getFirebaseUserEmail(): String?

    fun getFirebaseUserImageUrl(): String

    fun setFirebaseUserName(userName: String): Task<Void>?

    fun setFirebaseUserEmail(userEmail: String): Task<Void>?

    fun setFirebaseUserImageUrl(userImageUrl: String): Task<Void>?

    fun setFirebaseUserProfile(userName: String, userPhotoUrl: String?): Task<Void>?

    //=//=// F I R E B A S E  -  F I R E S T O R E //=//=//

    fun saveFirestoreUser(user: User): Task<Void>

    fun updateFirestoreUser(user: User): Task<Void>

    fun getFirestoreUser(userId: String): Task<DocumentSnapshot>

    fun getTypesQuery(): Query

    fun getLawsQuery(): Query

    fun getMaterialsQuery(): Query
}