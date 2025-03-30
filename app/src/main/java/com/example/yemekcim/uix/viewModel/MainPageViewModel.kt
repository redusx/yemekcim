package com.example.yemekcim.uix.viewModel

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
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

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .size(120.dp, 260.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            Image(
                painter = rememberAsyncImagePainter(resimUrl),
                contentDescription = yemek_resim_adi,
                modifier = Modifier
                    .size(100.dp, 150.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Text(
                text = when(yemek_resim_adi){
                    "kofte.png" -> "Köfte"
                    "izgarasomon.png" -> "Izgara Somon"
                    "kadayif.png" -> "Kadayıf"
                    "sutlac.png" -> "Sütlaç"
                    "izgaratavuk.png" -> "Izgara Tavuk"
                    else -> yemek_resim_adi.replaceFirstChar { it.uppercaseChar() }.dropLast(4)
                },
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
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
