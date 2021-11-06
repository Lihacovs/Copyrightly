package eu.balticit.copyrightly.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import eu.balticit.copyrightly.R
import eu.balticit.copyrightly.base.BaseFragment
import eu.balticit.copyrightly.databinding.FragmentLoginBinding
import eu.balticit.copyrightly.utils.AppUtils
import eu.balticit.copyrightly.viewmodels.LoginViewModel

/**
 * Login Activity. There are 3 methods to get user profile: Internal FireBase, Google, Facebook.
 * <p>
 * App authentication used overall flow described in:
 * 1.FireBase Password login docs: https://firebase.google.com/docs/auth/android/password-auth
 * 2.Google profile login docs: https://firebase.google.com/docs/auth/android/google-signin
 * 3.Facebook profile login docs: https://firebase.google.com/docs/auth/android/facebook-login
 */
class LoginFragment : BaseFragment() {

    private val TAG = "Login"
    private val RC_SIGN_IN = 9001
    private val loginViewModel: LoginViewModel by activityViewModels()
    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnLoginServer.setOnClickListener { view ->
            val email: String = binding.etLoginEmail.text.toString()
            val password: String = binding.etLoginPassword.text.toString()
            when {
                email.isEmpty() -> showSnackbar(R.string.login_empty_email, view)
                !AppUtils.isValidEmail(email) -> showSnackbar(R.string.login_invalid_email, view)
                password.isEmpty() -> showSnackbar(R.string.login_empty_password, view)
                password.length < 6 -> showSnackbar(R.string.login_short_password, view)
                else -> {
                    hideKeyboard()
                    showLoading()
                    loginViewModel.signInFirebaseUser(email, password)
                }
            }
        }

        loginViewModel.user.observe(viewLifecycleOwner, {
            hideLoading()
        })

        loginViewModel.errorMessage.observe(viewLifecycleOwner, {
            hideLoading()
            showSnackbar(it, root)
        })

        binding.btnLoginGoogle.setOnClickListener { view ->
            val signInIntent =
                loginViewModel.getGoogleSignInClient(requireActivity()).signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        binding.btnLoginFacebook.setOnClickListener { view ->
            showSnackbar("Facebook login: In development", view)
        }

        binding.btnLoginRegister.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_nav_login_to_nav_register)
        }

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            showLoading()
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                loginViewModel.firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                hideLoading()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}