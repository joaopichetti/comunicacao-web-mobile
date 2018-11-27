package br.edu.utfpr.comunicacaowebmobile.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import br.edu.utfpr.comunicacaowebmobile.R
import br.edu.utfpr.comunicacaowebmobile.model.dto.Credentials
import br.edu.utfpr.comunicacaowebmobile.services.ServiceGenerator
import br.edu.utfpr.comunicacaowebmobile.services.UsuarioService
import br.edu.utfpr.comunicacaowebmobile.util.RESULT_CODE_LOGIN_SUCCESSFUL
import br.edu.utfpr.comunicacaowebmobile.util.SHARED_PREF_TOKEN
import br.edu.utfpr.comunicacaowebmobile.util.helpers.AppHelper
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnAcessar.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if (dadosValidos()) {
            efetuarLogin()
        }
    }

    private fun dadosValidos(): Boolean {
        return when {
            TextUtils.isEmpty(edtUsername.text.toString()) -> {
                edtUsername.requestFocus()
                Toast.makeText(this, R.string.error_username_required, Toast.LENGTH_LONG).show()
                false
            }
            TextUtils.isEmpty(edtPassword.text.toString()) -> {
                edtPassword.requestFocus()
                Toast.makeText(this, R.string.error_password_required, Toast.LENGTH_LONG).show()
                false
            }
            else -> true
        }
    }

    private fun efetuarLogin() {
        showProgress()
        val service = ServiceGenerator.createService(UsuarioService::class.java)
        val credentials = Credentials(edtUsername.text.toString(), edtPassword.text.toString())
        val call = service.login(credentials)
        call.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                if (response?.isSuccessful == true) {
                    val token = response.headers()?.get("Authorization")?.replace("Bearer ", "")
                    if (TextUtils.isEmpty(token)) {
                        fail()
                    } else {
                        AppHelper(this@LoginActivity).setStringPref(SHARED_PREF_TOKEN, token ?: "")
                        setResult(RESULT_CODE_LOGIN_SUCCESSFUL)
                        finish()
                    }
                } else {
                    fail()
                }
            }

            override fun onFailure(call: Call<Void>?, t: Throwable?) {
                t?.printStackTrace()
                fail()
            }

            private fun fail() {
                Toast.makeText(this@LoginActivity, R.string.error_invalid_credentials, Toast.LENGTH_LONG).show()
                hideProgress()
            }
        })
    }

}
