package com.example.soko.proddata

import android.content.SearchRecentSuggestionsProvider


class MySuggestionProvider : SearchRecentSuggestionsProvider() {
    companion object {
        // AUTHORITY is a unique name, but it is recommended to use the name of the
        // package followed by the name of the class.
        const val AUTHORITY = "com.example.soko.proddata.MySuggestionProvider"

        // Uncomment line below, if you want to provide two lines in each suggestion:
        // public final static int MODE = DATABASE_MODE_QUERIES | DATABASE_MODE_2LINES;
        const val MODE = DATABASE_MODE_QUERIES
    }

    init {
        setupSuggestions(
            AUTHORITY,
            MODE
        )
    }
}