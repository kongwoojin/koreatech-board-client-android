package com.kongjak.koreatechboard.activity

import android.os.Bundle
import android.text.Html
import android.text.util.Linkify
import android.text.util.Linkify.TransformFilter
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.connection.RetrofitBuilder
import com.kongjak.koreatechboard.data.Article
import com.kongjak.koreatechboard.data.Files
import com.kongjak.koreatechboard.data.GlideImageGetter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class DormArticleActivity : AppCompatActivity() {

    var url: String = ""

    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school_article)

        url = intent.getStringExtra("url")!!

        progressBar = findViewById(R.id.progress_bar)

        progressBar.visibility = View.VISIBLE
        getApi()
    }

    private fun initView(
        title: String,
        writer: String,
        text: String,
        date: String,
        files: ArrayList<Files>
    ) {
        val titleTextView: TextView = findViewById(R.id.title_text_view)
        val writerTextView: TextView = findViewById(R.id.writer_text_view)
        val textTextView: TextView = findViewById(R.id.text_text_view)
        val dateTextView: TextView = findViewById(R.id.date_text_view)
        val fileTextView: TextView = findViewById(R.id.file_text_view)

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

        for (file in files) {
            fileTextView.append(file.fileName)
            if (files.iterator().hasNext())
                fileTextView.append("\n")
        }

        val transform = TransformFilter { _, _ -> "" }

        for (file in files) {
            val pattern = Pattern.compile(file.fileName.replace("(", "\\(").replace(")", "\\)"))
            Linkify.addLinks(fileTextView, pattern, file.fileUri, null, transform)
        }
    }

    private fun getApi() {
        RetrofitBuilder.api.getDormArticle(url)
            .enqueue(object : Callback<ArrayList<Article>> {
                override fun onResponse(
                    call: Call<ArrayList<Article>>,
                    response: Response<ArrayList<Article>>
                ) {
                    val list = response.body()!!
                    initView(
                        list[0].title,
                        list[0].writer,
                        list[0].text,
                        list[0].date,
                        list[0].files
                    )
                    progressBar.visibility = View.GONE
                }

                override fun onFailure(call: Call<ArrayList<Article>>, t: Throwable) {
                    Log.d("error", t.message.toString())
                }
            })
    }
}