package ndts.heinzelnisseandroid

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import com.inaka.killertask.KillerTask
import ndts.heinzelnisseandroid.ui.CustomPagerAdapter
import java.net.URL

class MainActivity : AppCompatActivity() {
    private val TAG = "Heinzel"
    lateinit var adapter: CustomPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        adapter = CustomPagerAdapter(applicationContext, supportFragmentManager)

        val viewPager = findViewById(R.id.view_pager) as ViewPager
        viewPager.adapter = adapter

        (findViewById(R.id.tab_layout) as TabLayout).setupWithViewPager(viewPager)
    }

    private fun search(searchItem: String) {
        val doWork: () -> String = {
            val address = getString(R.string.api_template) + searchItem
            URL(address).readText()
        }

        val onSuccess: (String) -> Unit = { result: String ->
            val response = ResponseParser(applicationContext).parse(result)
            adapter.updateData(response)
        }

        val onFailure: (Exception?) -> Unit = { e: Exception? ->
            Log.e(TAG, e?.toString())
        }

        KillerTask(doWork, onSuccess, onFailure).go()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val menuItem = menu.findItem(R.id.action_search)
        val searchView = (menuItem.actionView as SearchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                menuItem.collapseActionView()
                if (query != null) search(query)
                else Log.e(TAG, "query is null")

                return false
            }

            override fun onQueryTextChange(s: String?): Boolean = true
        })
        searchView.maxWidth = Int.MAX_VALUE
        return true
    }

}
