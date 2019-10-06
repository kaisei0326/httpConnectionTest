package com.example.gettext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.cUrlString
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import kotlinx.coroutines.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FuelManager.instance.apply {
            basePath = "http://httpbin.org"
            baseHeaders = mapOf("Device" to "Android")
            baseParams = listOf("key" to "value")
        }

        GET.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
                httpGet()
            }
        }

        POST.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
                httpPost()
            }
        }

        mainClearButton.setOnClickListener {
            mainResultText.text = ""
            mainAuxText.text = ""
        }
    }

    private suspend fun httpGet() {
        val (request, response, result) = Fuel.get("/get", listOf("userId" to "123")).awaitStringResponseResult()
        Log.d(TAG, response.toString())
        Log.d(TAG, request.toString())
        update(result)
    }

    private suspend fun httpPost() {
        val (request, response, result) = Fuel.post("/post", listOf("userId" to "123")).awaitStringResponseResult()
        Log.d(TAG, response.toString())
        Log.d(TAG, request.toString())
        update(result)
    }

    private fun <T : Any> update(result: Result<T, FuelError>) {
        result.fold(success = {
            mainResultText.append(it.toString())
        }, failure = {
            mainResultText.append(String(it.errorData))
        })
    }
}