package cl.dycier.bopbop

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.media.PlaybackParams
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class GameScreen : AppCompatActivity(), SensorEventListener {

    private lateinit var mediaVictory: MediaPlayer
    private lateinit var mediaLose: MediaPlayer
    private var playbackParams: PlaybackParams? = null
    private var playbackParams2: PlaybackParams? = null

    private var sensorManager: SensorManager? = null
    private var accelerometro: Sensor? = null

    private lateinit var textInstrucion: TextView
    private val handler = Handler(Looper.getMainLooper())

    private lateinit var gestureManager: GestureDetector

    private lateinit var textPuntaje: TextView
    private var puntaje = 0

    val randomValues = List(10) { Random.nextInt(0, 3) }
    private val instructions = listOf(
        "Desliza por la pantalla.",
        "Agita el dispositivo.",
        "Realiza dos toques."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)

        mediaVictory = MediaPlayer.create(this, R.raw.victorysound)
        playbackParams = mediaVictory.playbackParams

        mediaLose = MediaPlayer.create(this, R.raw.losesound)
        playbackParams2 = mediaLose.playbackParams

        gestureManager = GestureDetector(this, GestoEscucha())

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometro = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        textInstrucion = findViewById(R.id.instruccion)
        textPuntaje = findViewById(R.id.puntuacion)
        textPuntaje.text = puntaje.toString()
        showRandomInstruction()


        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureManager.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this, accelerometro, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }


    inner class GestoEscucha : SimpleOnGestureListener() {
        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {

            if(textInstrucion.text == "Desliza por la pantalla.")
            {
                textInstrucion.text = "Bien Hecho!"
                puntaje = puntaje + 1
                textPuntaje.text = puntaje.toString()
                mediaVictory.start()
                scheduleRandomInstruction()
            }else if(textInstrucion.text != "Bien Hecho!")
            {
                textInstrucion.text = "Fallaste"
                mediaLose.start()
            }
            return true
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            if(textInstrucion.text == "Realiza dos toques.")
            {
                textInstrucion.text = "Bien Hecho!"
                puntaje = puntaje + 1
                textPuntaje.text = puntaje.toString()
                mediaVictory.start()
                scheduleRandomInstruction()
            }else if(textInstrucion.text != "Bien Hecho!")
            {
                textInstrucion.text = "Fallaste"
                mediaLose.start()

            }
            return true
        }
    }

    private fun showToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
    private fun showToast2(mensajeDC : String){
        Toast.makeText(this, mensajeDC, Toast.LENGTH_SHORT).show()
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            val acceleration = Math.sqrt(x * x + y * y + z * z.toDouble()).toFloat()

            // You can adjust the acceleration threshold based on your needs
            val threshold = 50.0f

            if(acceleration > threshold)
            {
                if(textInstrucion.text == "Agita el dispositivo.")
                {
                    textInstrucion.text = "Bien Hecho!"
                    puntaje = puntaje + 1
                    textPuntaje.text = puntaje.toString()
                    mediaVictory.start()
                    scheduleRandomInstruction()
                }else if(textInstrucion.text != "Bien Hecho!")
                {
                    textInstrucion.text = "Fallaste"
                    mediaLose.start()


                }
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    private fun showRandomInstruction() {
        val randomIndex = (0 until instructions.size).random() // Seleccionar índice aleatorio
        val randomInstruction = instructions[randomIndex] // Obtener la instrucción aleatoria
        textInstrucion.text = randomInstruction // Mostrar la instrucción en el TextView
    }

    private fun scheduleRandomInstruction() {
        handler.postDelayed({
            showRandomInstruction() // Realizar la función después de 3 segundos
        }, 3000) // Retraso de 3 segundos (3000 milisegundos)
    }
}