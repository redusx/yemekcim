package com.example.yemekcim.data.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.yemekcim.R

//fun isInternetAvailable(context: Context): Boolean {
//    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
//
//    if (connectivityManager == null) {
//        Log.w("InternetCheck", "ConnectivityManager alınamadı.")
//        return false
//    }
//
//    try {
//        val network = connectivityManager.activeNetwork ?: return false
//        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
//        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
//
//    } catch (e: Exception) {
//        Log.e("InternetCheck", "İnternet durumu kontrol edilirken hata oluştu", e)
//        return false
//    }
//}

//@Composable
//fun YemekItem(yemek_resim_adi: String) {
//
//    val BASE_URL = "http://kasimadalan.pe.hu/yemekler/resimler/"
//    val resimUrl = "$BASE_URL${yemek_resim_adi}"
//
//    val context = LocalContext.current
//    val painter = rememberAsyncImagePainter(
//        model = ImageRequest.Builder(context)
//            .data(resimUrl)
//            .crossfade(true)
//            .placeholder(R.drawable.imageerror)
//            .error(R.drawable.imageerror)
//            .build()
//    )
//
//    Image(
//        painter = painter,
//        contentDescription = yemek_resim_adi,
//        modifier = Modifier
//            .size(100.dp, 150.dp)
//            .clip(RoundedCornerShape(10.dp))
//    )
//}