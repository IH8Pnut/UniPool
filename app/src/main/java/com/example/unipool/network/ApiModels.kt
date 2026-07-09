package com.example.unipool.network

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val message: String,
    val user: UserData?
)

data class UserData(
    val id: Int,
    val username: String,
    val email: String,
    val role: String
)

data class TripResponse(
    val id: Int,
    @SerializedName("shuttle_id") val shuttleId: Int,
    @SerializedName("departure_time") val departureTime: String,
    @SerializedName("arrival_time") val arrivalTime: String,
    val destination: String,
    val status: String,
    val passengers: Int,
    val capacity: Int,
    @SerializedName("driver_name") val driverName: String
)

data class BookSeatRequest(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("trip_id") val tripId: Int,
    val seat: String
)

data class GenericResponse(
    val message: String
)
