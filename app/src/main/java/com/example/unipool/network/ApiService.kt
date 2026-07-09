package com.example.unipool.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("login.php")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("get_trips.php")
    fun getTrips(): Call<List<TripResponse>>

    @POST("book_seat.php")
    fun bookSeat(@Body request: BookSeatRequest): Call<GenericResponse>
}
