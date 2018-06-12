package br.edu.utfpr.comunicacaowebmobile.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class AdapterListView<M>(var context: Context, var lista: MutableList<M>): BaseAdapter() {

    fun atualizarLista(lista: MutableList<M>?) {
        this.lista.clear()
        if (lista?.isEmpty() == false) {
            this.lista.addAll(lista)
            notifyDataSetChanged()
        } else {
            notifyDataSetInvalidated()
        }
    }

    override fun getView(position: Int, convertView: View?, rootView: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, rootView, false)
            viewHolder = ViewHolder()
            viewHolder.txtSimpleListItem = view.findViewById(android.R.id.text1)
            viewHolder.txtSimpleListItem?.setPadding(0, 10, 0, 10)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.txtSimpleListItem?.text = getItem(position)?.toString()
        return view
    }

    override fun getItem(position: Int): M {
        return lista[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return lista.size
    }

    private class ViewHolder {
        var txtSimpleListItem: TextView? = null
    }
}