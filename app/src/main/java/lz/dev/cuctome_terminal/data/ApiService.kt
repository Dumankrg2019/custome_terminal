package lz.dev.cuctome_terminal.data

import lz.dev.cuctome_terminal.presentation.TimeFrame
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("aggs/ticker/AAPL/range/{timeFrame}/2023-01-09/2024-01-09?adjusted=true&sort=desc&limit=50000&apiKey=9qBEhmNcwzuibpgDu0qewGx7ZexIoIba")
    suspend fun loadBars(
        @Path("timeFrame") timeFrame: String
    ): Result
}