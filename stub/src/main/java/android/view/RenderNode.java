package android.view;

public class RenderNode {

    public static RenderNode create(String name, View owningView){
        return null;
    }

    public boolean setLeftTopRightBottom(int left, int top, int right, int bottom) {
        return false;
    }

    public boolean setClipToBounds(boolean clipToBounds) {
        return false;
    }

    public DisplayListCanvas start(int width, int height) {
        return null;
    }

    public void end(DisplayListCanvas canvas) {
    }
}
