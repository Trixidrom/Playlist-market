package com.example.playlistmakettrix.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed class Resource<T>(val data: T? = null, val message: String? = null, val errorCode: Int? = null) {
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(message: String, data: T? = null, errorCode: Int): Resource<T>(data, message, errorCode)
}

//Альтернативный debounce
/*
Пояснение можно найти по этому пути:
16. Продвинутая многопоточность и сложный UI
04. Корутины на практике
02. Debounce и асинхронные задачи — Яндекс Практикум_files
 */
fun <T> debounce(delayMillis: Long,
                 coroutineScope: CoroutineScope,
                 useLastParam: Boolean,
                 action: (T) -> Unit): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        if (useLastParam) {
            debounceJob?.cancel()
        }
        if (debounceJob?.isCompleted != false || useLastParam) {
            debounceJob = coroutineScope.launch {
                delay(delayMillis)
                action(param)
            }
        }
    }
}