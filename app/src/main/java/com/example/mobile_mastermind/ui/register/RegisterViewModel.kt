package com.example.mobile_mastermind.ui.register

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_mastermind.data.repository.remote.request.RegisterRequest
import com.example.mobile_mastermind.data.repository.remote.response.BaseResponse
import com.example.mobile_mastermind.domain.usecase.remote.RegisterUseCase
import com.example.mobile_mastermind.ui.extension.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _uiState = mutableStateOf(RegisterUiState())
    val uiState: State<RegisterUiState> = _uiState

    private val _registerResult = MutableStateFlow<RegisterResult?>(null)
    val registerResult: StateFlow<RegisterResult?> = _registerResult

    fun onUsernameChanged(newUsername: String) {
        _uiState.value = _uiState.value.copy(username = newUsername)
    }

    fun onEmailChanged(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun onPasswordChanged(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun onRepeatPasswordChanged(newRepeatPassword: String) {
        _uiState.value = _uiState.value.copy(passwordRepeat = newRepeatPassword)
    }

    fun onImageSelected(uri: Uri) {
        _uiState.value = _uiState.value.copy(userImg = uri.toString())
    }

    fun isAllFilled(): Boolean {
        return _uiState.value.userImg.isNotEmpty() && _uiState.value.username.isNotEmpty() && _uiState.value.email.isNotEmpty() && _uiState.value.password.isNotEmpty() && _uiState.value.passwordRepeat.isNotEmpty()
    }

    fun onRegisterClicked(context: Context) {
        if (!passwordCheck(_uiState.value.password, _uiState.value.passwordRepeat)) {
            Log.d(TAG, "%> Error: Las contraseñas no son iguales.")
            return
        }
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            val img = prepareFilePart(_uiState.value.userImg, context, _uiState.value.username)
            val registerRequest = RegisterRequest(
                username = stringToRequest(_uiState.value.username),
                email = stringToRequest(_uiState.value.email),
                password = stringToRequest(_uiState.value.password),
                image = img
            )
            registerUseCase(registerRequest).collect { baseResponse ->
                when (baseResponse) {
                    is BaseResponse.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false
                        )
                        _registerResult.value = RegisterResult.Success
                    }

                    is BaseResponse.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false
                        )
                        _registerResult.value =
                            RegisterResult.Error(message = baseResponse.error.message)
                    }
                }
            }
        }
    }

    private fun passwordCheck(pass: String, repeatPass: String): Boolean {
        return pass == repeatPass
    }

    private fun stringToRequest(text: String): RequestBody {
        return text.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    private fun prepareFilePart(
        uri: String, context: Context, username: String
    ): MultipartBody.Part {
        val contentResolver = context.contentResolver
        val uriURL = uri.toUri()
        val fileBytes = contentResolver.openInputStream(uriURL)?.use {
            it.readBytes()
        } ?: throw IllegalArgumentException("No se pudo abrir el archivo")

        val fileName = "$username.jpg"
        val requestFile = fileBytes.toRequestBody(
            contentResolver.getType(uriURL)?.toMediaTypeOrNull() ?: "image/*".toMediaTypeOrNull()
        )

        return MultipartBody.Part.createFormData("image", fileName, requestFile)
    }

    fun clearRegisterResult() {
        _registerResult.value = null
    }
}