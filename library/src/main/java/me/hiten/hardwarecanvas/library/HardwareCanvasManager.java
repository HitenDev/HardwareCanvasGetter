package me.hiten.hardwarecanvas.library;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.O)
public class HardwareCanvasManager {

    private HardwareCanvasApi hardwareCanvasApi;


    public  Canvas createCanvas(int width, int height){
        if (hardwareCanvasApi==null){
            createHardwareCanvasApi(width,height);
        }
        return hardwareCanvasApi.getCanvas();
    }

    private void createHardwareCanvasApi(int width, int height) {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.P){
            hardwareCanvasApi = new HardwareCanvasApiP(width,height);
        } else if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            hardwareCanvasApi = new HardwareCanvasApiO(width,height);
        }else {
            throw new RuntimeException();
        }
    }

    public  Bitmap buildBitmap(){
        return hardwareCanvasApi.buildBitmap();
    }

    public void clean(){
        if (hardwareCanvasApi!=null) {
            hardwareCanvasApi.clean();
            hardwareCanvasApi = null;
        }
    }

}
