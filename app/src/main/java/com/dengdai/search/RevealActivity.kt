package com.dengdai.search

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.quinny898.library.persistentsearch.SearchBox
import com.quinny898.library.persistentsearch.SearchBox.SearchListener
import com.quinny898.library.persistentsearch.SearchResult
import kotlinx.android.synthetic.main.activity_reveal.*


class RevealActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reveal)
        searchbox.enableVoiceRecognition(this)
        this.setSupportActionBar(toolbar)

        toolbar.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener,
            androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                openSearch()
                return true
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    fun openSearch() {
        toolbar.title = ""
        searchbox.revealFromMenuItem(R.id.action_search, this)
        for (x in 0..9) {
            val option = SearchResult(
                "Result "
                        + x.toString(), resources.getDrawable(
                    R.mipmap.ic_history
                )
            )
            searchbox.addSearchable(option)
        }
        searchbox.setMenuListener {
            Toast.makeText(
                this@RevealActivity, "Menu click",
                Toast.LENGTH_LONG
            ).show()
        }
        searchbox.setSearchListener(object : SearchListener {
            override fun onSearchOpened() {
                // Use this to tint the screen
            }

            override fun onSearchClosed() {
                // Use this to un-tint the screen
                closeSearch()
            }

            override fun onSearchTermChanged(term: String) {
                // React to the search term changing
                // Called after it has updated results
            }

            override fun onSearch(searchTerm: String) {
                Toast.makeText(
                    this@RevealActivity, "$searchTerm Searched",
                    Toast.LENGTH_LONG
                ).show()
                toolbar.title = searchTerm
            }

            override fun onResultClick(result: SearchResult?) {
                //React to result being clicked
            }

            override fun onSearchCleared() {}
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1234 && resultCode == RESULT_OK) {
            val matches = data!!
                .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            searchbox.populateEditText(matches!![0])
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun closeSearch() {
        searchbox.hideCircularly(this)
        if (searchbox.searchText.isEmpty()) toolbar.title = ""
    }
}