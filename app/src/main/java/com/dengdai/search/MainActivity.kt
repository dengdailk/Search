package com.dengdai.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.quinny898.library.persistentsearch.SearchBox
import com.quinny898.library.persistentsearch.SearchResult
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var context: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchbox.enableVoiceRecognition(this)

        context = this
        for (x in 0..10){
            val option:SearchResult = SearchResult(
                "Result $x",
                resources.getDrawable(R.mipmap.ic_history)
            )
            searchbox.addSearchable(option)
        }

        searchbox.setMenuListener { Toast.makeText(this, "Menu click", Toast.LENGTH_LONG).show() }

        searchbox.setSearchListener(object : SearchBox.SearchListener {
            override fun onSearchOpened() {

            }

            override fun onSearchCleared() {

            }

            override fun onSearchClosed() {

            }

            override fun onSearchTermChanged(term: String?) {

            }

            override fun onSearch(result: String?) =
                Toast.makeText(context, result!! + " Searched", Toast.LENGTH_LONG).show()

            override fun onResultClick(result: SearchResult?) {

            }
        })


        searchbox.setOverflowMenu(R.menu.overflow_menu)
        searchbox.setOverflowMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.test_menu_item -> {
                        Toast.makeText(context, "Clicked!", Toast.LENGTH_SHORT).show();
                        return true
                    }

                }
                return false
            }
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

    fun reveal(view: View) {
        startActivity(Intent(this, RevealActivity::class.java))
    }
}