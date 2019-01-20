package me.hiten.hardwarecanvasdemo

import android.graphics.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*
import me.hiten.hardwarecanvas.library.HardwareCanvasManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iv.setImageResource(R.drawable.dog)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun circleCrop(view: View?){
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.HARDWARE
        val bitmap = BitmapFactory.decodeResource(resources,R.drawable.dog,options)
        val size = Math.min(iv.width,iv.height)
        val hardwareCanvasManager = HardwareCanvasManager()
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val bitmapShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val matrix = Matrix()
        var scale: Float?
        var dx = 0f
        var dy = 0f
        val bitmapWidth = bitmap.width
        val bitmapHeight = bitmap.height
        val targetWidth = iv.width
        val targetHeight = iv.height

        if (bitmapWidth*targetHeight>targetWidth*bitmapHeight){
            scale = targetHeight/bitmapHeight.toFloat()
            dx = (targetWidth-bitmapWidth*scale)*0.5f
        }else{
            scale = targetWidth/bitmapWidth.toFloat()
            dy = (targetHeight-bitmapHeight*scale)*0.5f
        }
        matrix.setScale(scale,scale)
        matrix.postTranslate(dx,dy)
        bitmapShader.setLocalMatrix(matrix)
        paint.shader = bitmapShader
        val canvas = hardwareCanvasManager.createCanvas(size,size)
        canvas.drawCircle(size/2f,size/2f,size/2f,paint)
        val buildBitmap = hardwareCanvasManager.buildBitmap()

        iv.setImageBitmap(buildBitmap)
        hardwareCanvasManager.clean()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun drawCircle(view: View) {
        val hardwareCanvasManager = HardwareCanvasManager()
        val canvas = hardwareCanvasManager.createCanvas(iv.width,iv.height)
        val p = Paint()
        p.isAntiAlias = true
        p.color = Color.BLACK
        canvas.drawCircle(iv.width/2f,iv.height/2f,Math.min(iv.width/2f,iv.height/2f),p)
        val buildBitmap = hardwareCanvasManager.buildBitmap()
        iv.setImageBitmap(buildBitmap)
        hardwareCanvasManager.clean()
    }

}
