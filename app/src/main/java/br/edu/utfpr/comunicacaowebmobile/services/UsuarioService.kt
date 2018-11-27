package br.edu.utfpr.comunicacaowebmobile.services

import br.edu.utfpr.comunicacaowebmobile.model.dto.Credentials
import br.edu.utfpr.comunicacaowebmobile.model.servidor.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UsuarioService {

    @GET("rest/usuarios/current")
    fun getCurrentUser(@Header("Authorization") authToken: String): Call<Usuario>
    @POST("rest/login")
    fun login(@Body credentials: Credentials): Call<Void>

}