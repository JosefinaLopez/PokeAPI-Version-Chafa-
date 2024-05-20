package com.example.pokedex_v1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pokedex_v1.databinding.ActivityDetallePokemonBinding
import com.squareup.picasso.Picasso

class DetallePokemon : AppCompatActivity() {
    private lateinit var binding: ActivityDetallePokemonBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetallePokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val extras = intent.extras
        if (extras != null) {
            val Img = extras.getString("Img")
            val Hei = extras.getString("Height")
            val Wei = extras.getString("Weight")
            val Tipo = extras.getString("Tipo")
            val Name = extras.getString("Nombre")




            Picasso.get().load(Img).into(binding.imgpika)
            binding.Height.text = Hei
            binding.Weight.text = Wei
            binding.Type.text = Tipo
            binding.TextNamePik.text = Name


            val hp = extras.getString("HP")
            val attack = extras.getString("Ataque")
            val defense = extras.getString("Defensa")
            val attackSpe = extras.getString("AtaqueEspecial")
            val defenseSpec = extras.getString("DefensaEspecial")

            binding.HP.text = "HP: ${hp}"
            binding.Ataque.text ="Attack: ${attack}"
            binding.Defensa.text = "Defense: ${defense}"
            binding.AtaqueEspecial.text = "Attack Special: ${attackSpe}"
            binding.DefensaEspecial.text = "Defense Special: ${defenseSpec}"

        }
    }

}
