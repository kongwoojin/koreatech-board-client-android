package com.kongjak.koreatechboard.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.fragment.ArticleFragment

class ArticleActivity : AppCompatActivity() {

    var site: String = ""
    var url: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        site = intent.getStringExtra("site")!!
        url = intent.getStringExtra("url")!!

        val articleFragment = ArticleFragment()
        val articleBundle = Bundle()
        articleBundle.putString("site", site)
        articleBundle.putString("url", url)
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
        }
        return super.onOptionsItemSelected(item)
    }
}