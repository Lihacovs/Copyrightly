package eu.balticit.copyrightly.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import eu.balticit.copyrightly.MyApp
import eu.balticit.copyrightly.data.AppRepositoryManager
import eu.balticit.copyrightly.data.firebase.model.*

class LearnViewModel : ViewModel() {

    private val repositoryManager: AppRepositoryManager = MyApp.repositoryManager

    private val _text = MutableLiveData<String>().apply {
        value = "This is Learn Fragment"
    }
    val text: LiveData<String> = _text

    fun getTypesFirestoreQueryOptions(): FirestoreRecyclerOptions<Type> {
        return FirestoreRecyclerOptions.Builder<Type>()
            .setQuery(repositoryManager.getTypesQuery(), Type::class.java).build()
    }

    fun getLawsFirestoreQueryOptions(): FirestoreRecyclerOptions<Law> {
        return FirestoreRecyclerOptions.Builder<Law>()
            .setQuery(repositoryManager.getLawsQuery(), Law::class.java).build()
    }

    fun getMaterialsFirestoreQueryOptions(): FirestoreRecyclerOptions<Material> {
        return FirestoreRecyclerOptions.Builder<Material>()
            .setQuery(repositoryManager.getMaterialsQuery(), Material::class.java).build()
    }

    fun getTypeDetailsFirestoreQueryOptions(typeId: String): FirestoreRecyclerOptions<TypeDetail> {
        return FirestoreRecyclerOptions.Builder<TypeDetail>()
            .setQuery(repositoryManager.getTypeDetailsQuery(typeId), TypeDetail::class.java).build()
    }

    fun getLawDetailsFirestoreQueryOptions(lawId: String): FirestoreRecyclerOptions<LawDetail> {
        return FirestoreRecyclerOptions.Builder<LawDetail>()
            .setQuery(repositoryManager.getLawDetailsQuery(lawId), LawDetail::class.java).build()
    }

    fun getMaterialDetailsFirestoreQueryOptions(materialId: String): FirestoreRecyclerOptions<MaterialDetail> {
        return FirestoreRecyclerOptions.Builder<MaterialDetail>()
            .setQuery(
                repositoryManager.getMaterialDetailsQuery(materialId),
                MaterialDetail::class.java
            ).build()
    }
}