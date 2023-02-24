package com.poke.client.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.poke.client.HomeFragment
import com.poke.client.R

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContentView(R.layout.layout_container)

		savedInstanceState ?: supportFragmentManager.commit {
			replace(R.id.fragment_container, HomeFragment.newInstance())
		}
	}
}