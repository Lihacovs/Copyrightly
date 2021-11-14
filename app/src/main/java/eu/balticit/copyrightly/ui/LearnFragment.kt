package eu.balticit.copyrightly.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import eu.balticit.copyrightly.R
import eu.balticit.copyrightly.adapters.*
import eu.balticit.copyrightly.base.BaseFragment
import eu.balticit.copyrightly.databinding.FragmentLearnPagerBinding
import eu.balticit.copyrightly.viewmodels.LearnViewModel

class LearnFragment : BaseFragment() {

    private lateinit var learnViewModel: LearnViewModel
    private var _binding: FragmentLearnPagerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        learnViewModel =
            ViewModelProvider(this).get(LearnViewModel::class.java)

        _binding = FragmentLearnPagerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val tabLayout = binding.tabs
        val viewPager = binding.viewPager
        viewPager.adapter = LearnPagerAdapter(this)

        // Set the icon and text for each tab
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            //tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()

        //(activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //topicAdapter.stopListening()
        _binding = null
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            TYPES_PAGE_INDEX -> getString(R.string.learn_topic_types)
            LAWS_PAGE_INDEX -> getString(R.string.learn_topic_laws)
            MATERIAL_PAGE_INDEX -> getString(R.string.learn_topic_material)
            else -> null
        }
    }
}