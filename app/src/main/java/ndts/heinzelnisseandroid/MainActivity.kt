package ndts.heinzelnisseandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.widget.TextView
import com.inaka.killertask.KillerTask
import java.net.URL

class MainActivity : AppCompatActivity() {
    private val TAG = "Heinzel"

    private lateinit var mainText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        mainText = findViewById(R.id.main_text) as TextView
    }


    private fun search(searchItem: String) {
        val doWork: () -> String = {
            val address = getString(R.string.api_template) + searchItem
            URL(address).readText()
        }

        val onSuccess: (String) -> Unit = { result: String ->
            mainText.text = result
        }

        val onFailure: (Exception?) -> Unit = { e: Exception? ->
            Log.e(TAG, e?.toString())
        }

        KillerTask(doWork, onSuccess, onFailure).go()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val menuItem = menu.findItem(R.id.action_search)
        (menuItem.actionView as SearchView)
                .setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                    override fun onQueryTextSubmit(query: String?): Boolean {
                        menuItem.collapseActionView()
                        if (query != null) search(query)
                        else Log.e(TAG, "query is null")

                        return false
                    }

                    override fun onQueryTextChange(s: String?): Boolean = true
                })
        return true
    }

    private fun norwegianFlag(): String = getByUnicode(0x1f1f3) + getByUnicode(0x1f1f4)
    private fun germanFlag(): String = getByUnicode(0x1f1e9) + getByUnicode(0x1f1ea)

    private fun getByUnicode(unicode: Int): String = String(Character.toChars(unicode))

}
