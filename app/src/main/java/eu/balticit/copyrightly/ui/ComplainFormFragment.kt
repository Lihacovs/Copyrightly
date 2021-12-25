package eu.balticit.copyrightly.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import eu.balticit.copyrightly.base.BaseFragment
import eu.balticit.copyrightly.databinding.FragmentComplainForm1Binding
import eu.balticit.copyrightly.viewmodels.ComplainViewModel

class ComplainFormFragment : BaseFragment(){
    private lateinit var complainViewModel: ComplainViewModel
    private var _binding: FragmentComplainForm1Binding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        complainViewModel =
            ViewModelProvider(this).get(ComplainViewModel::class.java)

        _binding = FragmentComplainForm1Binding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}