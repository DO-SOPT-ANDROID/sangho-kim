package org.sopt.dosopttemplate.data.mock

import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.presentation.main.home.list.UserInfo
import org.sopt.dosopttemplate.presentation.main.home.list.UserInfo.FriendInfo

val mockList: MutableList<UserInfo> = mutableListOf(
    FriendInfo(nickname = "이유빈", thumbnail = R.drawable.img_drink_1, isBirthday = true, birthDate= "10월 27일"),
    FriendInfo(nickname = "강유리", drink = "막걸리", drinkAmount = "2병", thumbnail = R.drawable.img_drink_2, description = "엠티 때 막차타고 집감"),
    FriendInfo(nickname = "김민우", drink = "소맥", drinkAmount = "1병", thumbnail = R.drawable.img_drink_3, description = "닭갈비에는 소주죠"),
    FriendInfo(nickname = "우상욱", drink = "소주", drinkAmount = "1짝", thumbnail = R.drawable.img_drink_4, description = "주량 검사 하겠습니다"),
    FriendInfo(nickname = "이태희", drink = "와인", drinkAmount = "1병", thumbnail = R.drawable.img_drink_5, description = "낭만이란 배를 타고 떠나갈거야"),
    FriendInfo(nickname = "이준희", thumbnail = R.drawable.img_drink_6, description = "시험기간 이슈"),
    FriendInfo(nickname = "이삭", drink = "소주", drinkAmount = "3병", thumbnail = R.drawable.img_drink_7, description = "술마시다 자꾸 전원이 꺼져요"),
    FriendInfo(nickname = "서재원", thumbnail = R.drawable.img_drink_8, description = "맛집에 가면 술이 술술술"),
    FriendInfo(nickname = "곽의진", drink = "소주", drinkAmount = "2병", thumbnail = R.drawable.img_drink_9, description = "뒤풀이 프로참석러"),
    FriendInfo(nickname = "박동민", drink = "맥주", drinkAmount = "1잔", thumbnail = R.drawable.img_drink_10, description = "마시면 바로 자요"),
    FriendInfo(nickname = "김지영", drink = "소주", drinkAmount = "30병", thumbnail = R.drawable.img_drink_1, description = "주량은 30병입니다"),
    )