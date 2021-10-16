package eu.balticit.copyrightly.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import eu.balticit.copyrightly.BuildConfig
import eu.balticit.copyrightly.R
import eu.balticit.copyrightly.databinding.FragmentAboutBinding
import eu.balticit.copyrightly.utils.AppUtils
import eu.balticit.copyrightly.viewmodels.AboutViewModel

class AboutFragment : Fragment() {

    private lateinit var aboutViewModel: AboutViewModel
    private var _binding: FragmentAboutBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        aboutViewModel =
            ViewModelProvider(this).get(AboutViewModel::class.java)

        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.tcAboutAppVersion.text = getString(R.string.app_version, BuildConfig.VERSION_NAME)

        //Opens app introduction screens
        binding.ivAboutLinkFunctions.setOnClickListener { view ->
            Snackbar.make(view, "Opens app introduction screens", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        //Opens email to send to developer
        binding.ivAboutSendEmail.setOnClickListener { view ->
            Snackbar.make(view, "Opens email to send to developer", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            AppUtils.composeEmail(
                this.requireContext(),
                arrayOf(getString(R.string.app_email)),
                getString(R.string.email_subject)
            )

        }

        //Opens GooglePlay to rate app
        binding.ivAboutSendRate.setOnClickListener { view ->
            Snackbar.make(
                view,
                "Package name: " + this.requireContext().packageName,
                Snackbar.LENGTH_LONG
            )
                .setAction("Action", null).show()

            AppUtils.openPlayStoreForApp(this.requireContext())
        }

        /*val textView: TextView = binding.tcAboutAppVersion
        aboutViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}