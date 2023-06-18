package com.example.proyectofinal.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.Producto
import com.example.proyectofinal.R

class ProductListAdapter(private val productItemLayout:Int): RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    private var productList:List<Producto>? = null

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        /* Obtengo los componentes del xml del item para decir que ahi se almacenara
           el nombre del producto, cantidad y precio.
         */
        var item: TextView = itemView.findViewById(R.id.nameProducto)
        var item2: TextView = itemView.findViewById(R.id.canditadProductos)
        var item3: TextView = itemView.findViewById(R.id.precioTotalProducto)
    }

    @SuppressLint("SuspiciousIndentation", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*
        Ingreso los valores que resivo de la base de datos a los
        componentes correspondientes.
         */
        val item= holder.item
        productList.let {
            item.text =  it!![position].productName
        }

        val item2= holder.item2
        productList.let {
            item2.text =  it!![position].cantidadProducto.toString()
        }

        val item3= holder.item3
        productList.let {
            item3.text = "$" + it!![position].precioProduct.toString()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(productItemLayout,parent,false)
        return ViewHolder(view)
    }

    fun setProductList(products: List<Producto>){
        productList=products
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (productList == null) 0 else productList!!.size
    }


}