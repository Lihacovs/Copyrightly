package eu.balticit.copyrightly.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import eu.balticit.copyrightly.adapters.LawDetailsAdapter
import eu.balticit.copyrightly.adapters.TypeDetailsAdapter
import eu.balticit.copyrightly.base.BaseFragment
import eu.balticit.copyrightly.databinding.FragmentLawDetailsBinding
import eu.balticit.copyrightly.databinding.FragmentTypeDetailsBinding
import eu.balticit.copyrightly.viewmodels.LearnViewModel

class LawDetailsFragment: BaseFragment() {
    lateinit var lawDetailsAdapter: LawDetailsAdapter

    private lateinit var learnViewModel: LearnViewModel
    private var _binding: FragmentLawDetailsBinding? = null

    val args: LawDetailsFragmentArgs by navArgs()


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

        _binding = FragmentLawDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        lawDetailsAdapter =
            LawDetailsAdapter(learnViewModel.getLawDetailsFirestoreQueryOptions(args.lawId))
        binding.rvLawDetailsList.adapter = lawDetailsAdapter
        lawDetailsAdapter.startListening()


        val textView: TextView = binding.textLawDetails
        learnViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lawDetailsAdapter.stopListening()
        _binding = null
    }
}