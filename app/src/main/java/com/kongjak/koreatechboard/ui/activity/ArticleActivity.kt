package com.kongjak.koreatechboard.ui.activity

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.browser.customtabs.CustomTabsIntent
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.ui.fragment.ArticleFragment
import com.kongjak.koreatechboard.ui.viewmodel.ArticleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleActivity : AppCompatActivity() {

    private lateinit var uuid: String

    private val articleViewModel: ArticleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        uuid = intent.getStringExtra("uuid")!!

        val articleFragment = ArticleFragment()
        val articleBundle = Bundle()
        articleBundle.putString("site", intent.getStringExtra("site")!!)
        articleBundle.putString("uuid", uuid)
        articleFragment.arguments = articleBundle

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.article_frame_layout, articleFragment)
            .commit()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.toolbar_menu_open_in_browser -> {
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(this, Uri.parse(articleViewModel.article.value?.articleUrl))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
}
