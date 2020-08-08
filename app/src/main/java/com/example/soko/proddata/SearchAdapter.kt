package com.example.soko.proddata


import android.app.SearchManager
import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView
import com.example.soko.R


class SearchAdapter(context: Context?, c: Cursor?, flags: Int) :
    CursorAdapter(context, c, flags) {
    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(
            R.layout.search_item, parent, false
        )
        val viewHolder = ViewHolder(view)
        view.setTag(viewHolder)
        return view
    }

    override fun bindView(view: View, context: Context?, cursor: Cursor) {
        val viewHolder = view.getTag() as ViewHolder
        viewHolder.mTitle.setText(
            cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
        )
    }

    fun getSuggestionText(position: Int): String? {
        if (position >= 0 && position < getCursor().getCount()) {
            val cursor: Cursor = getCursor()
            cursor.moveToPosition(position)
            return cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
        }
        return null
    }

    class ViewHolder(view: View) {
        var mTitle: TextView

        init {
            // view of your custom layout
            mTitle = view.findViewById(R.id.text)
        }
    }

    companion object {
        private val LOG_TAG = SearchAdapter::class.java.simpleName
    }
}