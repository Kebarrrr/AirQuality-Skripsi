package com.skripsi.airquality.ui.custom

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.skripsi.airquality.R

class AQIMeterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val needlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var aqiValue: Int = 0
    private var animatedAqiValue: Float = 0f

    private val colors = intArrayOf(
        ContextCompat.getColor(context, R.color.aqi_good),
        ContextCompat.getColor(context, R.color.aqi_moderate),
        ContextCompat.getColor(context, R.color.aqi_unhealthy_sg),
        ContextCompat.getColor(context, R.color.aqi_unhealthy),
        ContextCompat.getColor(context, R.color.aqi_very_unhealthy),
        ContextCompat.getColor(context, R.color.aqi_hazardous)
    )

    private val aqiLabels = arrayOf("Baik", "Sedang", "Tidak Sehat", "Berbahaya")
    private val aqiRanges = arrayOf(50, 100, 200, 300, 400, 500)

    init {
        textPaint.color = Color.WHITE
        textPaint.textSize = 50f
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)

        needlePaint.color = Color.RED
        needlePaint.strokeWidth = 10f
        needlePaint.strokeCap = Paint.Cap.ROUND

        shadowPaint.setShadowLayer(10f, 0f, 0f, Color.BLACK)
        setLayerType(LAYER_TYPE_SOFTWARE, shadowPaint)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width.toFloat()
        val height = height.toFloat()
        val centerX = width / 2
        val centerY = height * 0.8f
        val radius = Math.min(centerX, centerY) - 40

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 30f
        paint.shader = SweepGradient(centerX, centerY, colors, null)
        val arcRect = RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
        canvas.drawArc(arcRect, 180f, 180f, false, paint)

        for (i in aqiRanges.indices) {
            val angle = Math.toRadians(180.0 + (aqiRanges[i] / 500.0) * 180.0)
            val labelX = centerX + (radius - 40) * Math.cos(angle).toFloat()
            val labelY = centerY + (radius - 40) * Math.sin(angle).toFloat()
            canvas.drawText(aqiLabels.getOrElse(i) { "" }, labelX, labelY, textPaint)
        }

        val angle = 180 + (animatedAqiValue / 500f) * 180f
        val needleLength = radius - 30
        val needleX = centerX + (needleLength * Math.cos(Math.toRadians(angle.toDouble()))).toFloat()
        val needleY = centerY + (needleLength * Math.sin(Math.toRadians(angle.toDouble()))).toFloat()

        canvas.drawLine(centerX, centerY, needleX, needleY, needlePaint)

        paint.style = Paint.Style.FILL
        paint.color = Color.DKGRAY
        canvas.drawCircle(centerX, centerY, 15f, paint)

        canvas.drawText("AQI: $aqiValue", centerX, centerY - radius - 30, textPaint)
    }

    fun updateAQI(value: Int) {
        aqiValue = value
        val animator = ValueAnimator.ofFloat(animatedAqiValue, value.toFloat())
        animator.duration = 1000
        animator.addUpdateListener {
            animatedAqiValue = it.animatedValue as Float
            invalidate()
        }
        animator.start()
    }
}
