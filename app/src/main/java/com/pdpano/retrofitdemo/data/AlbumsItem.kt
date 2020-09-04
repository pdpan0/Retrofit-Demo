package com.pdpano.retrofitdemo.data

/*
    SerializedName converte o nome do atributo para o valor passado,
    antes de qualquer operação
 */
import com.google.gson.annotations.SerializedName

data class AlbumsItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("userId")
    val userId: Int
)