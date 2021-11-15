package eu.balticit.copyrightly.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import eu.balticit.copyrightly.adapters.MaterialDetailsAdapter
import eu.balticit.copyrightly.base.BaseFragment
import eu.balticit.copyrightly.databinding.FragmentMaterialDetailsBinding
import eu.balticit.copyrightly.viewmodels.LearnViewModel

class MaterialDetailsFragment : BaseFragment() {
    lateinit var materialDetailsAdapter: MaterialDetailsAdapter

    private lateinit var learnViewModel: LearnViewModel
    private var _binding: FragmentMaterialDetailsBinding? = null

    val args: MaterialDetailsFragmentArgs by navArgs()


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

        _binding = FragmentMaterialDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        materialDetailsAdapter =
            MaterialDetailsAdapter(learnViewModel.getMaterialDetailsFirestoreQueryOptions(args.materialId))
        binding.rvMaterialDetailsList.adapter = materialDetailsAdapter
        materialDetailsAdapter.startListening()


        val textView: TextView = binding.textMaterialDetails
        learnViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        materialDetailsAdapter.stopListening()
        _binding = null
    }
}