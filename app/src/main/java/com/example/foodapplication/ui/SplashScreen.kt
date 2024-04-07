package com.example.foodapplication.ui

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodapplication.R
import com.example.foodapplication.databinding.ActivitySplashScreenBinding
import com.example.foodapplication.ui.activity.MainActivity

class SplashScreen : AppCompatActivity() {
    lateinit var viewBinding : ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        viewBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)


        // Start the animation
        viewBinding.lottie.playAnimation()
        val textScaleAnimator = ObjectAnimator.ofFloat(viewBinding.foodExplore, "scaleX", 0f, 1f)
        textScaleAnimator.duration = 1000 // Adjust the duration as needed
        textScaleAnimator.start()
        // Handler to delay navigation after animation completion
        Handler(Looper.getMainLooper()).postDelayed({
            // Navigate to main activity
            val intent = Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(intent)
            // Finish current activity if needed
            finish()
        }, 3000) // Delay set to 3000 milliseconds (same as animation duration)
    }
}