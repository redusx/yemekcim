package com.example.yemekcim.uix.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.yemekcim.data.entity.Yemekler
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog


@Composable
fun SepeteEkleDialog(
    yemek: Yemekler,
    onDismiss: () -> Unit,
    onEkle: (adet: Int) -> Unit
) {
    var adet by remember { mutableStateOf(1) }

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFe6e7e8))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
                    Text(
                        text = yemek.yemek_adi,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp,
                        modifier = Modifier.padding(top = 8.dp),
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                AsyncImage(
                    model = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}",
                    contentDescription = yemek.yemek_adi,
                    modifier = Modifier
                        .size(160.dp)
                        .padding(start = 10.dp)
                )
                Text(text = "Tutar:${adet*yemek.yemek_fiyat}₺", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = { if (adet > 1) adet-- },shape = RoundedCornerShape(10.dp)) { Text("-") }
                    Text(text = adet.toString(), modifier = Modifier.padding(horizontal = 16.dp))
                    Button(onClick = { adet++ },shape = RoundedCornerShape(10.dp)) { Text("+") }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Button(
                        onClick = onDismiss,
                        Modifier.padding(start = 15.dp, end = 5.dp).weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        )
                    ) {
                        Text("İptal")
                    }
                    Button(
                        onClick = {
                            onEkle(adet)
                            onDismiss()
                        },
                        modifier = Modifier.padding(end = 15.dp, start = 5.dp).weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Sepete Ekle")
                    }
                }
            }
        }
    }
}