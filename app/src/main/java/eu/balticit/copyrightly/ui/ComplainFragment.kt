package eu.balticit.copyrightly.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import eu.balticit.copyrightly.databinding.FragmentComplainBinding
import eu.balticit.copyrightly.viewmodels.ComplainViewModel

class ComplainFragment : Fragment() {

    private lateinit var complainViewModel: ComplainViewModel
    private var _binding: FragmentComplainBinding? = null

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

        _binding = FragmentComplainBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textComplain
        complainViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}