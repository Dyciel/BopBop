package cl.dycier.bopbop

import android.content.Intent
import android.media.MediaPlayer
import android.media.PlaybackParams
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class GameScreen : AppCompatActivity() {

    private lateinit var mediaVictory: MediaPlayer
    private lateinit var mediaLose: MediaPlayer
    private var playbackParams: PlaybackParams? = null
    private var playbackParams2: PlaybackParams? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)

        mediaVictory = MediaPlayer.create(this, R.raw.victorysound)
        playbackParams = mediaVictory.playbackParams

        mediaLose = MediaPlayer.create(this, R.raw.losesound)
        playbackParams2 = mediaLose.playbackParams


        val buttonVictory = findViewById<Button>(R.id.victoryButton)

        buttonVictory.setOnClickListener {
            mediaVictory.start()
        }
        val buttonLose = findViewById<Button>(R.id.loseButton)

        buttonLose.setOnClickListener {
            mediaLose.start()
        }



    }
}