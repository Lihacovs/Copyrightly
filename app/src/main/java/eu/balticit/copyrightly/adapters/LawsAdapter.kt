package eu.balticit.copyrightly.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import eu.balticit.copyrightly.data.firebase.model.Law
import eu.balticit.copyrightly.databinding.ListItemLawBinding
import eu.balticit.copyrightly.ui.LearnFragmentDirections

class LawsAdapter(options: FirestoreRecyclerOptions<Law>) :
    FirestoreRecyclerAdapter<Law, RecyclerView.ViewHolder>(
        options
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LawsAdapter.LawViewHolder(
            ListItemLawBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, model: Law) {
        (holder as LawsAdapter.LawViewHolder).bind(model)
    }

    class LawViewHolder(
        private val binding: ListItemLawBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            Log.w("Law", "Opens initialised: ")
            binding.setClickListener {
                binding.law.let { law ->
                    Log.w("Law", "Opens law: " + law!!.lawName)
                    navigateToLaw(law, it)
                }
            }
        }

        fun bind(item: Law) {
            binding.apply {
                law = item
                executePendingBindings()
            }
        }

        private fun navigateToLaw(law: Law, view: View) {
            val direction =
                LearnFragmentDirections.actionNavLearnToNavLawDetails(law.lawId)
            view.findNavController().navigate(direction)
        }
    }
}