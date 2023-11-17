package org.sopt.dosopttemplate.util.extension

fun String.checkLength(min: Int, max: Int): Boolean = this.length in min..max