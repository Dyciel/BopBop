package cl.dycier.bopbop

import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.media.PlaybackParams
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var media: MediaPlayer
    private var playbackParams: PlaybackParams? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        media = MediaPlayer.create(this, R.raw.bopbopsong)
        playbackParams = media.playbackParams

        media.start()


        val buttonOKAbout = findViewById<Button>(R.id.aboutButton)

        buttonOKAbout.setOnClickListener {
            val intentAbout = Intent(this, AboutActivity::class.java)
            startActivity(intentAbout)
            }

        val buttonSettings = findViewById<Button>(R.id.settingbutton)

        buttonSettings.setOnClickListener {
            val intentSettings = Intent(this, SettingsActivity::class.java)
            startActivity(intentSettings)
        }

        val buttonGame = findViewById<Button>(R.id.playbutton)

        buttonGame.setOnClickListener {
            val intentGameScreen = Intent(this, GameScreen::class.java)
            startActivity(intentGameScreen)
        }

        val buttonSpeed = findViewById<Button>(R.id.speedMusic)

        buttonSpeed.setOnClickListener {
            playbackParams?.setSpeed(playbackParams!!.speed + 0.5f)
            media.playbackParams = playbackParams!!
        }

    }

    override fun onPause() {
        super.onPause()
        if(media.isPlaying){
            media.pause()
        }
    }
    private fun changePlaybackSpeed(speedChange: Float){
        playbackParams?.setSpeed(playbackParams!!.speed + speedChange)
        media.playbackParams = playbackParams!!
    }


}