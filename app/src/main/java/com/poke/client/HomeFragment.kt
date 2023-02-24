package com.poke.client

import android.os.Bundle
import androidx.fragment.app.Fragment

class HomeFragment : Fragment(R.layout.fragment_home) {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)




	}

	companion object {
		fun newInstance() = HomeFragment()
	}

}