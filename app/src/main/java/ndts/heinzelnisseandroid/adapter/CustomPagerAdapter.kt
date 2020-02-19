package ndts.heinzelnisseandroid.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.ListFragment
import ndts.heinzelnisseandroid.Unicode
import ndts.heinzelnisseandroid.objectmodel.Response

class CustomPagerAdapter(context: Context,
                         fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private lateinit var data: Response

    private val germanAdapter: CustomListAdapter = CustomListAdapter(context, ArrayList())
    private val germanFragment: ListFragment = ListFragment()

    private val norwegianAdapter: CustomListAdapter = CustomListAdapter(context, ArrayList())
    private val norwegianFragment: ListFragment = ListFragment()

    init {
        germanFragment.listAdapter = germanAdapter
        norwegianFragment.listAdapter = norwegianAdapter
    }

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> germanFragment
        1 -> norwegianFragment
        else -> Fragment()
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence = when (position) {
        0 -> Unicode.germanFlag() + Unicode.arrow() + Unicode.norwegianFlag()
        1 -> Unicode.norwegianFlag() + Unicode.arrow() + Unicode.germanFlag()
        else -> ""
    }

    fun updateData(response: Response) {
        data = response
        germanAdapter.updateData(data.getGermanTranslations())
        norwegianAdapter.updateData(data.getNorwegianTranslations())
    }
}