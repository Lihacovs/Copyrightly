package eu.balticit.copyrightly.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import eu.balticit.copyrightly.R
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

        // Create an ArrayAdapter using the string array and a default spinner layout
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.fragment_complaint_form_infringed_location_array,
                R.layout.spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                binding.spinnerComplainFormInfringedLocation.adapter = adapter
            }
        }

        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.fragment_complaint_form_types_array,
                R.layout.spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                binding.spinnerComplainFormTypeOfWork.adapter = adapter
            }
        }

        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.fragment_complaint_form_platforms_array,
                R.layout.spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                binding.spinnerComplainFormPlatform.adapter = adapter
            }
        }

        binding.setClickListenerSubmit {
            showSnackbar("Form Submit Pressed", it)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}