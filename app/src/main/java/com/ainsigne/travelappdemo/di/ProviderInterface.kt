package com.ainsigne.travelappdemo.di

interface Provider<T> {
    fun get(): T
}