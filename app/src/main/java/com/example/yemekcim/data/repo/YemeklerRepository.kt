package com.example.yemekcim.data.repo

import com.example.yemekcim.data.datasource.YemeklerDataSource
import com.example.yemekcim.data.entity.Yemekler

class YemeklerRepository(var yds: YemeklerDataSource){

    suspend fun tumYemekleriGetir() : List<Yemekler> = yds.tumYemekleriGetir()
}