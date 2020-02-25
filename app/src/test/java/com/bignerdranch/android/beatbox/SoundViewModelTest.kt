package com.bignerdranch.android.beatbox

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class SoundViewModelTest {

	private lateinit var sound: Sound
	private lateinit var subject: SoundViewModel
	private lateinit var beatBox: BeatBox

	@Before
	fun setUp() {
		sound = Sound("assetPath")
		subject = SoundViewModel()
		subject.sound = sound
		beatBox = mock(BeatBox::class.java)
	}

	@Test
	fun exposesSoundNameTitle() {
		assertThat(subject.title, `is`(sound.name))
	}

	@Test
	fun callsBeatBoxPlayOnButtonClicked() {
		subject.onButtonClicked()
		verify(beatBox).play(sound)
	}

}