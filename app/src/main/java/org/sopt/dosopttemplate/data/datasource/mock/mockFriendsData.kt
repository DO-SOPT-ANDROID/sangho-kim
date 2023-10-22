package org.sopt.dosopttemplate.data.datasource.mock

import org.sopt.dosopttemplate.data.model.User
import org.sopt.dosopttemplate.data.model.friendUser

val mockList: List<User> = listOf(
    friendUser(nickname = "강유리", drink = "막걸리", drinkAmount = "2병", description = "엠티 때 막차타고 집감"),
    friendUser(nickname = "이유빈", description = "뒤풀이에서 밤새고 싶어요"),
    friendUser(nickname = "김민우", drink = "소주", drinkAmount = "1병", description = "닭갈비에는 소주죠"),
    friendUser(nickname = "우상욱", drink = "소주한짝", drinkAmount = "1짝", description = "주량 검사 하겠습니다"),
    friendUser(nickname = "이태희", drink = "와인", drinkAmount = "1병", description = "낭만이란 배를 타고 떠나갈거야"),
    friendUser(nickname = "박동민", drink = "맥주", drinkAmount = "1잔", description = "마시면 바로 자요"),
    friendUser(nickname = "박강희", drink = "소주", drinkAmount = "반병"),
    friendUser(nickname = "곽의진", drink = "소주", drinkAmount = "2병", description = "뒤풀이 프로참석러"),
    friendUser(nickname = "이준희", description = "택시타고 도망가봐요"),
    friendUser(nickname = "이삭", drink = "소주", drinkAmount = "3병", description = "술마시다 자꾸 전원이 꺼져요"),
    friendUser(nickname = "서재원", description = "맛집에 가면 술이 술술술")
    )