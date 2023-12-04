package org.sopt.dosopttemplate.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import org.sopt.dosopttemplate.data.model.request.SignUpRequestDto
import org.sopt.dosopttemplate.di.ServicePool
import org.sopt.dosopttemplate.domain.entity.User
import retrofit2.Call
import retrofit2.Response
import java.util.regex.Pattern

class SignUpViewModel : ViewModel() {

    private val _signUpState: MutableLiveData<ServerState<User>> =
        MutableLiveData(ServerState.Empty)
    val signUpState: LiveData<ServerState<User>> = _signUpState

    private val _idText: MutableLiveData<String> = MutableLiveData("")
    val idText: LiveData<String> = _idText
    private val _pwText: MutableLiveData<String> = MutableLiveData("")
    val pwText: LiveData<String> = _pwText
    private val _nameText: MutableLiveData<String> = MutableLiveData("")
    val nameText: LiveData<String> = _nameText
    private val _drinkText: MutableLiveData<String> = MutableLiveData("")
    val drinkText: LiveData<String> = _drinkText

    private val isIdValid: LiveData<Boolean> = _idText.map { ID_REGEX.matcher(it).matches() }
    private val isPwValid: LiveData<Boolean> = _pwText.map { PW_REGEX.matcher(it).matches() }

    val isButtonValid: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkButtonValid() {
        isButtonValid.value =
            (isIdValid.value == true && isPwValid.value == true && !idText.value.isNullOrBlank() && !pwText.value.isNullOrBlank() && !nameText.value.isNullOrBlank() && !drinkText.value.isNullOrBlank())
    }

    fun postSignUpToServer() {
        ServicePool.authService.postSignUp(
            SignUpRequestDto(
                username = idText.value.orEmpty(),
                password = pwText.value.orEmpty(),
                nickname = nameText.value.orEmpty()
            )
        )
            .enqueue(object : retrofit2.Callback<Unit> {
                override fun onResponse(
                    call: Call<Unit>,
                    response: Response<Unit>,
                ) {
                    if (response.isSuccessful) {
                        _signUpState.value = ServerState.Success(
                            User(
                                id = idText.value.orEmpty(),
                                pw = pwText.value.orEmpty(),
                                nickname = nameText.value.orEmpty(),
                                drink = drinkText.value.orEmpty()
                            )
                        )
                    } else {
                        _signUpState.value = ServerState.Failure
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    _signUpState.value = ServerState.ServerError
                }
            })
    }

    private companion object {
        const val ID_PATTERN = """^(?=.*[a-zA-Z])(?=.*\d).{6,10}$"""
        val ID_REGEX: Pattern = Pattern.compile(ID_PATTERN)
        const val PW_PATTERN = """^(?=.*[a-zA-Z])(?=.*\d)(?=.*[~!@#$%^&*()?]).{6,12}$"""
        val PW_REGEX: Pattern = Pattern.compile(PW_PATTERN)
    }
}