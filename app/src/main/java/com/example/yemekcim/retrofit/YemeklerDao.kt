package com.example.yemekcim.retrofit

import com.example.yemekcim.data.entity.CRUDanswer
import com.example.yemekcim.data.entity.SepetYemekCevap
import com.example.yemekcim.data.entity.YemeklerCevap
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface YemeklerDao {

    //Yemekleri Getir
    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun tumYemekleriGetir() : YemeklerCevap


    //Sepete Yemek Ekle
    @FormUrlEncoded
    @POST("yemekler/sepeteYemekEkle.php")
    suspend fun sepeteYemekEkle(
        @Field("yemek_adi") yemekAdi: String,
        @Field("yemek_resim_adi") yemekResimAdi: String,
        @Field("yemek_fiyat") yemekFiyat: Int,
        @Field("yemek_siparis_adet") yemekSiparisAdet: Int,
        @Field("kullanici_adi") kullaniciAdi: String
    ): CRUDanswer

    //Sepetteki Yemekleri Getir
    @FormUrlEncoded
    @POST("yemekler/sepettekiYemekleriGetir.php")
    suspend fun sepettekiYemekleriGetir(
        @Field("kullanici_adi") kullaniciAdi: String
    ): SepetYemekCevap

    @FormUrlEncoded
    @POST("yemekler/sepettenYemekSil.php")
    suspend fun sepettenYemekSil(
        @Field("sepet_yemek_id") sepetYemekId: Int,
        @Field("kullanici_adi") kullaniciAdi: String
    ):CRUDanswer
}