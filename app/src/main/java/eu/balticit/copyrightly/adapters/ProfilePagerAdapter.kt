package eu.balticit.copyrightly.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import eu.balticit.copyrightly.ui.LawsFragment
import eu.balticit.copyrightly.ui.TypesFragment

const val CLAIMS_PAGE_INDEX = 0
const val MESSAGES_PAGE_INDEX = 1

class ProfilePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        CLAIMS_PAGE_INDEX to { TypesFragment() },
        MESSAGES_PAGE_INDEX to { LawsFragment() },
    )

    override fun getItemCount() = tabFragmentsCreators.size


    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}