package com.kongjak.koreatechcse.activity

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.kongjak.koreatechcse.data.GlideImageGetter
import com.kongjak.koreatechcse.R
import com.kongjak.koreatechcse.connection.RetrofitBuilder
import com.kongjak.koreatechcse.data.Article
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleActivity : AppCompatActivity() {

    var board: String = ""
    var articleNum: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        board = intent.getStringExtra("board")!!
        articleNum = intent.getIntExtra("article_num", 0)

        getApi()
    }

    private fun initView(title: String, writer: String, text: String, date: String) {
        val titleTextView: TextView = findViewById(R.id.title_text_view)
        val writerTextView: TextView = findViewById(R.id.writer_text_view)
        val textTextView: TextView = findViewById(R.id.text_text_view)
        val dateTextView: TextView = findViewById(R.id.date_text_view)

        titleTextView.text = title
        writerTextView.text = writer
        dateTextView.text = date

        val htmlText = Html.fromHtml(
            text,
            HtmlCompat.FROM_HTML_MODE_COMPACT,
            GlideImageGetter(textTextView),
            null
        )

        textTextView.text = htmlText
    }

    private fun getApi() {
        RetrofitBuilder.api.getArticle(board, articleNum)
            .enqueue(object : Callback<ArrayList<Article>> {
                override fun onResponse(
                    call: Call<ArrayList<Article>>,
                    response: Response<ArrayList<Article>>
                ) {
                    val list = response.body()
                    initView(list!![0].title, list[0].writer, list[0].text, list[0].date)
                }

                override fun onFailure(call: Call<ArrayList<Article>>, t: Throwable) {
                    Log.d("error", t.message.toString())
                }
            })
    }
}