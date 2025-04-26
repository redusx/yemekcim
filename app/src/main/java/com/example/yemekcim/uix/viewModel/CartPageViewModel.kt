package com.example.yemekcim.uix.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yemekcim.data.entity.SepetYemek
import com.example.yemekcim.data.repo.YemeklerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartPageViewModel @Inject constructor(private val repo: YemeklerRepository): ViewModel() {

    private val _sepetListesi = mutableStateOf<List<SepetYemek>>(emptyList())
    val sepetListesi: State<List<SepetYemek>> = _sepetListesi

    fun sepettekiYemekleriGetir(kullaniciAdi: String = "riza") {
        viewModelScope.launch {
            try {
                _sepetListesi.value = repo.sepettekiYemekleriGetir(kullaniciAdi)
            } catch (e: Exception) {
                Log.e("Sepet", "Sepet çekilemedi: ${e.localizedMessage}")
            }
        }
    }

    fun yemekSil(sepetYemekId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val kullaniciAdi = "riza"

                val silmeBasarili = repo.sepettenYemekSil(sepetYemekId, kullaniciAdi)

                if (silmeBasarili) {
                    Log.d("ViewModel", "Silme başarılı: ID=$sepetYemekId. Sepet yeniden yükleniyor.")
                    sepettekiYemekleriGetir()
                } else {
                    Log.e("ViewModel", "API silme işlemi başarısız oldu: ID=$sepetYemekId")
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "yemekSil sırasında genel hata: ${e.localizedMessage}", e)
            }
        }
    }

}
