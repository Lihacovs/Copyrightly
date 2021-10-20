package eu.balticit.copyrightly.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import eu.balticit.copyrightly.databinding.FragmentLoginBinding
import eu.balticit.copyrightly.viewmodels.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel =
            ViewModelProvider(this).get(LoginViewModel::class.java)

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnLoginServer.setOnClickListener { view ->
            Snackbar.make(view, "Login through FireBase", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        binding.btnLoginGoogle.setOnClickListener { view ->
            Snackbar.make(view, "Login through Google", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        binding.btnLoginFacebook.setOnClickListener { view ->
            Snackbar.make(view, "Login through Facebook", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        binding.btnLoginRegister.setOnClickListener { view ->
            Snackbar.make(view, "Opens register screen", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        /*val textView: TextView = binding.textLogin
        loginViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}