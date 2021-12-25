package eu.balticit.copyrightly.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import eu.balticit.copyrightly.R
import eu.balticit.copyrightly.base.BaseFragment
import eu.balticit.copyrightly.databinding.FragmentComplainBinding
import eu.balticit.copyrightly.viewmodels.ComplainViewModel
import eu.balticit.copyrightly.viewmodels.LoginViewModel

class ComplainFragment : BaseFragment() {

    private lateinit var complainViewModel: ComplainViewModel
    private lateinit var loginViewModel: LoginViewModel
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
        loginViewModel =
            ViewModelProvider(this).get(LoginViewModel::class.java)

        _binding = FragmentComplainBinding.inflate(inflater, container, false)
        val root: View = binding.root

        loginViewModel.user.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                findNavController().navigate(R.id.nav_login)
            }
        })

        binding.setClickListenerYouTube {
            showSnackbar("Complain on YouTube", it)
            findNavController().navigate(R.id.nav_complain_form_1)
        }

        binding.setClickListenerTikTok {
            showSnackbar("Complain on TikTok", it)
        }

        binding.setClickListenerFacebook {
            showSnackbar("Complain on Facebook", it)
        }

        binding.setClickListenerTwitter {
            showSnackbar("Complain on Twitter", it)
        }

        binding.setClickListenerInstagram {
            showSnackbar("Complain on Instagram", it)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}