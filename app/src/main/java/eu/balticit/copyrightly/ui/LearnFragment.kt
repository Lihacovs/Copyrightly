package eu.balticit.copyrightly.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import eu.balticit.copyrightly.adapters.TopicAdapter
import eu.balticit.copyrightly.databinding.FragmentLearnBinding
import eu.balticit.copyrightly.viewmodels.LearnViewModel

class LearnFragment : Fragment() {

    lateinit var topicAdapter: TopicAdapter

    private lateinit var learnViewModel: LearnViewModel
    private var _binding: FragmentLearnBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        learnViewModel =
            ViewModelProvider(this).get(LearnViewModel::class.java)

        _binding = FragmentLearnBinding.inflate(inflater, container, false)
        val root: View = binding.root

        topicAdapter = TopicAdapter(learnViewModel.getFirestoreQueryOptions())
        binding.rvTopicList.adapter = topicAdapter
        topicAdapter.startListening()


        val textView: TextView = binding.textLearn
        learnViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        topicAdapter.stopListening()


        _binding = null
    }
}