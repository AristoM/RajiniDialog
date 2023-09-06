package com.aristom.rajinidialogue

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Vibrator
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.util.Random

class MainActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var playButton: Button
    private val musicFiles = arrayOf(R.raw.song1, R.raw.song2, R.raw.song3,
        R.raw.song4, R.raw.song5, R.raw.song6, R.raw.song7) // Add your music file references here
    private var currentMusicIndex = -1
    private var isReleased = false
    private lateinit var vibrator: Vibrator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        playButton = findViewById(R.id.playButton)
        mediaPlayer = MediaPlayer()
        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator // Initialize Vibrator


        playButton.setOnClickListener { playRandomMusic() }
    }

    private fun playRandomMusic() {
        vibrator.vibrate(50) // Vibrate for 50 milliseconds
        if (mediaPlayer != null && !isReleased && mediaPlayer!!.isPlaying) {
            mediaPlayer!!.stop()
            mediaPlayer!!.reset()
        }

        val randomIndex = Random().nextInt(musicFiles.size)
        if (randomIndex != currentMusicIndex) {
            currentMusicIndex = randomIndex

            val randomMusicId = musicFiles[currentMusicIndex]

            mediaPlayer = MediaPlayer.create(this, randomMusicId)
            mediaPlayer!!.start()
            isReleased = false
        }
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer!!.release()
        mediaPlayer = null
        isReleased = true;
    }

}

