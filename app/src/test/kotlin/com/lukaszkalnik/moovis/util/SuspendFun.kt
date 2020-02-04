package com.lukaszkalnik.moovis.util

interface SuspendFun<U> {
    suspend operator fun invoke(): U
}
