package com.example.watchapp.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.util.concurrent.TimeUnit
import kotlin.math.min

class RoundClockView: View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val paint = Paint()
    private var width = getWidth()
    private var height = getHeight()

    private var padding = min(width, height)/20f

    private var xCenter = width/2f
    private var yCenter = height/2f
    private var radius = min(width,height)/2f - padding

    private var borderWidth = radius/12f
    private var hourHandWidth = radius/20f
    private var minuteHandWidth = radius/30f
    private var secondHandWidth = radius/60f

    private var hourHandSize = radius - radius/2f
    private var handSize = radius - radius/4f
    private var handPadding = radius/4f
    private var textSize = radius - handSize

    private var calendar = Calendar.getInstance()
    private var timeZoneOffsetInHours = TimeUnit.HOURS.convert(calendar.timeZone.rawOffset.toLong(), TimeUnit.MILLISECONDS)

    private var backgroundColor = Color.WHITE
    private var borderColor = Color.BLACK
    private var hourHandColor = Color.BLACK
    private var minuteHandColor = Color.BLACK
    private var secondHandColor = Color.BLACK
    private var numeralsColor = Color.BLACK

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawBackground(canvas)
        drawBorder(canvas)
        drawNumerals(canvas)
        drawHands(canvas)

        postInvalidateDelayed(1000);
    }

    private fun drawBackground(canvas: Canvas){
        setPaintAttr(backgroundColor, Paint.Style.FILL, 0f)
        canvas.drawCircle(xCenter, yCenter, radius, paint)
    }
    private fun drawBorder(canvas: Canvas){
        setPaintAttr(borderColor, Paint.Style.STROKE, borderWidth)
        canvas.drawCircle(xCenter, yCenter, radius, paint)
    }

    private fun drawHands(canvas: Canvas){
        calendar = Calendar.getInstance()
        val currentOffset = TimeUnit.HOURS.convert(calendar.timeZone.rawOffset.toLong(), TimeUnit.MILLISECONDS)
        var offset = 0

        if (currentOffset != timeZoneOffsetInHours){

            offset = (currentOffset - timeZoneOffsetInHours).toInt()
        }

        Log.d("DebugTag", "curroffset= $currentOffset timezoneoffsetinhours = $timeZoneOffsetInHours offset = $offset")

        var hour = calendar.get(Calendar.HOUR_OF_DAY)-offset
        hour = if (hour > 12) hour-12 else hour

        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        drawHourHand(canvas, hour)
        drawMinuteHand(canvas, minute)
        drawSecondHand(canvas, second)

    }

    private fun drawHourHand(canvas: Canvas, hour: Int){
        setPaintAttr(hourHandColor, Paint.Style.STROKE, hourHandWidth)
        val angle = Math.PI * hour / 6f - Math.PI / 2f
        canvas.drawLine(
            (xCenter - Math.cos(angle)*handPadding).toFloat(),
            (yCenter - Math.sin(angle)*handPadding).toFloat(),
            (xCenter + Math.cos(angle)*hourHandSize).toFloat(),
            (yCenter + Math.sin(angle)*hourHandSize).toFloat(),
            paint
        )

    }

    private fun drawMinuteHand(canvas: Canvas, minute: Int){
        setPaintAttr(minuteHandColor, Paint.Style.STROKE, minuteHandWidth)

        val angle = Math.PI * minute / 30f - Math.PI / 2f
        canvas.drawLine(
            (xCenter - Math.cos(angle)*handPadding).toFloat(),
            (yCenter - Math.sin(angle)*handPadding).toFloat(),
            (xCenter + Math.cos(angle)*handSize).toFloat(),
            (yCenter + Math.sin(angle)*handSize).toFloat(),
            paint
        )
    }

    private fun drawSecondHand(canvas: Canvas, second: Int){
        setPaintAttr(secondHandColor, Paint.Style.STROKE, secondHandWidth)
        val angle = Math.PI * second / 30f - Math.PI / 2f
        canvas.drawLine(
            (xCenter - Math.cos(angle)*handPadding).toFloat(),
            (yCenter - Math.sin(angle)*handPadding).toFloat(),
            (xCenter + Math.cos(angle)*handSize).toFloat(),
            (yCenter + Math.sin(angle)*handSize).toFloat(),
            paint
        )
    }

    private fun drawNumerals(canvas: Canvas){
        setPaintAttr(numeralsColor, Paint.Style.FILL_AND_STROKE, 1f)
        paint.textSize = textSize
        paint.typeface = Typeface.SERIF
        val rect = Rect()
        for (num in (1..12)){
            val angle = Math.PI * num / 6f - Math.PI / 2f
            paint.getTextBounds(num.toString(), 0, num.toString().length, rect)
            val x = (xCenter+Math.cos(angle)*(radius-handPadding)-rect.width()/2f).toFloat()
            val y = (yCenter+Math.sin(angle)*(radius-handPadding)+rect.height()/2f).toFloat()

            canvas.drawText(num.toString(), x, y, paint)
        }
        for (dot in (1..60)){
            val angle = Math.PI * dot / 30f - Math.PI / 2f
            val x = (xCenter + Math.cos(angle)*(radius-padding)).toFloat()
            val y = (yCenter + Math.sin(angle)*(radius-padding)).toFloat()
            if(dot % 5 == 0 ){
                canvas.drawCircle(x, y, radius/75f, paint)
            } else{
                canvas.drawCircle(x, y, radius/100f, paint)
            }
        }
    }

    private fun setPaintAttr(color: Int, style: Paint.Style, strokeWidth: Float){
        paint.reset()
        paint.color = color
        paint.style = style
        paint.strokeWidth = strokeWidth
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredSize = 600
        val minimumSize = 250

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        var suggestedSize = min(widthSize, heightSize)

        if (suggestedSize < minimumSize){
            suggestedSize = minimumSize
        }

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        val width = when (widthMode){
            MeasureSpec.EXACTLY -> suggestedSize
            MeasureSpec.AT_MOST -> min(desiredSize, suggestedSize)
            else -> desiredSize
        }

        val height = when (heightMode){
            MeasureSpec.EXACTLY -> suggestedSize
            MeasureSpec.AT_MOST -> min(desiredSize, suggestedSize)
            else -> desiredSize
        }

        val size = min(width, height)
        val padding = size/20f

        this.width = width
        this.height = height

        this.xCenter = width/2f
        this.yCenter = height/2f
        this.radius = min(width,height)/2f - padding

        this.hourHandSize = radius - radius/2f
        this.handSize = radius - radius/4f

        this.handPadding = radius/4f

        this.padding = padding
        this.textSize = radius - handSize

        this.borderWidth = radius/12f
        this.hourHandWidth = radius/20f
        this.minuteHandWidth = radius/30f
        this.secondHandWidth = radius/60f

        setMeasuredDimension(size, size)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.width = w
        this.height = h
        this.xCenter = width/2f
        this.yCenter = height/2f
        this.radius = min(width,height)/2f - padding

        this.hourHandSize = radius - radius/2f
        this.handSize = radius - radius/4f

        this.padding = min(width,height)/20f
        this.handPadding = radius/4f

        this.textSize = radius - handSize
        this.borderWidth = radius/12f
        this.hourHandWidth = radius/20f
        this.minuteHandWidth = radius/30f
        this.secondHandWidth = radius/60f
    }

    fun setTimezone(timeZone: TimeZone){
        this.timeZoneOffsetInHours = TimeUnit.HOURS.convert(timeZone.rawOffset.toLong(), TimeUnit.MILLISECONDS)
    }

    fun setColors(
        backgroundColor: Int = Color.WHITE,
        borderColor: Int = Color.BLACK,
        hourHandColor: Int = Color.BLACK,
        minuteHandColor: Int = Color.BLACK,
        secondHandColor: Int = Color.BLACK,
        numeralsColor: Int = Color.BLACK
    ){
        this.backgroundColor = backgroundColor
        this.borderColor = borderColor
        this.hourHandColor = hourHandColor
        this.minuteHandColor = minuteHandColor
        this.secondHandColor = secondHandColor
        this.numeralsColor= numeralsColor
    }
}

