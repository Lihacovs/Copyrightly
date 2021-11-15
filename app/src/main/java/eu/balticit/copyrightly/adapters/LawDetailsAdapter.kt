package eu.balticit.copyrightly.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import eu.balticit.copyrightly.data.firebase.model.Law
import eu.balticit.copyrightly.data.firebase.model.LawDetail
import eu.balticit.copyrightly.databinding.ListItemLawDetailBinding
import eu.balticit.copyrightly.databinding.ListItemTypeDetailBinding

class LawDetailsAdapter(options: FirestoreRecyclerOptions<LawDetail>) :
    FirestoreRecyclerAdapter<LawDetail, RecyclerView.ViewHolder>(
        options
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LawDetailsAdapter.LawDetailViewHolder(
            ListItemLawDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, model: LawDetail) {
        (holder as LawDetailsAdapter.LawDetailViewHolder).bind(model)
    }

    class LawDetailViewHolder(
        private val binding: ListItemLawDetailBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            Log.w("LawDetail", "Opens initialised: ")
            binding.setClickListener {
                binding.lawDetail.let { lawDetail ->
                    Log.w("LawDetail", "Opens Type Detail: " + lawDetail!!.lawDetailTitle)
                }
            }
        }

        fun bind(item: LawDetail) {
            binding.apply {
                lawDetail = item
                executePendingBindings()
            }
        }
    }
}