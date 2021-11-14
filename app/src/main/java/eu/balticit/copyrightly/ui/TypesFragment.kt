package eu.balticit.copyrightly.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import eu.balticit.copyrightly.adapters.TypesAdapter
import eu.balticit.copyrightly.base.BaseFragment
import eu.balticit.copyrightly.databinding.FragmentTypesBinding
import eu.balticit.copyrightly.viewmodels.LearnViewModel

class TypesFragment: BaseFragment() {
    lateinit var typesAdapter: TypesAdapter

    private lateinit var learnViewModel: LearnViewModel
    private var _binding: FragmentTypesBinding? = null

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

        _binding = FragmentTypesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        typesAdapter = TypesAdapter(learnViewModel.getTypesFirestoreQueryOptions())
        binding.rvTypesList.adapter = typesAdapter
        typesAdapter.startListening()


        val textView: TextView = binding.textTypes
        learnViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        typesAdapter.stopListening()
        _binding = null
    }
}