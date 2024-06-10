package aandrosov.gnews.ui.activities

import aandrosov.gnews.R
import aandrosov.gnews.ui.fragments.FavoriteNewsFragment
import aandrosov.gnews.ui.fragments.NewsFragment
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace

class MainActivity : AppCompatActivity(R.layout.main_activity) {
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_toolbar_menu, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.setVisible(false)
        val menuItemId = when(item.itemId) {
            R.id.favorites_menu_item -> {
                supportFragmentManager.commit {
                    replace<FavoriteNewsFragment>(R.id.fragment_container_view)
                }
                R.id.trends_menu_item
            }
            R.id.trends_menu_item -> {
                supportFragmentManager.commit {
                    replace<NewsFragment>(R.id.fragment_container_view)
                }
                R.id.favorites_menu_item
            }
            else -> return super.onOptionsItemSelected(item)
        }

        menu?.findItem(menuItemId)?.setVisible(true)
        return true
    }
}