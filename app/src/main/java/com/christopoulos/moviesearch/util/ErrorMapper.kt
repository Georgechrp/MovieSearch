package com.christopoulos.moviesearch.util

import android.content.Context
import com.christopoulos.moviesearch.R
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.toUserMessage(context: Context): String = when (this) {
    is UnknownHostException,
    is SocketTimeoutException,
    is IOException ->
        context.getString(R.string.network_error)

    is HttpException -> when (code()) {
        401 -> context.getString(R.string.auth_error)
        404 -> context.getString(R.string.not_found_error)
        else -> context.getString(R.string.server_error, code())
    }
    else -> context.getString(R.string.unexpected_error)
}