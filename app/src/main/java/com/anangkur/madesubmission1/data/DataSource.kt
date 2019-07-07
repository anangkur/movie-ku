package com.anangkur.madesubmission1.data

interface DataSource {
    fun saveLanguage(language: String)
    fun loadLanguage(): String?
}