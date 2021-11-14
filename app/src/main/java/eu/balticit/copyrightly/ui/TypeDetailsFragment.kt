package eu.balticit.copyrightly.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import eu.balticit.copyrightly.adapters.LawsAdapter
import eu.balticit.copyrightly.adapters.TypeDetailsAdapter
import eu.balticit.copyrightly.base.BaseFragment
import eu.balticit.copyrightly.databinding.FragmentLawsBinding
import eu.balticit.copyrightly.databinding.FragmentTypeDetailsBinding
import eu.balticit.copyrightly.viewmodels.LearnViewModel

class TypeDetailsFragment: BaseFragment() {
    lateinit var typeDetailsAdapter: TypeDetailsAdapter

    private lateinit var learnViewModel: LearnViewModel
    private var _binding: FragmentTypeDetailsBinding? = null

    val args: TypeDetailsFragmentArgs by navArgs()


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

        _binding = FragmentTypeDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        typeDetailsAdapter = TypeDetailsAdapter(learnViewModel.getTypeDetailsFirestoreQueryOptions(args.typeId))
        binding.rvTypeDetailsList.adapter = typeDetailsAdapter
        typeDetailsAdapter.startListening()


        val textView: TextView = binding.textTypeDetails
        learnViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        typeDetailsAdapter.stopListening()
        _binding = null
    }
}