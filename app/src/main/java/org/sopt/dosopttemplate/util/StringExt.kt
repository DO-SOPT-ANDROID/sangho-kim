package org.sopt.dosopttemplate.util

fun String.checkLength(min: Int, max: Int): Boolean = this.length in min..max