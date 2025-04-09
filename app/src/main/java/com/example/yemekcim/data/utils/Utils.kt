package com.example.yemekcim.data.utils

import android.content.Context
import android.net.ConnectivityManager
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

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork
    val capabilities = connectivityManager.getNetworkCapabilities(network)
    return capabilities != null
}

@Composable
fun YemekItem(yemek_resim_adi: String,isNetworkAvailable: Boolean) {
    if (isNetworkAvailable) {
        val BASE_URL = "http://kasimadalan.pe.hu/yemekler/resimler/"
        val resimUrl = "$BASE_URL${yemek_resim_adi}"

        val context = LocalContext.current
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(context)
                .data(resimUrl)
                .crossfade(true)
                .placeholder(R.drawable.imageerror)
                .error(R.drawable.imageerror)
                .build()
        )

        Image(
            painter = painter,
            contentDescription = yemek_resim_adi,
            modifier = Modifier
                .size(100.dp, 150.dp)
                .clip(RoundedCornerShape(10.dp))
        )
    }else {
        Image(
            painter = painterResource(id = R.drawable.imageerror),
            contentDescription = "İnternet yok",
            modifier = Modifier.size(100.dp, 150.dp).clip(RoundedCornerShape(10.dp))
        )
    }

}