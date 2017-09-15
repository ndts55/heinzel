package ndts.heinzelnisseandroid.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ndts.heinzelnisseandroid.R
import ndts.heinzelnisseandroid.objectmodel.TranslationEntry


class CustomListAdapter(context: Context, private var items: List<TranslationEntry>) : ArrayAdapter<TranslationEntry>(context, -1, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val itemView: View

        itemView = convertView ?: inflater.inflate(R.layout.list_item, parent, false)

        val item = items[position]

        (itemView.findViewById<TextView>(R.id.item_left)).text = item.getOriginal().toString()
        (itemView.findViewById<TextView>(R.id.item_right)).text = item.getTranslation().toString()

        return itemView
    }

    override fun getItem(position: Int): TranslationEntry = items[position]

    override fun getCount(): Int = items.size

    public fun updateData(newItems: List<TranslationEntry>) {
        items = newItems
        notifyDataSetChanged()
    }
}