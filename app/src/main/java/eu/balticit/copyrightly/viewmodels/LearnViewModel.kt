package eu.balticit.copyrightly.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import eu.balticit.copyrightly.MyApp
import eu.balticit.copyrightly.data.AppRepositoryManager
import eu.balticit.copyrightly.data.firebase.model.Topic
import java.util.*

class LearnViewModel : ViewModel() {

    private val repositoryManager: AppRepositoryManager = MyApp.repositoryManager

    private val _text = MutableLiveData<String>().apply {
        value = "This is Learn Fragment"
    }
    val text: LiveData<String> = _text


    fun getFirestoreQueryOptions(): FirestoreRecyclerOptions<Topic> {
        return FirestoreRecyclerOptions.Builder<Topic>()
            .setQuery(repositoryManager.getTopicsQuery(), Topic::class.java).build()
    }
}