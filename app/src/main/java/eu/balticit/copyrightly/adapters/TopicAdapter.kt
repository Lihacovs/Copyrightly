package eu.balticit.copyrightly.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import eu.balticit.copyrightly.data.firebase.model.Topic
import eu.balticit.copyrightly.databinding.ListItemTopicBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil


class TopicAdapter(options: FirestoreRecyclerOptions<Topic>) :
    FirestoreRecyclerAdapter<Topic, RecyclerView.ViewHolder>(
        options
    ) {

    /*private val mDiffer: AsyncListDiffer<Topic> = AsyncListDiffer(this, PlantDiffCallback())

    override fun getItemCount(): Int {
        return mDiffer.currentList.size
    }

    fun submitList(list: List<Topic>){
        mDiffer.submitList(list)
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TopicViewHolder(
            ListItemTopicBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, model: Topic) {
        //val topic = mDiffer.currentList.get(position)
        (holder as TopicViewHolder).bind(model)
    }

    override fun onDataChanged() {
        super.onDataChanged()
    }


    class TopicViewHolder(
        private val binding: ListItemTopicBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            Log.w("Topic", "Opens initialised: ")
            binding.setClickListener {
                binding.topic.let { topic ->
                    Log.w("Topic", "Opens topic: " + topic!!.topicName)
                }
            }
        }

        fun bind(item: Topic) {
            binding.apply {
                topic = item
                executePendingBindings()
            }
        }

        private fun navigateToTopic(topic: Topic, view: View) {

        }
    }

    private class PlantDiffCallback : DiffUtil.ItemCallback<Topic>() {

        override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean {
            return oldItem.topicId == newItem.topicId
        }

        override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean {
            return oldItem == newItem
        }
    }
}