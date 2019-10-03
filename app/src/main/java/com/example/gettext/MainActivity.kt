package com.example.gettext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getButton.setOnClickListener {
            var data = ""

            val httpAsync = "http://192.168.43.230:8080/ranking/search?name=taro"
                .httpGet()
                .responseString { _, _, result ->
                    when (result) {
                        is Result.Failure -> println(result.getException())
                        is Result.Success -> data = result.get()
                    }
                }
            httpAsync.join()

            text_view.text = data
        }
    }
}

/*
    val message = getString(R.string.message, "taro")
    val textView = findViewById<TextView>(R.id.text_view)
    textView.setText(message)

    val accessor = HttpAccessor()
    val resultJSON = accessor.getJson("http://localhost:8080/ranking/search?name=taro")
    val name = resultJSON.getString("name")
    text_view.text = resultJSON.string("name")
*/
