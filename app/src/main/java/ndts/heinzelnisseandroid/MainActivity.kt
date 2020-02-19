package ndts.heinzelnisseandroid

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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
    private val tag = "Heinzel"
    private lateinit var adapter: CustomPagerAdapter
    private lateinit var progressbar: ProgressBar
    private lateinit var messagetextview: TextView
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        progressbar = findViewById(R.id.loading_circle)
        messagetextview = findViewById(R.id.message_textview)

        adapter = CustomPagerAdapter(applicationContext, supportFragmentManager)

        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = adapter

        (findViewById<TabLayout>(R.id.tab_layout)).setupWithViewPager(viewPager)
    }

    private fun search(searchItem: String) {
        val callURL: () -> String = {
            val address = getString(R.string.api_template) + searchItem
            URL(address).readText()
        }

        val onSuccess: (String) -> Unit = { result: String ->
            val response = ResponseParser().parse(result)
            handleResponse(response)
        }

        val onFailure: (Exception?) -> Unit = { e: Exception? ->
            Log.e(tag, e.toString())
        }

        // pre execute
        messagetextview.hide()
        viewPager.hide()
        progressbar.show()

        KillerTask(callURL, onSuccess, onFailure).go()
    }

    private fun handleResponse(response: Response) {
        val isDeEmpty = response.getGermanTranslations().isEmpty()
        val isNoEmpty = response.getNorwegianTranslations().isEmpty()

        progressbar.hide()

        if (isDeEmpty && isNoEmpty) {
            // no translations
            messagetextview.show()
        } else {
            viewPager.show()
            adapter.updateData(response)

            if (isDeEmpty && !isNoEmpty) {
                // switch to right tab
                viewPager.currentItem = 1
            }
            if (!isDeEmpty && isNoEmpty) {
                // switch to left tab
                viewPager.currentItem = 0
            }
        }
    }

    private fun View.show() {
        visibility = View.VISIBLE
    }

    private fun View.hide() {
        visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val menuItem = menu.findItem(R.id.action_search)

        val searchView = (menuItem.actionView as SearchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //menuItem.collapseActionView()
                if (query != null) search(query)
                else Log.e(tag, "query is null")
                return false
            }

            override fun onQueryTextChange(s: String?): Boolean = true
        })
        searchView.maxWidth = Int.MAX_VALUE

        return true
    }
}
