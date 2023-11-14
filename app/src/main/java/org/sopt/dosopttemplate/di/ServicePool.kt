package org.sopt.dosopttemplate.di

import org.sopt.dosopttemplate.data.service.AuthService
import org.sopt.dosopttemplate.data.datasource.service.FollowerService

object ServicePool {
    val followerService = FollowerApiFactory.create<FollowerService>()
    val authService = AuthApiFactory.create<AuthService>()
}