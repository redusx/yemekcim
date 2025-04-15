package com.example.yemekcim.retrofit

import com.example.yemekcim.data.entity.CRUDanswer
import com.example.yemekcim.data.entity.YemeklerCevap
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST


interface YemeklerDao {


    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun tumYemekleriGetir() : YemeklerCevap

}