package br.edu.utfpr.comunicacaowebmobile.util.helpers

import android.app.AlertDialog
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import br.edu.utfpr.comunicacaowebmobile.R
import br.edu.utfpr.comunicacaowebmobile.util.DialogCallback


class DialogHelper(private val context: Context) {

    fun mostrarConfirmacao(mensagem: String, positiveCallback: DialogCallback?, negativeCallback: DialogCallback?) {
        if (!TextUtils.isEmpty(mensagem)) {
            try {
                AlertDialog.Builder(context)
                        .setTitle(context.getString(R.string.dlg_title_confirm))
                        .setMessage(mensagem)
                        .setCancelable(false)
                        .setPositiveButton("Sim", { dialog, _ ->
                            positiveCallback?.execute()
                            dialog.dismiss()
                        })
                        .setNegativeButton("Não", { dialog, _ ->
                            negativeCallback?.execute()
                            dialog.dismiss()
                        })
                        .setIcon(R.drawable.ic_dialog_question)
                        .create()
                        .show()
            } catch (e: Exception) {
                Log.e("ConfirmDialogFail", "Fail to display the following message: '$mensagem'. Cause: ", e)
            }

        }
    }

    fun mostrarErro(mensagem: String, callback: DialogCallback?) {
        if (!TextUtils.isEmpty(mensagem)) {
            try {
                AlertDialog.Builder(context)
                        .setTitle(context.getString(R.string.dlg_title_error))
                        .setMessage(mensagem)
                        .setCancelable(false)
                        .setPositiveButton("OK", { dialog, _ ->
                            callback?.execute()
                            dialog.dismiss()
                        })
                        .setIcon(R.drawable.ic_dialog_error)
                        .create()
                        .show()
            } catch (e: Exception) {
                Log.e("ErrorDialogFail", "Fail to display the following message: '$mensagem'. Cause: ", e)
            }
        }
    }

    fun mostrarToast(stringResource: Int) {
        Toast.makeText(context, stringResource, Toast.LENGTH_LONG).show()
    }

}