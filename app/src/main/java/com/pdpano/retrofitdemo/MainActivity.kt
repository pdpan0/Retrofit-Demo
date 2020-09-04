package com.pdpano.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.pdpano.retrofitdemo.data.Albums
import com.pdpano.retrofitdemo.data.AlbumsItem
import com.pdpano.retrofitdemo.services.RetrofitInstance
import com.pdpano.retrofitdemo.services.AlbumService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var retService: AlbumService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//      Cria uma instância do retrofit chamando nossa classe de serviço AlbumService
        retService = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)
    }

    fun getRequestWithQueryParameters(view: View){
//      Bloco do live data builder
        val responseLiveData: LiveData<Response<Albums>> = liveData {
            val response: Response<Albums> = retService.getSortedAlbums(3)
            emit(response)
        }
//      Observar o responseLiveData
        responseLiveData.observe(this, Observer {
            val albumsList: MutableListIterator<AlbumsItem>? = it.body()?.listIterator()
            if(albumsList!=null) {
                while (albumsList.hasNext()) {
                    val albumsItem: AlbumsItem = albumsList.next()
                    val result =" "+"Album Title: ${albumsItem.title}"+"\n"+
                            " "+"Album Id: ${albumsItem.id}"+"\n"+
                            " "+"User Id: ${albumsItem.userId}"+"\n\n\n"

                    text_view.append(result)
                }
            }
        })
    }

    fun getRequestPathParameters(view: View){
//      Path Parameter Exemplo
        val pathResponse: LiveData<Response<AlbumsItem>> = liveData {
            val response = retService.getAlbumById(3)
            emit(response)
        }

        pathResponse.observe(this, Observer {
            val title = it.body()?.title
            Toast.makeText(applicationContext, title,Toast.LENGTH_LONG).show()
        })
    }

    fun uploadAlbum(view: View) {
        val album = AlbumsItem(0, "My title", 3)

        val postResponse : LiveData<Response<AlbumsItem>> = liveData {
            val response = retService.uploadAlbum(album)
            emit(response)
        }
        postResponse.observe(this, Observer {
            val receivedAlbumsItem: AlbumsItem? = it.body()
            val result =" "+"Album Title: ${receivedAlbumsItem?.title}"+"\n"+
                    " "+"Album Id: ${receivedAlbumsItem?.id}"+"\n"+
                    " "+"User Id: ${receivedAlbumsItem?.userId}"+"\n\n\n"

            text_view.text = result
        })
    }
}