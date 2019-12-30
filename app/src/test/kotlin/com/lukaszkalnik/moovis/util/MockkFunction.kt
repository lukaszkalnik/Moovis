package com.lukaszkalnik.moovis.util

interface MockkFunction<U> {
    suspend operator fun invoke(): U
}