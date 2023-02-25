package com.poke.client.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.poke.client.R
import dagger.hilt.android.AndroidEntryPoint
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
	}

	companion object {
		fun newInstance() = MainFragment()
	}

}