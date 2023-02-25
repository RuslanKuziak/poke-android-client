package poke.features.pokemons.list.extension

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState

fun CombinedLoadStates.isNotLoading(): Boolean {
    return prepend is LoadState.NotLoading && prepend.endOfPaginationReached
            || append is LoadState.NotLoading && append.endOfPaginationReached
}

fun CombinedLoadStates.isLoading(): Boolean {
    return prepend is LoadState.Loading || append is LoadState.Loading
}