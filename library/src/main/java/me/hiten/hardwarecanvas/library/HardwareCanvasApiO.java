package me.hiten.hardwarecanvas.library;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.DisplayListCanvas;
import android.view.RenderNode;
import android.view.ThreadedRenderer;


class HardwareCanvasApiO extends HardwareCanvasApi {


    private RenderNode mNode;

    private DisplayListCanvas mCanvas;

    HardwareCanvasApiO(int width, int height) {
        super(width, height);
    }

    @Override
    public Canvas getCanvas() {
        try {
            RenderNode renderNode = RenderNode.create("HardwareCanvasApiO", null);
            renderNode.setLeftTopRightBottom(0, 0, mWidth, mHeight);
            renderNode.setClipToBounds(true);
            DisplayListCanvas canvas = renderNode.start(mWidth, mHeight);
            if (canvas != null) {
                mNode = renderNode;
                mCanvas = canvas;
                return canvas;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Bitmap buildBitmap() {
        if (mNode != null && mCanvas != null) {
            try {
                mNode.end(mCanvas);
                Bitmap bitmap = ThreadedRenderer.createHardwareBitmap(mNode, mWidth, mHeight);
                if (bitmap != null) {
                    return bitmap;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void clean() {
        mNode = null;
        mCanvas = null;
    }
}
