package com.example.pokedex_v1

data class Pokemon(
    val name: String,
    val url: String
)

data class PokemonDetail(
    val id: Int,
    val name: String,
    val base_experience: Int,
    val height: Int,
    val is_default: Boolean,
    val order: Int,
    val weight: Int,
    val abilities: List<AbilitySlot>,
    val forms: List<Form>,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<TypeSlot>
)

data class AbilitySlot(
    val ability: Ability,
    val is_hidden: Boolean,
    val slot: Int
)

data class Ability(
    val name: String,
    val url: String
)

data class Form(
    val name: String,
    val url: String
)

data class Sprites(
    val front_default: String
)

data class Stat(
    val base_stat: Int,
    val effort: Int,
    val stat: StatDetail
)

data class StatDetail(
    val name: String,
    val url: String
)

data class TypeSlot(
    val slot: Int,
    val type: Type
)

data class Type(
    val name: String,
    val url: String
)

data class Typess(
    val results: List<XD>
)
data class XD(val name:String, val url:String)

data class PokemonTypeResponse(
    val pokemon: List<PokemonEntry>
)

data class PokemonEntry(
    val pokemon: Pokemon
)


data class PokemonListResponse(val results: List<Pokemon>)

//Hacerlo Mutable
var listTypes: MutableList<String> = mutableListOf()
