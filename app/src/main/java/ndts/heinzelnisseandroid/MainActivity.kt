package ndts.heinzelnisseandroid

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.inaka.killertask.KillerTask
import ndts.heinzelnisseandroid.adapter.CustomPagerAdapter
import ndts.heinzelnisseandroid.objectmodel.Response
import java.net.URL

class MainActivity : AppCompatActivity() {
    private val TAG = "Heinzel"
    private lateinit var adapter: CustomPagerAdapter
    private lateinit var progressbar: ProgressBar
    private lateinit var messagetextview: TextView
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        progressbar = findViewById(R.id.loading_circle) as ProgressBar
        messagetextview = findViewById(R.id.message_textview) as TextView

        adapter = CustomPagerAdapter(applicationContext, supportFragmentManager)

        viewPager = findViewById(R.id.view_pager) as ViewPager
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
            handleResponse(response)
        }

        val onFailure: (Exception?) -> Unit = { e: Exception? ->
            Log.e(TAG, e?.toString())
        }

        // pre execute
        showLoadingView()
        KillerTask(doWork, onSuccess, onFailure).go()
    }

    private fun handleResponse(response: Response) {
        val isDeEmpty = response.getGermanTranslations().isEmpty()
        val isNoEmpty = response.getNorwegianTranslations().isEmpty()

        hideLoadingView()

        if (isDeEmpty && isNoEmpty) {
            // no translations
            showMessageView()
        } else {
            showPagerView()
            adapter.updateData(response)

            if (isDeEmpty && !isNoEmpty){
                // switch to right tab
                viewPager.currentItem = 1
            }
            if (!isDeEmpty && isNoEmpty) {
                // switch to left tab
                viewPager.currentItem = 0
            }
        }
    }

    private fun showLoadingView() {
        messagetextview.visibility = View.GONE
        viewPager.visibility = View.GONE
        progressbar.visibility = View.VISIBLE
    }

    private fun hideLoadingView() {
        progressbar.visibility = View.GONE
    }

    private fun showMessageView() {
        messagetextview.visibility = View.VISIBLE
    }

    private fun showPagerView() {
        viewPager.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val menuItem = menu.findItem(R.id.action_search)
        val searchView = (menuItem.actionView as SearchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //menuItem.collapseActionView()
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
