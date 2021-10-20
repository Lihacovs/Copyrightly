package eu.balticit.copyrightly.data.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import eu.balticit.copyrightly.data.firebase.FirebaseHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * Reads and writes the data from the Firebase database.
 */
class AppFirebaseHelper internal constructor() : FirebaseHelper {

    //private final FirebaseFirestore mFirestore;
    private val mAuth: FirebaseAuth
    //private final FirebaseStorage mStorage;

    init {
        //mFirestore = FirebaseFirestore.getInstance();
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

}