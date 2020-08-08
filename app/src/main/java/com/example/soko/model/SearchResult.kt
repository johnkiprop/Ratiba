package com.example.soko.model

sealed class SearchResult {
    data class Success(val data: List<ProductItems>) : SearchResult()
    data class Error(val error: Exception) : SearchResult()
}