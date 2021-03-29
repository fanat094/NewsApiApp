package com.dyamschikov.newsapiapp.utils

import retrofit2.Response

abstract class DataSource {
    protected suspend fun <T> getResultDefault(call: suspend () -> Response<T>): Result<T> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                error(" ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Result<T> {
        return Result.error(message)
    }
}