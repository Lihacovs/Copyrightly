package eu.balticit.copyrightly.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import eu.balticit.copyrightly.adapters.LawsAdapter
import eu.balticit.copyrightly.base.BaseFragment
import eu.balticit.copyrightly.databinding.FragmentLawsBinding
import eu.balticit.copyrightly.viewmodels.LearnViewModel

class LawsFragment: BaseFragment() {
    lateinit var lawsAdapter: LawsAdapter

    private lateinit var learnViewModel: LearnViewModel
    private var _binding: FragmentLawsBinding? = null

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

        _binding = FragmentLawsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        lawsAdapter = LawsAdapter(learnViewModel.getLawsFirestoreQueryOptions())
        binding.rvLawList.adapter = lawsAdapter
        lawsAdapter.startListening()


        val textView: TextView = binding.textLaw
        learnViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lawsAdapter.stopListening()
        _binding = null
    }
}