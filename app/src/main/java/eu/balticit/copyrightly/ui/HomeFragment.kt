package eu.balticit.copyrightly.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import eu.balticit.copyrightly.databinding.FragmentHomeBinding
import eu.balticit.copyrightly.viewmodels.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val userName: TextView = binding.textHome
        homeViewModel.mUserName.observe(viewLifecycleOwner, Observer {
            userName.text = it
        })

        val userId: TextView = binding.textView2
        homeViewModel.userId.observe(viewLifecycleOwner, Observer {
            userId.text = it
        })

        val nameEt: EditText = binding.homeEt
        binding.homeButton.setOnClickListener {
            homeViewModel.setFirebaseUserName(nameEt.text.toString())
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}