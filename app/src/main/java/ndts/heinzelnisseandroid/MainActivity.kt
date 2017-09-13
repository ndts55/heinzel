package ndts.heinzelnisseandroid

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import java.net.URL
import com.inaka.killertask.KillerTask

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val maintext = findViewById(R.id.main_text) as TextView

        val defaultsearchitem = "luft"

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { _ ->
            val onSuccess: (String) -> Unit = { result: String ->
                maintext.text = result
            }

            val onFailed: (Exception?) -> Unit = { e: Exception? ->
                Log.e("Heinzel", e.toString())
            }

            val doWork: () -> String = {
                val address = "https://heinzelnisse.info/searchResults?type=json&searchItem=" + defaultsearchitem
                URL(address).readText()
            }

            val killerTask = KillerTask(doWork, onSuccess, onFailed)

            killerTask.go()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
