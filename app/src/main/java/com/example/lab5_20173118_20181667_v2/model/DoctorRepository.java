package com.example.lab5_20173118_20181667_v2.model;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DoctorRepository {
    @GET("/api/")
    Call<DoctorDto> obtenerDoctor();
}
