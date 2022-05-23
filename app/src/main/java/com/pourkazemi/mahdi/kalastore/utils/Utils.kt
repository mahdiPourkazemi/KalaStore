package com.pourkazemi.mahdi.maktab_hw_18_1.util

import android.graphics.Bitmap
import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.IOException
import javax.net.ssl.SSLException

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error<out T>(val message: String?) : ResultWrapper<T>()
    object Loading : ResultWrapper<Nothing>()
}

suspend inline fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    crossinline apiCall: suspend () -> Response<T>
) = flow {
    emit(ResultWrapper.Loading)
    try {
        val response = apiCall.invoke() //() after name or .invoke()
        val responseBody = response.body()
        if (response.isSuccessful && responseBody != null) {
            emit(ResultWrapper.Success(responseBody))
        } else {
            val errorBody = response.errorBody()
            if (errorBody != null) {
/*                val type=object :TypeToken<PictureItemError>(){}.type
                val responseError=Gson().fromJson<PictureItemError>(errorBody.charStream(),type)*/
                emit(ResultWrapper.Error<T>("response body error"))
            } else {
                emit(ResultWrapper.Error<T>("error is here for you to smile"))
            }
        }
    } catch (e: SSLException) {
        emit(ResultWrapper.Error(e.message))
    } catch (e: IOException) {
        emit(ResultWrapper.Error(e.message))
    } catch (e: HttpException) {
        emit(ResultWrapper.Error(e.message))
    } catch (e: Throwable) {
        emit(ResultWrapper.Error(e.message))
    }/* finally {
        emit(ResultWrapper.Error("finally you .."))
    }*/
}

private fun logger(msg: String) {
    Log.d("app_logger", msg)
}

fun Bitmap.toByteArrayConverter(): ByteArray {
    ByteArrayOutputStream().apply {
        compress(Bitmap.CompressFormat.JPEG, 90, this)
        return toByteArray()
    }
}