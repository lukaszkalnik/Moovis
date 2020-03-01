package com.lukaszkalnik.moovis.util

interface SuspendFun<U> {
    suspend operator fun invoke(): U
}

interface SuspendFun1<A, U> {
    suspend operator fun invoke(a: A): U
}
