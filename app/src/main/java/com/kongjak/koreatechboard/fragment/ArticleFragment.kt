package com.kongjak.koreatechboard.fragment

import android.os.Bundle
import android.text.Html
import android.text.util.Linkify
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.connection.RetrofitBuilder
import com.kongjak.koreatechboard.data.Article
import com.kongjak.koreatechboard.data.Files
import com.kongjak.koreatechboard.data.GlideImageGetter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class ArticleFragment : Fragment() {

    var site: String = ""
    var url: String = ""

    private lateinit var progressBar: ProgressBar
    private lateinit var titleTextView: TextView
    private lateinit var writerTextView: TextView
    private lateinit var textTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var fileTextView: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_article, container, false)

        progressBar = rootView.findViewById(R.id.progress_bar)
        titleTextView = rootView.findViewById(R.id.title_text_view)
        writerTextView = rootView.findViewById(R.id.writer_text_view)
        textTextView = rootView.findViewById(R.id.text_text_view)
        dateTextView = rootView.findViewById(R.id.date_text_view)
        fileTextView = rootView.findViewById(R.id.file_text_view)

        site = requireArguments().getString("site")!!
        url = requireArguments().getString("url")!!

        progressBar.visibility = View.VISIBLE
        getApi()
        return rootView
    }

    private fun initView(
        title: String,
        writer: String,
        text: String,
        date: String,
        files: ArrayList<Files>
    ) {
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

        val transform = Linkify.TransformFilter { _, _ -> "" }

        for (file in files) {
            val pattern = Pattern.compile(file.fileName.replace("(", "\\(").replace(")", "\\)"))
            Linkify.addLinks(fileTextView, pattern, file.fileUri, null, transform)
        }
    }

    private fun getApi() {
        RetrofitBuilder.api.getArticle(site, url)
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