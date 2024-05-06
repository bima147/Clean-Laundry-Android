package com.bimaprakoso.cleanlaundrybootcamp

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bimaprakoso.cleanlaundrybootcamp.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 2100 // 2 seconds delay
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val colorStart = ContextCompat.getColor(this, R.color.white)
        val colorEnd = ContextCompat.getColor(this, R.color.primary)

        val colorEnd2 = ContextCompat.getColor(this, R.color.secondary)

        val colorAnimator1 = ObjectAnimator.ofObject(
            binding.textViewSplash1,
            "textColor",
            ArgbEvaluator(),
            colorStart,
            colorEnd
        )

        val colorAnimator2 = ObjectAnimator.ofObject(
            binding.textViewSplash2,
            "textColor",
            ArgbEvaluator(),
            colorStart,
            colorEnd2
        )

        val colorAnimator3 = ObjectAnimator.ofObject(
            ArgbEvaluator(),
            colorStart,
            colorEnd2
        )

        colorAnimator3.addUpdateListener { animator ->
            val color = animator.animatedValue as Int
            binding.imageViewLogo.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        }

        colorAnimator1.duration = 2000 // duration of the color transition in milliseconds
        colorAnimator1.interpolator = LinearInterpolator()
        colorAnimator1.start()

        colorAnimator2.duration = 2000 // duration of the color transition in milliseconds
        colorAnimator2.interpolator = LinearInterpolator()
        colorAnimator2.start()

//        colorAnimator3.duration = 2000 // duration of the color transition in milliseconds
//        colorAnimator3.interpolator = LinearInterpolator()
//        colorAnimator3.start()

        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish() // Close this activity
        }, SPLASH_DELAY)
    }
}