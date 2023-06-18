package com.example.proyectofinal.ui.main


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofinal.Producto
import com.example.proyectofinal.R
import com.example.proyectofinal.databinding.FragmentListaProductosBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [listaProductos.newInstance] factory method to
 * create an instance of this fragment.
 */
class listaProductos : Fragment() {
    private var adapter: ProductListAdapter? = null
    private val viewModel: MainViewModel by viewModels()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentListaProductosBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListaProductosBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment listaProductos.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            listaProductos().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Evento para regresar a la ventana del formulario
        binding.btnagregarMasProducto.setOnClickListener {
            val action = listaProductosDirections.regresoFormulario()
            findNavController().navigate(action)
        }

        // Obtiene todos nuestros productos desde la base de datos
        viewModel.obtenerProductos()?.observe(viewLifecycleOwner) { products ->
            products?.let {
                adapter?.setProductList(it)
            }
        }
        // Llamamos al metodo que agregar los items al recyclerView
        recyclerSetup()
        // metodo que se encarga de calcular el total de pagar y lo asigna a un textView
        calcularCuentaTotal()
    }

    private fun calcularCuentaTotal() {
        // Creo mi variable que me ayudara a saber el total que se gastara
        var totalPrecio: Int = 0
        // Creo una variable tipo LiveData para obtener la consulta
        val recorrerLosDatos: LiveData<List<Producto>> = viewModel.obtenerProductos()!!
        // Hago un observer para que este pendiente de los cambios de la base de datos
        recorrerLosDatos.observe(viewLifecycleOwner, Observer { listaProductos ->
            // Verificar si la lista de productos no es nula
            listaProductos?.let {
                // Recorrer los elementos de la lista de productos
                for (producto in it) {
                    // Acceder a los atributos del producto
                    val cantidad = producto.cantidadProducto
                    val precio = producto.precioProduct
                    // Calculo el total que pagara el usuario
                    totalPrecio += cantidad * precio
                }
                // Agrego el resultado final al textView totalPrecio
                binding.totalPrecio.setText("$"+totalPrecio)
            }
        })
    }

    // Metodo que ingresa los items al recyclerView
    private fun recyclerSetup() {
        adapter = ProductListAdapter(R.layout.item)
        binding.productRecycler.layoutManager = LinearLayoutManager(context)
        binding.productRecycler.adapter = adapter
    }
}