package com.bignerdranch.android.beatbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.beatbox.databinding.ActivityMainBinding
import com.bignerdranch.android.beatbox.databinding.ListItemSoundBinding

class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

	private val beatBoxViewModel: BeatBoxViewModel by lazy {
		ViewModelProvider(
			this, ViewModelProvider.NewInstanceFactory()).get(BeatBoxViewModel::class.java
		)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		if (beatBoxViewModel.beatBox == null)
			beatBoxViewModel.beatBox = BeatBox(assets)

		val binding: ActivityMainBinding = DataBindingUtil.setContentView(
			this, R.layout.activity_main
		)

		binding.speedBar.setOnSeekBarChangeListener(this)

		binding.recyclerView.apply {
			layoutManager = GridLayoutManager(context, 3)
			adapter = SoundAdapter(beatBoxViewModel.beatBox!!.sounds)
		}

	}

	override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
		beatBoxViewModel.beatBox?.speed = progress
	}

	override fun onStartTrackingTouch(seekBar: SeekBar?) {}
	override fun onStopTrackingTouch(seekBar: SeekBar?) {}

	private inner class SoundHolder(private val binding: ListItemSoundBinding):
		RecyclerView.ViewHolder(binding.root) {

		init {
			binding.apply {
				viewModel = SoundViewModel(beatBoxViewModel.beatBox!!)
			}
		}

		fun bind(sound: Sound) {
			binding.apply {
				viewModel?.sound = sound
				executePendingBindings()
			}
		}
	}

	private inner class SoundAdapter(private val sounds: List<Sound>):
		RecyclerView.Adapter<SoundHolder>() {

		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
			val binding = DataBindingUtil.inflate<ListItemSoundBinding>(
				layoutInflater, R.layout.list_item_sound, parent, false
			)
			return SoundHolder(binding)
		}

		override fun onBindViewHolder(holder: SoundHolder, position: Int) {
			holder.bind(sounds[position])
		}

		override fun getItemCount() = sounds.size
	}
}
