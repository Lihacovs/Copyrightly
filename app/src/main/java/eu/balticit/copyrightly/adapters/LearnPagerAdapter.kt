package eu.balticit.copyrightly.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import eu.balticit.copyrightly.ui.LawsFragment
import eu.balticit.copyrightly.ui.MaterialsFragment
import eu.balticit.copyrightly.ui.TypesFragment

const val TYPES_PAGE_INDEX = 0
const val LAWS_PAGE_INDEX = 1
const val MATERIAL_PAGE_INDEX = 2

class LearnPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        TYPES_PAGE_INDEX to { TypesFragment() },
        LAWS_PAGE_INDEX to { LawsFragment() },
        MATERIAL_PAGE_INDEX to { MaterialsFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size


    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}