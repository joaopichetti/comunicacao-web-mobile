package br.edu.utfpr.comunicacaowebmobile.activities

import android.os.Bundle
import android.text.TextUtils
import br.edu.utfpr.comunicacaowebmobile.R
import br.edu.utfpr.comunicacaowebmobile.model.servidor.Cidade
import br.edu.utfpr.comunicacaowebmobile.services.CidadeService
import br.edu.utfpr.comunicacaowebmobile.services.ServiceGenerator
import br.edu.utfpr.comunicacaowebmobile.util.EXTRA_CIDADE_ID
import br.edu.utfpr.comunicacaowebmobile.util.RESULT_CODE_REFRESH_LIST
import br.edu.utfpr.comunicacaowebmobile.util.SHARED_PREF_TOKEN
import br.edu.utfpr.comunicacaowebmobile.util.helpers.AppHelper
import br.edu.utfpr.comunicacaowebmobile.util.helpers.DialogHelper
import kotlinx.android.synthetic.main.activity_cidades_form.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CidadesFormActivity : BaseActivity() {

    private lateinit var cidade: Cidade
    private lateinit var cidadeService: CidadeService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cidades_form)
        cidadeService = ServiceGenerator.createService(CidadeService::class.java)
        val token = AppHelper(this).getStringPref(SHARED_PREF_TOKEN) ?: ""
        val cidadeId = intent.extras.getInt(EXTRA_CIDADE_ID, 0)
        if (cidadeId > 0) {
            title = getString(R.string.R_string_alterar_cidade, cidadeId)
            showProgress()
            val call = cidadeService.getCidade(token, cidadeId)
            call.enqueue(object : Callback<Cidade> {
                override fun onResponse(call: Call<Cidade>?, response: Response<Cidade>?) {
                    if (response?.isSuccessful == true) {
                        preencherCampos(response.body() ?:
                            Cidade(0, "", ""))
                        hideProgress()
                    } else {
                        fail()
                    }
                }

                override fun onFailure(call: Call<Cidade>?, t: Throwable?) {
                    t?.printStackTrace()
                    fail()
                }

                private fun fail() {
                    hideProgress()
                    DialogHelper(this@CidadesFormActivity)
                            .mostrarErro(getString(R.string.error_load_cidade), null)
                }
            })
        } else {
            setTitle(R.string.nova_cidade)
            preencherCampos(Cidade(cidadeId, "", ""))
        }
        btnSalvar.setOnClickListener {
            obterDados()
            if (dadosValidos()) {
                salvar()
            }
        }
    }

    private fun dadosValidos(): Boolean {
        val dialogHelper = DialogHelper(this)
        return when {
            TextUtils.isEmpty(cidade.nome) -> {
                edtNome.requestFocus()
                dialogHelper.mostrarToast(R.string.cidade_nome_required)
                false
            }
            TextUtils.isEmpty(cidade.ufSigla) -> {
                edtUfSigla.requestFocus()
                dialogHelper.mostrarToast(R.string.cidade_uf_sigla_required)
                false
            }
            else -> true
        }
    }

    private fun obterDados() {
        cidade.nome = edtNome.text.toString()
        cidade.ufSigla = edtUfSigla.text.toString()
    }

    private fun preencherCampos(cidade: Cidade) {
        this.cidade = cidade
        edtNome.setText(this.cidade.nome)
        edtUfSigla.setText(this.cidade.ufSigla)
    }

    private fun salvar() {
        showProgress()
        val token = AppHelper(this).getStringPref(SHARED_PREF_TOKEN) ?: ""
        val call = cidadeService.salvarCidade("Bearer $token", cidade)
        call.enqueue(object: Callback<Cidade> {
            override fun onResponse(call: Call<Cidade>?, response: Response<Cidade>?) {
                if (response?.isSuccessful == true) {
                    setResult(RESULT_CODE_REFRESH_LIST)
                    finish()
                } else {
                    fail()
                }
            }

            override fun onFailure(call: Call<Cidade>?, t: Throwable?) {
                t?.printStackTrace()
                fail()
            }

            private fun fail() {
                hideProgress()
                DialogHelper(this@CidadesFormActivity).mostrarErro(getString(R.string.error_save_cidade), null)
            }
        })
    }
}
