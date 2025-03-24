package com.example.yemekcim.data.datasource

import com.example.yemekcim.data.entity.Yemekler
import com.example.yemekcim.retrofit.YemeklerDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class YemeklerDataSource(var yemeklerDao:YemeklerDao) {

    suspend fun tumYemekleriGetir(): List<Yemekler> = withContext(Dispatchers.IO){
        return@withContext yemeklerDao.tumYemekleriGetir().yemekler
    }

}