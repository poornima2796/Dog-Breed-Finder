package com.poorni.breedfinder.Services

import java.net.HttpURLConnection
import java.net.URL
import java.util.*

object WebServices {
    val dogImages: String
        get() {
            var url: URL? = null
            var connection: HttpURLConnection? = null
            var textResult = ""
            val query_parameter = ""
            try {
                url = URL("https://dog.ceo/api/breeds/image/random")
                connection = url.openConnection() as HttpURLConnection
                connection.readTimeout = 10000
                connection!!.connectTimeout = 15000
                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json")
                connection.setRequestProperty("Accept", "application/json")
                val scanner = Scanner(connection.inputStream)
                while (scanner.hasNextLine()) {
                    textResult += scanner.nextLine()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                connection!!.disconnect()
            }
            return textResult
        }

    val getAllDogs: String
        get() {
            var url: URL? = null
            var connection: HttpURLConnection? = null
            var textResult = ""
            val query_parameter = ""
            try {
                url = URL("https://dog.ceo/api/breeds/list/all")
                connection = url.openConnection() as HttpURLConnection
                connection.readTimeout = 10000
                connection!!.connectTimeout = 15000
                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json")
                connection.setRequestProperty("Accept", "application/json")
                val scanner = Scanner(connection.inputStream)
                while (scanner.hasNextLine()) {
                    textResult += scanner.nextLine()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                connection!!.disconnect()
            }
            return textResult
        }




}