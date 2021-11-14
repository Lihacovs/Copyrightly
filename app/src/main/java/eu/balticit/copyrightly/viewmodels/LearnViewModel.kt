package eu.balticit.copyrightly.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import eu.balticit.copyrightly.MyApp
import eu.balticit.copyrightly.data.AppRepositoryManager
import eu.balticit.copyrightly.data.firebase.model.Law
import eu.balticit.copyrightly.data.firebase.model.Material
import eu.balticit.copyrightly.data.firebase.model.Type

class LearnViewModel : ViewModel() {

    private val repositoryManager: AppRepositoryManager = MyApp.repositoryManager

    private val _text = MutableLiveData<String>().apply {
        value = "This is Learn Fragment"
    }
    val text: LiveData<String> = _text

    fun getTypesFirestoreQueryOptions(): FirestoreRecyclerOptions<Type>{
        return FirestoreRecyclerOptions.Builder<Type>()
            .setQuery(repositoryManager.getTypesQuery(), Type::class.java).build()
    }

    fun getLawsFirestoreQueryOptions(): FirestoreRecyclerOptions<Law>{
        return FirestoreRecyclerOptions.Builder<Law>()
            .setQuery(repositoryManager.getLawsQuery(), Law::class.java).build()
    }

    fun getMaterialsFirestoreQueryOptions(): FirestoreRecyclerOptions<Material>{
        return FirestoreRecyclerOptions.Builder<Material>()
            .setQuery(repositoryManager.getMaterialsQuery(), Material::class.java).build()
    }
}