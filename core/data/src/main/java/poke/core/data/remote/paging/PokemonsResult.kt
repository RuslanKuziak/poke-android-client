package poke.core.data.remote.paging

import poke.core.data.remote.model.PokeItemResponse

data class PokemonsResult(val total: Int, val item: PokeItemResponse)