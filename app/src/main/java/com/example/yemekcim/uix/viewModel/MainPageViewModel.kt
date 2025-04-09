package com.example.yemekcim.uix.viewModel

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.yemekcim.R
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

    val kategoriyeGoreYemekler = mapOf(
        "0" to listOf("kofte.png", "ayran.png", "baklava.png", "fanta.png",
            "izgarasomon.png", "izgaratavuk.png", "kadayif.png", "kahve.png",
            "lazanya.png", "makarna.png", "pizza.png", "su.png",
            "sutlac.png", "tiramisu.png"),
        "1" to listOf("kofte.png", "izgarasomon.png", "izgaratavuk.png", "lazanya.png", "makarna.png", "pizza.png"),
        "2" to listOf("ayran.png", "fanta.png", "kahve.png", "su.png"),
        "3" to listOf("baklava.png", "kadayif.png", "sutlac.png", "tiramisu.png"),
        "4" to listOf() // Dondurma kategorisi boş
    )




}
