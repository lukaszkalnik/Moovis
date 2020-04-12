package com.lukaszkalnik.moovis.util

import arrow.core.Either
import arrow.core.Left

/**
 * Inlined version of [Either] `flatMap` to allow passing suspending function as [f].
 */
inline fun <A, B, C> Either<A, B>.suspendFlatMap(f: (B) -> Either<A, C>): Either<A, C> = fold({ Left(it) }, f)
