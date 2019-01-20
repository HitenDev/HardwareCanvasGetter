package me.hiten.hardwarecanvas.library;

import android.graphics.Bitmap;
import android.graphics.Canvas;

abstract class HardwareCanvasApi {

    protected int mWidth;
    protected int mHeight;

    protected HardwareCanvasApi(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
    }

    protected abstract Canvas getCanvas();

    protected abstract Bitmap buildBitmap();

    protected abstract void clean();
}
