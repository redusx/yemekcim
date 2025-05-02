package com.example.yemekcim.data.repo

import android.util.Log
import com.example.yemekcim.data.entity.SepetYemek
import com.example.yemekcim.data.entity.Yemekler
import com.example.yemekcim.retrofit.YemeklerDao
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class YemeklerRepository @Inject constructor(
    private val yemeklerDao: YemeklerDao
) {
    suspend fun tumYemekleriGetir(): List<Yemekler> {
        return try {
            val response = yemeklerDao.tumYemekleriGetir()
            if (response.success == 1) {
                response.yemekler
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            println("Veri çekerken hata oluştu: ${e.message}")
            emptyList()
        }
    }

    suspend fun sepeteYemekEkle(
        yemekAdi: String,
        yemekResimAdi: String,
        yemekFiyat: Int,
        yemekSiparisAdet: Int,
        kullaniciAdi: String
    ) = yemeklerDao.sepeteYemekEkle(yemekAdi, yemekResimAdi, yemekFiyat, yemekSiparisAdet, kullaniciAdi)


    suspend fun sepettekiYemekleriGetir(kullaniciAdi: String): List<SepetYemek> {
        return yemeklerDao.sepettekiYemekleriGetir(kullaniciAdi).sepetYemekler
    }

    suspend fun sepettenYemekSil(sepetYemekId: Int, kullaniciAdi: String): Boolean {
        return try {
            val response = yemeklerDao.sepettenYemekSil(sepetYemekId, kullaniciAdi)
            Log.d("Repository", "Sepetten Silme Cevabı: Success=${response.success}, Message=${response.message}")
            response.success == 1
        } catch (e: Exception) {
            Log.e("Repository", "Sepetten Silme Hatası: ${e.localizedMessage}", e)
            false
        }
    }
}