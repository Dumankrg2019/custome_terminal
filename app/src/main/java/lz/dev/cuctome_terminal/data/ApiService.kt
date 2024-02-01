package lz.dev.cuctome_terminal.data

import retrofit2.http.GET

interface ApiService {

    @GET("aggs/ticker/AAPL/range/1/hour/2023-01-09/2024-01-09?adjusted=true&sort=desc&limit=50000&apiKey=9qBEhmNcwzuibpgDu0qewGx7ZexIoIba")
    suspend fun loadBars(): Result
}