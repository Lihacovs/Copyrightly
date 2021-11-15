package eu.balticit.copyrightly.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import eu.balticit.copyrightly.data.firebase.model.MaterialDetail
import eu.balticit.copyrightly.databinding.ListItemMaterialDetailBinding
import eu.balticit.copyrightly.databinding.ListItemTypeDetailBinding

class MaterialDetailsAdapter(options: FirestoreRecyclerOptions<MaterialDetail>) :
    FirestoreRecyclerAdapter<MaterialDetail, RecyclerView.ViewHolder>(
        options
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MaterialDetailsAdapter.MaterialDetailViewHolder(
            ListItemMaterialDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        model: MaterialDetail
    ) {
        (holder as MaterialDetailsAdapter.MaterialDetailViewHolder).bind(model)
    }

    class MaterialDetailViewHolder(
        private val binding: ListItemMaterialDetailBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            Log.w("MaterialDetail", "Opens initialised: ")
            binding.setClickListener {
                binding.materialDetail.let { materialDetail ->
                    Log.w(
                        "MaterialDetail",
                        "Opens Material Detail: " + materialDetail!!.materialDetailTitle
                    )
                }
            }
        }

        fun bind(item: MaterialDetail) {
            binding.apply {
                materialDetail = item
                executePendingBindings()
            }
        }
    }
}