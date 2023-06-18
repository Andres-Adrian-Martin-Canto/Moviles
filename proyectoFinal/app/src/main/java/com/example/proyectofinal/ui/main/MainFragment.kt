package com.example.proyectofinal.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.viewModels
import com.example.proyectofinal.Producto

import com.example.proyectofinal.databinding.FragmentMainBinding
import java.util.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Evento que crea la accion para navegar a la otra ventana.
        binding.btnVerLista.setOnClickListener {
            // Limpiar los paint
            limpiarPaint()
            // Ira a la otra pantalla.
            val action = MainFragmentDirections.navegarALista()
            findNavController().navigate(action)
        }

        // Evento para eliminar un producto
        binding.btnEliminar.setOnClickListener {
            val nombreProducto = binding.editNameProduct.text.toString()
            if (nombreProducto != "") {
                // Le enviamos como parametro el contenido ingresado por el usuairo
                viewModel.deleteProduct(binding.editNameProduct.text.toString())
            }else{
                Toast.makeText(context, "Ingresa un nombre de producto", Toast.LENGTH_SHORT)
                    .show()
            }
            limpiarPaint()
        }

        // Evento para buscar un elemento en especifico
        binding.btnBuscar.setOnClickListener {
            // Checamos que no este vacio la busqueta del producto
            val nombreProducto = binding.editNameProduct.text.toString()
            if (nombreProducto != "") {
                // Le enviamos el parametro de busqueda
                viewModel.buscar(binding.editNameProduct.text.toString())
                // Nos regresa el resultado y lo ponemos dentro de los paint
                viewModel.getSearchResults().observe(viewLifecycleOwner) { products ->
                    products?.let {
                        if (it.isNotEmpty()) {
                            // Le asignamos los parametros correspondientes
                            binding.editNameProduct.setText(it[0].productName)
                            binding.editCantidadProducto.setText(it[0].cantidadProducto.toString())
                            binding.editPrecioProduct.setText(it[0].precioProduct.toString())
                        } else {
                            // Si no lo encuentra muestra que no se encontro
                            Toast.makeText(context, "No se encontro elemento", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }else{
                Toast.makeText(context, "Ingresa un producto", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        // Boton que agregara datos.
        binding.btnAgregar.setOnClickListener {
            // Almacenamos los panel correctos
            val nombreProducto = binding.editNameProduct.text.toString()
            val precio = binding.editPrecioProduct.text.toString()
            val cantidad = binding.editCantidadProducto.text.toString()
            // Condicion que valida que no haya ningun paint vacio
            if (validarPaint()) {
                Toast.makeText(context, "Porfavor rellena todas las casillas", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // Condicion que valida que el nombre del producto no tenga numero
                if (!nombreProducto.matches(Regex(".*\\d.*"))) {
                    val product =
                        Producto(
                            nombreProducto,
                            Integer.parseInt(precio),
                            Integer.parseInt(cantidad)
                        )
                    viewModel.insertProduct(product)
                } else {
                    Toast.makeText(
                        context, "Solo ingresar letras en el " +
                                "apartado de nombre producto", Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
            limpiarPaint()
        }
    }

    // Funcion que se encarga de limpiar los paint
    fun limpiarPaint() {
        binding.editCantidadProducto.setText("")
        binding.editNameProduct.setText("")
        binding.editPrecioProduct.setText("")
    }

    // Funcion que se encarga de checar que no hayan pain vacios ni espacios
    fun validarPaint(): Boolean {
        val nombreProducto = binding.editNameProduct.text.toString()
        val precio = binding.editPrecioProduct.text.toString()
        val cantidad = binding.editCantidadProducto.text.toString()
        return !(nombreProducto.isNotEmpty() && !nombreProducto.contains(" ") || precio.isNotEmpty() && !precio.contains(" ") || cantidad.isNotEmpty() && !cantidad.contains(" "))
    }
}