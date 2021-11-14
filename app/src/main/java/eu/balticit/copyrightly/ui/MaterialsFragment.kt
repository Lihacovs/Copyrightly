package eu.balticit.copyrightly.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import eu.balticit.copyrightly.adapters.MaterialsAdapter
import eu.balticit.copyrightly.base.BaseFragment
import eu.balticit.copyrightly.databinding.FragmentMaterialsBinding
import eu.balticit.copyrightly.viewmodels.LearnViewModel

class MaterialsFragment : BaseFragment() {
    lateinit var materialsAdapter: MaterialsAdapter

    private lateinit var learnViewModel: LearnViewModel
    private var _binding: FragmentMaterialsBinding? = null

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

        _binding = FragmentMaterialsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        materialsAdapter = MaterialsAdapter(learnViewModel.getMaterialsFirestoreQueryOptions())
        binding.rvMaterialList.adapter = materialsAdapter
        materialsAdapter.startListening()


        val textView: TextView = binding.textMaterial
        learnViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        materialsAdapter.stopListening()
        _binding = null
    }
}