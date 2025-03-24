package com.example.yemekcim.data.entity

data class Yemekler(
    var yemek_id:Int,
    var yemek_adi:String,
    var yemek_resim_adi:String,
    var yemek_fiyat:Int
){}







//@Entity(tableName = "yemekler")
//data class yemekler(
//    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "yemek_id") @NotNull var yemek_id:Int,
//    @ColumnInfo("yemek_adi")@NotNull var yemek_adi:String,
//    @ColumnInfo("yemek_resim_adi")@NotNull var yemek_resim_adi:String,
//    @ColumnInfo("yemek_fiyat")@NotNull var yemek_fiyat:Int
//){}
