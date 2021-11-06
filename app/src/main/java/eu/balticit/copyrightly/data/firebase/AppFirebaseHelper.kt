package eu.balticit.copyrightly.data.firebase

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import eu.balticit.copyrightly.data.firebase.model.User
import eu.balticit.copyrightly.utils.AppConstants


/**
 * Reads and writes the data from the Firebase database.
 */
class AppFirebaseHelper : FirebaseHelper {

    private val mFirestore: FirebaseFirestore
    private val mAuth: FirebaseAuth
    //private final FirebaseStorage mStorage;

    init {
        mFirestore = Firebase.firestore
        mAuth = Firebase.auth
        //mStorage = FirebaseStorage.getInstance();
    }

    override fun createFirebaseUser(email: String, password: String): Task<AuthResult> {
        return mAuth.createUserWithEmailAndPassword(email, password)
    }

    override fun signInFirebaseUser(email: String, password: String): Task<AuthResult> {
        return mAuth.signInWithEmailAndPassword(email, password)
    }

    override fun signInFirebaseWithCredential(credential: AuthCredential): Task<AuthResult> {
        return mAuth.signInWithCredential(credential)
    }

    override fun signOutFirebaseUser() {
        mAuth.signOut()
    }

    override fun getFirebaseUser(): FirebaseUser? {
        return mAuth.currentUser
    }

    override fun getFirebaseUserId(): String? {
        return mAuth.currentUser?.uid
    }

    override fun getFirebaseUserName(): String? {
        return mAuth.currentUser?.displayName
    }

    override fun getFirebaseUserEmail(): String? {
        return mAuth.currentUser?.email
    }

    override fun getFirebaseUserImageUrl(): String {
        return mAuth.currentUser?.photoUrl.toString()
    }

    override fun setFirebaseUserName(userName: String): Task<Void>? {
        val profileUpdates = userProfileChangeRequest {
            displayName = userName
        }
        return mAuth.currentUser?.updateProfile(profileUpdates)
    }

    override fun setFirebaseUserEmail(userEmail: String): Task<Void>? {
        return mAuth.currentUser?.updateEmail(userEmail)
    }

    override fun setFirebaseUserImageUrl(userImageUrl: String): Task<Void>? {
        val profileUpdates = userProfileChangeRequest {
            photoUri = Uri.parse(userImageUrl)
        }
        return mAuth.currentUser?.updateProfile(profileUpdates)
    }

    override fun setFirebaseUserProfile(userName: String, userPhotoUrl: String?): Task<Void>? {
        val profileUpdates = userProfileChangeRequest {
            displayName = userName
            photoUri = Uri.parse(userPhotoUrl)
        }
        return mAuth.currentUser?.updateProfile(profileUpdates)
    }

    //=//=// F I R E B A S E  -  F I R E S T O R E //=//=//

    override fun saveUser(user: User): Task<Void> {
        return mFirestore.collection(AppConstants.USERS_COLLECTION)
            .document(user.userId)
            .set(user)
    }

    override fun updateUser(user: User): Task<Void> {
        return mFirestore.collection(AppConstants.USERS_COLLECTION)
            .document(user.userId)
            .set(user, SetOptions.merge())
    }

    override fun getUser(userId: String): Task<DocumentSnapshot> {
        val docRef: DocumentReference =
            mFirestore.collection(AppConstants.USERS_COLLECTION).document(userId)
        return docRef.get()
    }


}