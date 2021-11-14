package eu.balticit.copyrightly.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import eu.balticit.copyrightly.data.firebase.model.Material
import eu.balticit.copyrightly.databinding.ListItemMaterialBinding

class MaterialsAdapter(options: FirestoreRecyclerOptions<Material>) :
    FirestoreRecyclerAdapter<Material, RecyclerView.ViewHolder>(
        options
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MaterialsAdapter.MaterialViewHolder(
            ListItemMaterialBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, model: Material) {
        (holder as MaterialsAdapter.MaterialViewHolder).bind(model)
    }

    class MaterialViewHolder(
        private val binding: ListItemMaterialBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            Log.w("Material", "Opens initialised: ")
            binding.setClickListener {
                binding.material.let { material ->
                    Log.w("Material", "Opens material: " + material!!.materialName)
                }
            }
        }

        fun bind(item: Material) {
            binding.apply {
                material = item
                executePendingBindings()
            }
        }

        private fun navigateToMaterial(material: Material, view: View) {

        }
    }
}