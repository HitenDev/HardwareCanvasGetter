# HardwareCanvasGetter
搞这个代码主要目的是想获取一个HardwareCanvas（硬件画布），从而支持操作Hardware Bitmaps（硬件位图），或者绘制一些简单的图形；主要是解决软件画布无法操作硬件位图的异常`Software rendering doesn't support hardware bitmaps`

**何为硬件画布**

硬件画布相对于软件画布而言，主要是针对Android支持硬件加速以后，Canvas的操作步骤，可以直接交给GPU来渲染，反正是CPU来渲染，也就理解成软件画布；

**何为硬件位图**

硬件位图是Android8.0推出的一种Bitmap格式，通过设置Bitmap.Config.HARDWARE来创建，Hardware Bitmap与其他格式的Bitmap区别是：它的像素点是直接存储在显存上，这样做的好处是节省一半内存，但是也存在不兼容的问题，一般情况下Software Canvas是无法直接操作Hardware Bitmap，如果那软件画布不小心操作硬件位图，会报Software rendering doesn't support hardware bitmaps异常；

**软件画布和硬件画布的区别**

最常见的创建软件画布的情况：

- 通过Bitmap来接收画布内容，直接创建Canvas：
```
Bitmap bitmap = Bitmap.create();
Canvas canvas = new Canvas(bitmap);
```
- 通过自定义SurfaceView，从SurfaceHolder获取Canvas
```
 SurfaceHolder holder = getHolder();
 Canvas canvas=holder.lockCanvas();
```

Android3.0推出硬件加速，开启了硬件加速的Activity或者View，在`View.onDraw(Canvas canvas)`方法中入参`canvas`是硬件画布，相反是软件画布；

- 禁用硬件加速的情况
  - 通过修改AndroidManifest禁用硬件加速

  - 通过设置View.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
  
**软件画布异常的重新**

Glide剪切圆形图片

```
...
//获取接受画布内容的Bitmap
Bitmap result = pool.get(destMinEdge, destMinEdge, outConfig);
    result.setHasAlpha(true);
    //加锁
    BITMAP_DRAWABLE_LOCK.lock();
    try {
      //创建软件画布
      Canvas canvas = new Canvas(result);
      //画圆形
      canvas.drawCircle(radius, radius, radius, CIRCLE_CROP_SHAPE_PAINT);
      //画原图，通过画笔设置SRC_IN属性
      canvas.drawBitmap(toTransform, null, destRect, CIRCLE_CROP_BITMAP_PAINT);
      clear(canvas);
    } finally {
      BITMAP_DRAWABLE_LOCK.unlock();
    }
 ...
```
一旦toTransform是硬件位图，`canvas.drawBitmap(xxx)`这行代码就会报错；

**使用该项目获取硬件画布的流程：**
```
val hardwareCanvasManager = HardwareCanvasManager()
try {
    val canvas = hardwareCanvasManager.createCanvas(size, size)
    //画圆形
    canvas.drawCircle(radius, radius, radius, CIRCLE_CROP_SHAPE_PAINT);
    //画原图，通过画笔设置SRC_IN属性
    canvas.drawBitmap(inBitmap, null, destRect, CIRCLE_CROP_BITMAP_PAINT);
    val buildBitmap = hardwareCanvasManager.buildBitmap()
    iv.setImageBitmap(buildBitmap)
} finally {
    hardwareCanvasManager.clean()
}
```
通过`HardwareCanvasManager`获得的Canvas支持绘制硬件位图；

**兼容问题**
硬件加速存在的绘制问题，硬件画布同样存在



