package com.anangkur.movieku.data.model

data class Response(
    val success: Boolean,
    val dates: Dates,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int,
    var status_message: String,
    val status_code: Int
)