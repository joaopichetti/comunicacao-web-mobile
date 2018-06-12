package br.edu.utfpr.comunicacaowebmobile.services

import br.edu.utfpr.comunicacaowebmobile.model.dto.Credentials
import br.edu.utfpr.comunicacaowebmobile.model.servidor.Cidade
import br.edu.utfpr.comunicacaowebmobile.model.servidor.Usuario
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @GET("rest/usuarios/current")
    fun getCurrentUser(@Header("Authorization") authToken: String): Call<Usuario>
    @POST("rest/login")
    fun login(@Body credentials: Credentials): Call<Any>
    @GET("rest/cidades")
    fun getCidades(@Header("Authorization") authToken: String): Call<MutableList<Cidade>>
    @GET("rest/cidades/{cidadeId}")
    fun getCidade(@Header("Authorization") authToken: String,
                  @Path("cidadeId") cidadeId: Int): Call<Cidade>
    @POST("rest/cidades")
    fun salvarCidade(@Header("Authorization") authToken: String,
                     @Body cidade: Cidade): Call<Cidade>
    @DELETE("rest/cidades/{cidadeId}")
    fun removerCidade(@Header("Authorization") authToken: String,
                      @Path("cidadeId") cidadeId: Int): Call<Any>

}