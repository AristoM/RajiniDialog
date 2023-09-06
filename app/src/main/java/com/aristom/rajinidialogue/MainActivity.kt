package com.aristom.rajinidialogue

import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Vibrator
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import java.util.Random

class MainActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var playButton: Button
    private lateinit var stopButton: Button
    private lateinit var root: LinearLayout
    private val musicFiles = arrayOf(
        R.raw.song1,
        R.raw.song2,
        R.raw.song3,
        R.raw.song4,
        R.raw.song5,
        R.raw.song6,
        R.raw.song7
    ) // Add your music file references here
    private var currentMusicIndex = -1
    private var isReleased = false
    private lateinit var vibrator: Vibrator
    private val backgroundImages = listOf(
        R.drawable.bg_1,
        R.drawable.bg_2,
        R.drawable.bg_3,
        R.drawable.bg_4,
        R.drawable.bg_5,
        R.drawable.bg_6,
        R.drawable.bg_7
    ) // Add your image resource IDs here
    private var currentImageIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        root = findViewById(R.id.root_layout)
        playButton = findViewById(R.id.playButton)
        stopButton = findViewById(R.id.stopButton)
        mediaPlayer = MediaPlayer()
        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator // Initialize Vibrator


        playButton.setOnClickListener { playRandomMusic() }
        stopButton.setOnClickListener { stopMusic() }
    }

    private fun stopMusic() {
        if (mediaPlayer != null && !isReleased && mediaPlayer!!.isPlaying) {
            mediaPlayer!!.stop()
            mediaPlayer!!.reset()
        }
    }

    private fun changeBackground() {
        if (currentImageIndex < backgroundImages.size) {
            val drawable: Drawable? =
                resources.getDrawable(backgroundImages[currentImageIndex], theme)
            root.background = drawable
            currentImageIndex++

            // Reset the index to loop through images
            if (currentImageIndex >= backgroundImages.size) {
                currentImageIndex = 0
            }
        }
    }

    private fun playRandomMusic() {
        vibrator.vibrate(50) // Vibrate for 50 milliseconds
        if (mediaPlayer != null && !isReleased && mediaPlayer!!.isPlaying) {
            mediaPlayer!!.stop()
            mediaPlayer!!.reset()
        }

        currentMusicIndex++
        if (currentMusicIndex >= musicFiles.size) {
            currentMusicIndex = 0 // Loop back to the first song
        }

        val nextMusicId = musicFiles[currentMusicIndex]

        mediaPlayer = MediaPlayer.create(this, nextMusicId)
        mediaPlayer!!.start()
        isReleased = false

        changeBackground()
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer!!.release()
        mediaPlayer = null
        isReleased = true;
    }

}

