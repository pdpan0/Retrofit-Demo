package com.pdpano.retrofitdemo.services

import com.pdpano.retrofitdemo.data.Albums
import com.pdpano.retrofitdemo.data.AlbumsItem
import retrofit2.Response
import retrofit2.http.*

interface AlbumService {
    /*
    @GET() -> Anotação que indica o método e o endpoint que ira fazer a chamada.
    @POST() -> Anotação que indica o método e o endpoint que ira fazer a chamada.

    suspend -> faz parte do kotlin coroutines para fazer ser uma função assíncrona
    Response<Albums> -> é o tipo da resposta que iremos receber.
     */
    @GET("/albums")
    suspend fun getAlbums() : Response<Albums>

    @GET("/albums")
    suspend fun getSortedAlbums(@Query("userId") userId: Int) : Response<Albums>

    @GET("/albums/{id}")
    suspend fun getAlbumById(@Path("id") albumId: Int) : Response<AlbumsItem>

    @POST("/albums")
    suspend fun uploadAlbum(@Body album: AlbumsItem) : Response<AlbumsItem>
}