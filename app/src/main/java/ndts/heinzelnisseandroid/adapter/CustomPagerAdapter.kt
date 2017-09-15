package ndts.heinzelnisseandroid.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.ListFragment
import ndts.heinzelnisseandroid.Emoji
import ndts.heinzelnisseandroid.objectmodel.Response

class CustomPagerAdapter(context: Context,
                         fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager) {
    lateinit var data: Response

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
        0 -> Emoji.germanFlag() + Emoji.arrow() + Emoji.norwegianFlag()
        1 -> Emoji.norwegianFlag() + Emoji.arrow() + Emoji.germanFlag()
        else -> ""
    }

    fun updateData(response: Response) {
        data = response
        germanAdapter.updateData(data.getGermanTranslations())
        norwegianAdapter.updateData(data.getNorwegianTranslations())
    }
}