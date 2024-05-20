package com.example.pokedex_v1

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class PokemonAdapter(
    private val pokemonList: MutableList<PokemonDetail>,
    private val context: Context,// Añadir el contexto
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.TextName)
        private val nameType: TextView = itemView.findViewById(R.id.TextTipo)
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewPokemon)
        private val Card: CardView = itemView.findViewById(R.id.card)

        fun bind(pokemon: PokemonDetail, context: Context) {
            if(pokemon.name.length > 7)
            {
                nameTextView.text = "${pokemon.name.substring(0,7)}..."

            }
            else{
                nameTextView.text = pokemon.name
            }
            nameType.text = pokemon.types.joinToString(", ") { it.type.name }
            Picasso.get().load(pokemon.sprites.front_default).into(imageView)
            //Informacion de la  pantalla detalle
            Card.setOnClickListener(){
                val intent = Intent(context,DetallePokemon::class.java)
                intent.putExtra("Nombre",pokemon.name)
                intent.putExtra("Height",pokemon.height.toString())
                intent.putExtra("Weight",pokemon.weight.toString())
                intent.putExtra("Tipo",pokemon.types[0].type.name)
                intent.putExtra("Img",pokemon.sprites.front_default)

                intent.putExtra("HP",pokemon.stats[0].base_stat.toString())
                intent.putExtra("Ataque",pokemon.stats[1].base_stat.toString())
                intent.putExtra("Defensa",pokemon.stats[2].base_stat.toString())
                intent.putExtra("AtaqueEspecial",pokemon.stats[3].base_stat.toString())
                intent.putExtra("DefensaEspecial",pokemon.stats[4].base_stat.toString())
                intent.putExtra("Speen",pokemon.stats[5].base_stat)

                context.startActivity(intent)

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pokemon, parent, false)
        return PokemonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.bind(pokemon,context) // Pasar el clickListener al ViewHolder
    }

    fun addPokemon(pokemon: PokemonDetail) {
        pokemonList.add(pokemon)
        notifyDataSetChanged()
    }
    fun ClearData()
    {
        pokemonList.clear()
    }
}