package com.example.yemekcim.data.repo

//import com.example.yemekcim.data.datasource.YemeklerDataSource
import com.example.yemekcim.data.entity.SepetYemek
import com.example.yemekcim.data.entity.Yemekler
import com.example.yemekcim.retrofit.YemeklerDao
import javax.inject.Inject
import javax.inject.Singleton

//class YemeklerRepository(var yds: YemeklerDataSource){
//
//    suspend fun tumYemekleriGetir() : List<Yemekler> = yds.tumYemekleriGetir()
//}

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
}