package br.edu.utfpr.comunicacaowebmobile.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AbsListView
import android.widget.PopupMenu
import br.edu.utfpr.comunicacaowebmobile.R
import br.edu.utfpr.comunicacaowebmobile.adapters.AdapterListView
import br.edu.utfpr.comunicacaowebmobile.model.servidor.Cidade
import br.edu.utfpr.comunicacaowebmobile.services.CidadeService
import br.edu.utfpr.comunicacaowebmobile.services.ServiceGenerator
import br.edu.utfpr.comunicacaowebmobile.util.DialogCallback
import br.edu.utfpr.comunicacaowebmobile.util.EXTRA_CIDADE_ID
import br.edu.utfpr.comunicacaowebmobile.util.RESULT_CODE_REFRESH_LIST
import br.edu.utfpr.comunicacaowebmobile.util.SHARED_PREF_TOKEN
import br.edu.utfpr.comunicacaowebmobile.util.helpers.AppHelper
import br.edu.utfpr.comunicacaowebmobile.util.helpers.DialogHelper
import kotlinx.android.synthetic.main.activity_cidades.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CidadesActivity : BaseActivity(), PopupMenu.OnMenuItemClickListener {

    companion object {
        const val REQUEST_CODE = 2
    }

    private lateinit var adapter: AdapterListView<Cidade>
    private lateinit var cidadeService: CidadeService
    private var cidadeSelecionada: Cidade? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cidades)
        refreshableContainer.setOnRefreshListener {
            carregarCidades()
        }
        cidadeService = ServiceGenerator.createService(CidadeService::class.java)
        adapter = AdapterListView(this, mutableListOf())
        lstCidades.adapter = adapter
        lstCidades.setOnScrollListener(object: AbsListView.OnScrollListener {
            override fun onScroll(absListView: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                refreshableContainer.isEnabled = firstVisibleItem == 0
            }

            override fun onScrollStateChanged(absListView: AbsListView?, i: Int) {}
        })
        lstCidades.setOnItemClickListener { adapterView, view, position, _ ->
            val selectedItem = adapterView.getItemAtPosition(position)
            if (selectedItem is Cidade) {
                cidadeSelecionada = selectedItem
                val popup = PopupMenu(this@CidadesActivity, view)
                popup.menuInflater.inflate(R.menu.popup_acoes, popup.menu)
                popup.setOnMenuItemClickListener(this@CidadesActivity)
                popup.show()
            }
        }
        fabNovaCidade.setOnClickListener {
            iniciarFormulario(0)
        }
        carregarCidades()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (REQUEST_CODE == requestCode && RESULT_CODE_REFRESH_LIST == resultCode) {
            carregarCidades()
        }
    }

    override fun onMenuItemClick(menuItem: MenuItem?): Boolean {
        return when (menuItem?.itemId) {
            R.id.itemAlterar -> {
                alterarCidade()
                true
            }
            R.id.itemRemover -> {
                removerCidade()
                true
            }
            else -> false
        }
    }

    private fun ajustarTela() {
        txtEmptyLstCidades.visibility = if (adapter.count > 0) View.GONE else View.VISIBLE
    }

    private fun alterarCidade() {
        iniciarFormulario(cidadeSelecionada?.codigo ?: 0)
    }

    private fun carregarCidades() {
        val token = AppHelper(this).getStringPref(SHARED_PREF_TOKEN)
        val call = cidadeService.getCidades(token ?: "")
        call.enqueue(object: Callback<MutableList<Cidade>> {
            override fun onResponse(call: Call<MutableList<Cidade>>?, response: Response<MutableList<Cidade>>?) {
                if (response?.isSuccessful == true) {
                    adapter.atualizarLista(response.body())
                    ajustarTela()
                    refreshableContainer.isRefreshing = false
                    hideProgress()
                } else {
                    fail()
                }
            }

            override fun onFailure(call: Call<MutableList<Cidade>>?, t: Throwable?) {
                t?.printStackTrace()
                fail()
            }

            private fun fail() {
                ajustarTela()
                refreshableContainer.isRefreshing = false
                hideProgress()
                DialogHelper(this@CidadesActivity).mostrarErro(getString(R.string.error_load_cidades), null)
            }
        })
    }

    private fun iniciarFormulario(cidadeId: Int) {
        startActivityForResult(Intent(this, CidadesFormActivity::class.java)
                .putExtra(EXTRA_CIDADE_ID, cidadeId), REQUEST_CODE)
    }

    private fun removerCidade() {
        DialogHelper(this).mostrarConfirmacao(getString(R.string.remover_cidade_confirmar),
                object: DialogCallback {
                    override fun execute() {
                        showProgress()
                        val token = AppHelper(this@CidadesActivity).getStringPref(SHARED_PREF_TOKEN) ?: ""
                        val call = cidadeService.removerCidade(token, cidadeSelecionada?.codigo ?: 0)
                        call.enqueue(object: Callback<Void> {
                            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                                if (response?.isSuccessful == true) {
                                    carregarCidades()
                                } else {
                                    fail()
                                }
                            }

                            override fun onFailure(call: Call<Void>?, t: Throwable?) {
                                t?.printStackTrace()
                                fail()
                            }

                            private fun fail() {
                                hideProgress()
                                DialogHelper(this@CidadesActivity).mostrarErro(getString(R.string.error_remove_cidade), null)
                            }
                        })
                    }
        }, null)
    }
}
