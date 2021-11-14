package eu.balticit.copyrightly.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import eu.balticit.copyrightly.data.firebase.model.TypeDetail
import eu.balticit.copyrightly.databinding.ListItemTypeDetailBinding

class TypeDetailsAdapter(options: FirestoreRecyclerOptions<TypeDetail>) :
    FirestoreRecyclerAdapter<TypeDetail, RecyclerView.ViewHolder>(
        options
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TypeDetailsAdapter.TypeDetailViewHolder(
            ListItemTypeDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        model: TypeDetail
    ) {
        (holder as TypeDetailsAdapter.TypeDetailViewHolder).bind(model)
    }

    class TypeDetailViewHolder(
        private val binding: ListItemTypeDetailBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            Log.w("TypeDetail", "Opens initialised: ")
            binding.setClickListener {
                binding.typeDetail.let { typeDetail ->
                    Log.w("TypeDetail", "Opens Type Detail: " + typeDetail!!.typeDetailTitle)
                }
            }
        }

        fun bind(item: TypeDetail) {
            binding.apply {
                typeDetail = item
                executePendingBindings()
            }
        }
    }
}