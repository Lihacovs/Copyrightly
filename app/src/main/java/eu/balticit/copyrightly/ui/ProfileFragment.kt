package eu.balticit.copyrightly.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import eu.balticit.copyrightly.R
import eu.balticit.copyrightly.adapters.*
import eu.balticit.copyrightly.base.BaseFragment
import eu.balticit.copyrightly.databinding.FragmentProfileBinding
import eu.balticit.copyrightly.viewmodels.ProfileViewModel

class ProfileFragment : BaseFragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val tabLayout = binding.profileTabs
        val viewPager = binding.viewPager
        viewPager.adapter = ProfilePagerAdapter(this)

        // Set the icon and text for each tab
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()

        profileViewModel.user.observe(viewLifecycleOwner, Observer { it
            binding.apply {
                user = it
                executePendingBindings()
            }
        })

        binding.setClickListener {
            showSnackbar("Edit profile for: " + binding.user?.userName, it)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            CLAIMS_PAGE_INDEX -> getString(R.string.profile_user_claims)
            MESSAGES_PAGE_INDEX -> getString(R.string.profile_user_messages)
            else -> null
        }
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            CLAIMS_PAGE_INDEX -> R.drawable.outline_copyright_24
            MESSAGES_PAGE_INDEX -> R.drawable.ic_mail_outlain
            else -> throw IndexOutOfBoundsException()
        }
    }
}