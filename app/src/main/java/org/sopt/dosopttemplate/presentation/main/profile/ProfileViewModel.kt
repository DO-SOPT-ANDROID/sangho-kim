package org.sopt.dosopttemplate.presentation.main.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.sopt.dosopttemplate.data.local.UserSharedPref
import org.sopt.dosopttemplate.domain.entity.emptyUser

class ProfileViewModel : ViewModel() {

    private val _drinkAmount = MutableStateFlow<Float>(0.0F)
    val drinkAmount: StateFlow<Float> = _drinkAmount

    fun updateDrinkAmount(amount: Float) {
        _drinkAmount.value = amount
    }

    fun changeDrinkAmountToPref(amount: Float) {
        val user = UserSharedPref.getUserFromPref().apply {
            if (this != null) drinkAmount = amount
        }
        UserSharedPref.setUserToPref(user ?: return)
    }

    fun getUserData() = UserSharedPref.getUserFromPref() ?: emptyUser()

    fun clearUserData() {
        UserSharedPref.clearUserPref()
    }
}