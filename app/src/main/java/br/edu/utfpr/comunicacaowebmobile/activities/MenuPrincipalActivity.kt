package br.edu.utfpr.comunicacaowebmobile.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import br.edu.utfpr.comunicacaowebmobile.R
import br.edu.utfpr.comunicacaowebmobile.util.DialogCallback
import br.edu.utfpr.comunicacaowebmobile.util.SHARED_PREF_TOKEN
import br.edu.utfpr.comunicacaowebmobile.util.helpers.AppHelper
import br.edu.utfpr.comunicacaowebmobile.util.helpers.DialogHelper
import kotlinx.android.synthetic.main.activity_menu_principal.*

class MenuPrincipalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)
        btnCidades.setOnClickListener { iniciarListaCidades() }
    }

    override fun onBackPressed() {
        DialogHelper(this).mostrarConfirmacao(getString(R.string.confirm_close_app), object: DialogCallback {
            override fun execute() {
                finish()
            }
        }, null)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.itemDesconectar -> {
                DialogHelper(this@MenuPrincipalActivity).mostrarConfirmacao(getString(R.string.desconectar_confirmar),
                        object: DialogCallback {
                            override fun execute() {
                                AppHelper(this@MenuPrincipalActivity).setStringPref(SHARED_PREF_TOKEN, "")
                                finish()
                            }
                        }, null)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun iniciarListaCidades() {
        startActivity(Intent(this, CidadesActivity::class.java))
    }

    private fun iniciarListaClientes() {
        // TODO
    }

    private fun iniciarListPedidos() {
        // TODO
    }

    private fun iniciarListaProdutos() {
        // TODO
    }
}
