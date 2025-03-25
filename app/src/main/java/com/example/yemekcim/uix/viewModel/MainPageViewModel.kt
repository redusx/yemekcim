package com.example.yemekcim.uix.viewModel

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import coil.compose.rememberAsyncImagePainter
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

    @Composable
    fun YemekItem(yemek_resim_adi:String) {
        val BASE_URL = "http://kasimadalan.pe.hu/yemekler/resimler/"
        val resimUrl = "$BASE_URL${yemek_resim_adi}"

        Image(
            painter = rememberAsyncImagePainter(resimUrl),
            contentDescription = yemek_resim_adi,
            modifier = Modifier
                .size(100.dp,250.dp)
                .clip(RoundedCornerShape(10.dp))
        )
    }

//    fun getYemekResimAdiById(yemekId: String): String? {
//        return yemeklerListesi.find { it.yemek_id == yemekId }?.yemek_resim_adi
//    }
}
