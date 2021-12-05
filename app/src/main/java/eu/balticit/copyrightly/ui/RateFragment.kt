package eu.balticit.copyrightly.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.testing.FakeReviewManager
import eu.balticit.copyrightly.MainActivity
import eu.balticit.copyrightly.base.BaseFragment
import eu.balticit.copyrightly.databinding.FragmentRateBinding
import eu.balticit.copyrightly.viewmodels.RateViewModel

class RateFragment : BaseFragment() {

    private lateinit var rateViewModel: RateViewModel
    private var _binding: FragmentRateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rateViewModel =
            ViewModelProvider(this).get(RateViewModel::class.java)

        _binding = FragmentRateBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rateButton.setOnClickListener {
            val manager = ReviewManagerFactory.create(requireContext())
            val request = manager.requestReviewFlow()
            request.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showSnackbar("Task is successful" ,it)
                    // We got the ReviewInfo object
                    val reviewInfo = task.result
                    val flow = manager.launchReviewFlow(activity as MainActivity, reviewInfo)
                    flow.addOnCompleteListener { _ ->
                        // The flow has finished. The API does not indicate whether the user
                        // reviewed or not, or even whether the review dialog was shown. Thus, no
                        // matter the result, we continue our app flow.
                        showSnackbar("Review flow is completed" ,it)
                    }
                } else {
                    showSnackbar("Task Error" ,it)
                    // There was some problem, continue regardless of the result.
                }
            }


        }

        val textView: TextView = binding.textRate
        rateViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}