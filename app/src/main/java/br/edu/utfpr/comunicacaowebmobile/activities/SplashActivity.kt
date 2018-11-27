package br.edu.utfpr.comunicacaowebmobile.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import br.edu.utfpr.comunicacaowebmobile.R
import br.edu.utfpr.comunicacaowebmobile.model.servidor.Usuario
import br.edu.utfpr.comunicacaowebmobile.services.ServiceGenerator
import br.edu.utfpr.comunicacaowebmobile.services.UsuarioService
import br.edu.utfpr.comunicacaowebmobile.util.RESULT_CODE_LOGIN_SUCCESSFUL
import br.edu.utfpr.comunicacaowebmobile.util.SHARED_PREF_TOKEN
import br.edu.utfpr.comunicacaowebmobile.util.helpers.AppHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        verificarCredenciais()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (REQUEST_CODE == requestCode && resultCode == RESULT_CODE_LOGIN_SUCCESSFUL) {
            iniciarMenuPrincipal()
        } else {
            finish()
        }
    }

    private fun iniciarLogin() {
        startActivityForResult(Intent(this, LoginActivity::class.java), REQUEST_CODE)
    }

    private fun iniciarMenuPrincipal() {
        startActivity(Intent(this, MenuPrincipalActivity::class.java))
        finish()
    }

    private fun verificarCredenciais() {
        val token = AppHelper(this).getStringPref(SHARED_PREF_TOKEN)
        if (TextUtils.isEmpty(token)) {
            iniciarLogin()
        } else {
            val service = ServiceGenerator.createService(UsuarioService::class.java)
            val call = service.getCurrentUser("Bearer $token")
            call.enqueue(object: Callback<Usuario> {
                override fun onResponse(call: Call<Usuario>?, response: Response<Usuario>?) {
                    if (response?.isSuccessful == true) {
                        iniciarMenuPrincipal()
                    } else {
                        fail()
                    }
                }

                override fun onFailure(call: Call<Usuario>?, t: Throwable?) {
                    fail()
                }

                private fun fail() {
                    AppHelper(this@SplashActivity).setStringPref(SHARED_PREF_TOKEN, "")
                    iniciarLogin()
                }
            })
        }
    }
}
