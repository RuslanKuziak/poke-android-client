package com.poke.client.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.poke.client.R
import dagger.hilt.android.AndroidEntryPoint
import poke.features.pokemondetails.PokemonDetailsFragment
import poke.features.pokemons.list.PokemonListFragment

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_home) {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		savedInstanceState ?: childFragmentManager.commit {
			replace(
				R.id.fragment_container,
				PokemonListFragment.newInstance()
			)
		}

		subscribeToResults()
	}

	private fun subscribeToResults() {
		childFragmentManager.setFragmentResultListener(
			PokemonListFragment.SELECTED_ID_KEY,
			this
		) { requestKey, result ->
			when (requestKey) {
				PokemonListFragment.SELECTED_ID_KEY -> {
					openPokemonDetails(result.getString(PokemonListFragment.SELECTED_ID_KEY, ""))
				}
			}
		}
	}

	private fun openPokemonDetails(value: String) {
		parentFragmentManager.commit {
			setCustomAnimations(
				poke.core.ui.R.anim.slide_in,
				poke.core.ui.R.anim.fade_out,
				poke.core.ui.R.anim.fade_in,
				poke.core.ui.R.anim.slide_out
			)
			replace(id, PokemonDetailsFragment.newInstance(id = value))
			addToBackStack("")
		}
	}

	companion object {
		fun newInstance() = MainFragment()
	}

}