package com.anangkur.madesubmission1.data.model

data class Response(
    val dates: Dates,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int,
    var status_message: String,
    val status_code: Int
)