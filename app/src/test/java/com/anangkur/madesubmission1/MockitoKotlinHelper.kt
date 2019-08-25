package com.anangkur.madesubmission1

import org.mockito.*

fun <T> eq(obj: T): T = Mockito.eq<T>(obj)
fun <T> any(): T = Mockito.any<T>()
fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()
inline fun <reified T : Any> argumentCaptor(): ArgumentCaptor<T> = ArgumentCaptor.forClass(T::class.java)
inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)!!