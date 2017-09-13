package ndts.heinzelnisseandroid

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.inaka.killertask.KillerTask
import java.net.URL

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    lateinit var maintext: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        maintext = findViewById(R.id.main_text) as TextView

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { defaultsearch() }
    }

    fun defaultsearch() {
        val onSuccess: (String) -> Unit = { result: String ->
            callback(result)
        }

        val onFailed: (Exception?) -> Unit = { e: Exception? ->
            Log.e("Heinzel", e.toString())
        }

        val doWork: () -> String = {
            val address = "https://heinzelnisse.info/searchResults?type=json&searchItem=luft"
            URL(address).readText()
        }

        val killerTask = KillerTask(doWork, onSuccess, onFailed)

        killerTask.go()
    }

    fun callback(string: String) {
        val parser = ResponseParser(applicationContext)
        val response = parser.parse(string)
        maintext.text = response.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        (menu
                .findItem(R.id.action_search)
                .actionView as SearchView)
                .setOnQueryTextListener(this)
        return true
    }

    // onQueryTextListener
    override fun onQueryTextSubmit(query: String?): Boolean {
        return if (query != null) {
            Toast.makeText(applicationContext, query, Toast.LENGTH_SHORT).show()
            true
        } else {
            false
        }
    }

    override fun onQueryTextChange(s: String?): Boolean = true
}
