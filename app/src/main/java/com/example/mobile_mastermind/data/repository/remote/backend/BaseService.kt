package com.example.mobile_mastermind.data.repository.remote.backend

import android.util.Log
import com.example.mobile_mastermind.data.repository.remote.response.BaseResponse
import com.example.mobile_mastermind.data.repository.remote.response.ErrorWrapper
import com.example.mobile_mastermind.data.repository.remote.response.SuccessWrapper
import com.example.mobile_mastermind.domain.model.ErrorModel
import com.example.mobile_mastermind.ui.extension.TAG
import com.google.gson.Gson
import retrofit2.Response

abstract class BaseService {
    suspend fun <T : Any> apiCall(call: suspend () -> Response<SuccessWrapper<T>>): BaseResponse<T> {
        try {
            val response = call.invoke()

            return if (!response.isSuccessful) {
                val errorResponse = mapErrorResponse(response)
                Log.e(TAG, "%> errorResponse: ${errorResponse.message}")
                BaseResponse.Error(errorResponse)
            } else {
                val body = response.body()
                if (body != null) {
                    BaseResponse.Success(
                        data = body.data
                    )
                } else {
                    BaseResponse.Error(mapErrorResponse(response))
                }
            }
        } catch (throwable: Throwable) {
            Log.e(TAG, "%> throwable: ${throwable.message}")
            throwable.printStackTrace()
            return BaseResponse.Error(mapErrorResponse(throwable))
        }
    }

    private fun <T> mapErrorResponse(response: Response<T>): ErrorModel {
        val errorBody = response.errorBody()?.string()
        Log.d(TAG, "%> ErrorBody: $errorBody")
        val errorData = try {
            val wrapper = Gson().fromJson(errorBody, ErrorWrapper::class.java)
            wrapper.error
        } catch (exception: java.lang.Exception) {
            Log.e(TAG, "l> exception: ${exception.message}")
            exception.printStackTrace()
            null
        }

        return ErrorModel(
            code = errorData?.errorCode ?: 400,
            message = errorData?.message ?: ""
        )
    }

    private fun mapErrorResponse(throwable: Throwable): ErrorModel {
        return (ErrorModel(
            code = 0,
            message = throwable.message ?: "Vuelve a intentarlo más tarde."
        ))
    }
}