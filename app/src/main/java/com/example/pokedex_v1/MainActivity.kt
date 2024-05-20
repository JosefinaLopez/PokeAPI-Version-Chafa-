package com.example.pokedex_v1

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex_v1.databinding.ActivityMainBinding
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.gson.responseObject
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PokemonAdapter(mutableListOf(),this@MainActivity) // Inicializar el adaptador con una lista vacía
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        SelectPokemonType()
        fetchPokemonTypes()
        //fetchPokemonData()
    }
/*
    private fun fetchPokemonData() {
        val url = "https://pokeapi.co/api/v2/pokemon?offset=0&limit=80"

        Fuel.get(url)
            .responseObject<PokemonListResponse> { _, _, result -> //Convierte el Json en tipo Objeto "PokemonListResponse"
                result.fold(success = { pokemonListResponse -> //resutl.fold maneja el caso exitoso
                    val pokemonList = pokemonListResponse.results //Pasa la a una variable el resultado de la consulta
                    pokemonList.forEach { pokemon -> //Itera sobre la lista, los pokemons , y manda a llamar a otra solicitud con otra API
                        fetchPokemonDetail(pokemon.url)
                    }
                }, failure = { error ->
                    println("Error al obtener la lista de Pokémon: $error")
                })
            }
    }*/

    private fun fetchPokemonDetail(url: String) {
        Fuel.get(url)
            .responseObject<PokemonDetail> { _, _, result ->
                result.fold(success = { pokemonDetail ->
                    adapter.addPokemon(pokemonDetail)
                }, failure = { error ->
                    println("Error al obtener el detalle del Pokémon: $error")
                })
            }
    }
    //Obtiene las categorias
    private fun fetchPokemonTypes() {
        Fuel.get("https://pokeapi.co/api/v2/type")
            .responseObject<Typess>() { request, response, result ->
                result.fold(success = { awa ->
                    val spinner = binding.Spinner
                    //Omitiendo la ultima , pq no da nada xd
                    for(i in 1..18) {
                        //Se agrega a una lista mutable , el resultado (nombre)
                        listTypes.add(awa.results[i].name)
                    }
                    //Se crea un adaptador
                    val adapter = ArrayAdapter(this,R.layout.spinner_layout, listTypes)
                    // layout a usar cuando la lista de opciones aparece
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter

                }, failure = { error ->
                    println("Error al obtener el detalle del Pokémon: $error")
                })
            }
    }
    private fun SelectPokemonType() {
        //Se instancia el spinner
        val spinner = binding.Spinner
        //Se crea un evento click
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            //Se instancia un metodo que permite seleccionar y saber su posicion del spinner
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedPokemon = listTypes[position]
                val url = "https://pokeapi.co/api/v2/type/${selectedPokemon}"
                adapter.ClearData()
                //Se hace la consulta para obtener los pokemons
                Fuel.get(url)
                    .responseObject<PokemonTypeResponse> { _, _, result ->
                        result.fold(success = { pokemonTypeResponse ->
                            val pokemonList = pokemonTypeResponse.pokemon
                            pokemonList.forEach { entry ->
                                val pokemon = entry.pokemon
                                //Se manda a llamar a los detalles del pokemon
                                fetchPokemonDetail(pokemon.url)
                                //Toast.makeText(this@MainActivity, "Seleccionaste: ${pokemon.name}", Toast.LENGTH_SHORT).show()
                            }
                        }, failure = { error ->
                            println("Error al obtener la lista de Pokémon: $error")
                        })
                    }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }


}
