package eu.balticit.copyrightly.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import eu.balticit.copyrightly.databinding.FragmentHomeBinding
import eu.balticit.copyrightly.viewmodels.HomeViewModel
import eu.balticit.copyrightly.viewmodels.LoginViewModel

class HomeFragment : Fragment() {

    private val loginViewModel: LoginViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val userName: TextView = binding.textHome
        loginViewModel.user.observe(viewLifecycleOwner, Observer {
            userName.text = it?.displayName.toString()
        })

        val userId: TextView = binding.textView2
        loginViewModel.user.observe(viewLifecycleOwner, Observer {
            userId.text = it?.uid.toString()
        })

        val nameEt: EditText = binding.homeEt
        binding.homeButton.setOnClickListener {
            loginViewModel.setFirebaseUserName(nameEt.text.toString())
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}