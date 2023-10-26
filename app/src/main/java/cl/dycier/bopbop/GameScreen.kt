package cl.dycier.bopbop

import android.content.Intent
import android.media.MediaPlayer
import android.media.PlaybackParams
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.widget.Button
import android.widget.Toast

class GameScreen : AppCompatActivity() {

    private lateinit var mediaVictory: MediaPlayer
    private lateinit var mediaLose: MediaPlayer
    private var playbackParams: PlaybackParams? = null
    private var playbackParams2: PlaybackParams? = null

    private lateinit var gestureManager: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)

        mediaVictory = MediaPlayer.create(this, R.raw.victorysound)
        playbackParams = mediaVictory.playbackParams

        mediaLose = MediaPlayer.create(this, R.raw.losesound)
        playbackParams2 = mediaLose.playbackParams

        gestureManager = GestureDetector(this, GestoEscucha())


        val buttonVictory = findViewById<Button>(R.id.victoryButton)

        buttonVictory.setOnClickListener {
            mediaVictory.start()
        }
        val buttonLose = findViewById<Button>(R.id.loseButton)

        buttonLose.setOnClickListener {
            mediaLose.start()
        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureManager.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    inner class GestoEscucha : SimpleOnGestureListener() {
        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            showToast("Deslizado Exitoso")
            return true
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            showToast2("Doble Click")
            return true
        }
    }

    private fun showToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
    private fun showToast2(mensajeDC : String){
        Toast.makeText(this, mensajeDC, Toast.LENGTH_SHORT).show()
    }
}