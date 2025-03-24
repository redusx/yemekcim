package com.example.yemekcim.uix.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yemekcim.data.entity.Yemekler
import com.example.yemekcim.data.repo.YemeklerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(private val yrepo: YemeklerRepository) :ViewModel() {
    var yemeklerListesi = MutableLiveData<List<Yemekler>>()


    init {
        getYemekler()
    }
    fun getYemekler() {
        CoroutineScope(Dispatchers.Main).launch{
            yemeklerListesi.value = yrepo.tumYemekleriGetir()
        }
    }


//    fun getYemekResimAdiById(yemekId: String): String? {
//        return yemeklerListesi.find { it.yemek_id == yemekId }?.yemek_resim_adi
//    }
}
