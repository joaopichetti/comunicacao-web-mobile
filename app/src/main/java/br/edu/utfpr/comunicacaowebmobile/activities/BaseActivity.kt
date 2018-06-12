package br.edu.utfpr.comunicacaowebmobile.activities

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.edu.utfpr.comunicacaowebmobile.R

open class BaseActivity: AppCompatActivity() {

    private lateinit var pDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pDialog = ProgressDialog(this)
        pDialog.setMessage(getString(R.string.aguarde))
    }

    protected fun hideProgress() {
        if (pDialog.isShowing) {
            pDialog.dismiss()
        }
    }

    protected fun showProgress() {
        if (!pDialog.isShowing) {
            pDialog.show()
        }
    }

}