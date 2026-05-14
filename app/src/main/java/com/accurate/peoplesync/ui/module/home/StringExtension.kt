package com.accurate.peoplesync.ui.module.home

fun String.toInitial(): String {
    val words = trim()
        .split(" ")
        .filter { it.isNotEmpty() }
        .take(2)
    return buildString {
        words.forEach { word ->
            word.firstOrNull { it.isLetter() }
                ?.uppercaseChar()
                ?.let { append(it) }
        }
    }
}