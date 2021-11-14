package eu.balticit.copyrightly.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import eu.balticit.copyrightly.data.firebase.model.Type
import eu.balticit.copyrightly.databinding.ListItemTypeBinding

class TypesAdapter(options: FirestoreRecyclerOptions<Type>) : FirestoreRecyclerAdapter<Type, RecyclerView.ViewHolder>(
    options
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TypesAdapter.TypeViewHolder(
            ListItemTypeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, model: Type) {
        (holder as TypeViewHolder).bind(model)
    }

    class TypeViewHolder(
        private val binding: ListItemTypeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            Log.w("Type", "Opens initialised: ")
            binding.setClickListener {
                binding.type.let { type ->
                    Log.w("Type", "Opens type: " + type!!.typeName)
                }
            }
        }

        fun bind(item: Type) {
            binding.apply {
                type = item
                executePendingBindings()
            }
        }

        private fun navigateToType(type: Type, view: View) {

        }
    }
}