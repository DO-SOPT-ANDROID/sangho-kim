package org.sopt.dosopttemplate.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.data.model.request.SignUpRequestDto
import org.sopt.dosopttemplate.di.ServicePool.authService
import org.sopt.dosopttemplate.domain.entity.User
import java.util.regex.Pattern

class SignUpViewModel : ViewModel() {

    private val _signUpState: MutableLiveData<ServerState<User>> =
        MutableLiveData(ServerState.Empty)
    val signUpState: LiveData<ServerState<User>> = _signUpState

    val idText: MutableLiveData<String> = MutableLiveData("")
    val pwText: MutableLiveData<String> = MutableLiveData("")
    val nameText: MutableLiveData<String> = MutableLiveData("")
    val drinkText: MutableLiveData<String> = MutableLiveData("")

    val isIdValid: LiveData<Boolean> = idText.map { ID_REGEX.matcher(it).matches() }
    val isPwValid: LiveData<Boolean> = pwText.map { PW_REGEX.matcher(it).matches() }

    val isButtonValid: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkButtonValid() {
        isButtonValid.value = (isIdValid.value == true && isPwValid.value == true)
    }

    fun postSignUpToServer() {
        viewModelScope.launch {
            runCatching {
                authService.postSignUp(
                    SignUpRequestDto(
                        username = idText.value.orEmpty(),
                        password = pwText.value.orEmpty(),
                        nickname = nameText.value.orEmpty()
                    )
                )
            }.onSuccess {
                _signUpState.value = ServerState.Success(
                    User(
                        id = idText.value.orEmpty(),
                        pw = pwText.value.orEmpty(),
                        nickname = nameText.value.orEmpty(),
                        drink = drinkText.value.orEmpty()
                    )
                )
            }.onFailure {
                _signUpState.value = ServerState.Failure
            }
        }
    }

    private companion object {
        const val ID_PATTERN = """^(?=.*[a-zA-Z])(?=.*\d).{6,10}$"""
        val ID_REGEX: Pattern = Pattern.compile(ID_PATTERN)
        const val PW_PATTERN = """^(?=.*[a-zA-Z])(?=.*\d)(?=.*[~!@#$%^&*()?]).{6,12}$"""
        val PW_REGEX: Pattern = Pattern.compile(PW_PATTERN)
    }
}