package me.hiten.hardwarecanvas.library;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;

class HardwareCanvasApiP extends HardwareCanvasApi{

    private Picture mPicture;

    private Canvas mCanvas;

    HardwareCanvasApiP(int width, int height) {
        super(width,height);
    }

    @Override
    public Canvas getCanvas() {
        mPicture = new Picture();
        mCanvas = mPicture.beginRecording(mWidth, mHeight);
        return mCanvas;
    }

    @Override
    public Bitmap buildBitmap() {
        if (mPicture!=null) {
            mPicture.endRecording();
            return Bitmap.createBitmap(mPicture);
        }
        return null;
    }

    @Override
    protected void clean() {
        mPicture = null;
        mCanvas = null;
    }
}
