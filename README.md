#前言

![](https://upload-images.jianshu.io/upload_images/3520331-405f33e928ef4a4f.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

本文的目的有两个：
1. 大多数时候，自定义View并不会被用到，但一旦用到，通常都是很炫酷的效果。App的开发本身并不酷，让它们变酷的是设计师们的想象力与创造力。对于开发工程师而言，要做的，就是把他们的想象力与创造力变成现实。
2. Kotlin结合自定义View效果的实现，只要是 Java 能做的事情，Kotlin 都可以做，甚至还可以做得更好。

* 案例代码已上传Github，案例代码详情可戳—>[代码案例内容传送门](https://github.com/wangshuaialex/Kotlin-CustomView)
接下来就是本文的主题核心内容： 
***
#一、仪表盘
![图1-1 仪表盘效果的实现](https://upload-images.jianshu.io/upload_images/3520331-5eadb546b8a1625d.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##1、思路分析：

###①拆分为外层圆弧
1. 外层圆弧可通过**canvas.drawArc()**的形式进行实现，在本文中我通过Path首先添加了最外层的圆弧。
2. 当前的控件，我使其填充屏幕，对于圆弧首先需指定其所在的矩形范围，再指定圆弧的占有角度。
以下为关键实现内容：
~~~
                //先onDraw()绘制内容中，画外层的圆弧
                mPath.addArc(
                    width / 2 - mRadius,
                    height / 2 - mRadius,
                    width / 2 + mRadius,
                    height / 2 + mRadius,
                    (90 + mArcAngle / 2).toFloat(),
                    (360 - mArcAngle).toFloat()
                )
                canvas.drawPath(mPath, mPaint)
~~~

* 在Kotlin中，对自定义View的处理中，不需要再像Java的getWidth()、getHeight()的方式指定获取屏幕宽、高，直接通过width、height 获取即可。

 ~~~
    //扇形角度
    val mArcAngle = 120
~~~

对于扇形角度，我在这里定义为120°，也是下图所示起始角度，代码中对于**起始角度**设置为**90 + mArcAngle / 2**，圆弧扫过的角度为360°减去起始点的起始角度，**扫过角度**即为**360 - mArcAngle**。

![图1-1-1  外层圆弧绘制图解](https://upload-images.jianshu.io/upload_images/3520331-ca8813129435653b.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


###②拆分为中层矩形刻度尺


1. 先定义PathDashPathEffect变量：
~~~
    //路径改变器
    lateinit var mPathDashPathEffect: PathDashPathEffect
    //刻度线数量  
    val mDashCount: Int = 20
~~~
2. 然后**在初始化代码块中，先定义一个小矩形的宽和高**；
在这里我设置路径的宽高分别为3dp、8dp，并做了相关的适配：
**强调一点：CCW为counter-clockwise，逆时针方向绘制**
~~~
    init {
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 3f
        //对仪表盘添加每一个小刻度矩形
        mPath.addRect(
            0F,
            0F,
            DimensionUtils.dp2px(3f),
            DimensionUtils.dp2px(8f),
            Path.Direction.CCW
        )
    }
~~~

3. 然后在onSizeChanged()中，对于PathDashPathEffect进行实例化，PathDashPathEffect的四个参数中，在上面的官网贴图中我已经展示，简单做总结：

| 参数 |意义    | 
|:-------------:|:-------------:|
| shape      | 绘制路径 | 
| advance      | 绘制间距     |  
| phase | 绘制偏移     |  
| style | 绘制样式   |  

 根据原生Api要求，**需注意的是画笔样式需为STROKE、STROKE_AND_FILL两种样式，如果画笔设置为FILL的样式，PathDashPathEffect在路径上设置无效**。
~~~
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mPathDashPathEffect = PathDashPathEffect(
            mPath,
            PathMeasure(mPath, false).length - DimensionUtils.dp2px(3f) / mDashCount,
            0F,
            PathDashPathEffect.Style.ROTATE
        )
    }
~~~

4. 最后在onDraw()中对刻度条进行绘制；
刻度尺也是需要借助于当前绘制的圆弧。核心点在于**mPaint.setPathEffect(mPathDashPathEffect)**：

~~~
                //设置刻度条
                mPaint.setPathEffect(mPathDashPathEffect)
                //然后再画刻度条
                mPath.addArc(
                    width / 2 - mRadius,
                    height / 2 - mRadius,
                    width / 2 + mRadius,
                    height / 2 + mRadius,
                    (90 + mArcAngle / 2).toFloat(),
                    (360 - mArcAngle).toFloat()
                )
                canvas.drawPath(mPath, mPaint)
                mPaint.setPathEffect(null)
~~~

* 在这里，需要对**PathEffect**做详细介绍解释（Android Api节选）：
*PathEffect is the base class for objects in the Paint that affectthe geometry of a drawing primitive before it is transformed by thecanvas' matrix and drawn.*
译：PathEffect是Paint中的对象的基类，这些对象在**被canvas的矩阵变换和绘制之前影响了原始的绘制对象**。

* PathEffect有多个子类，在这里不做赘述，我所使用的是PathDashPathEffect，详情查看—>[官网文档传送门]
(https://developer.android.google.cn/reference/android/graphics/PathDashPathEffect.html)

![图1-2-1 PathDashPathEffect的官网说明](https://upload-images.jianshu.io/upload_images/3520331-905d832fdfc74593.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)




###③拆分为内层指向线
绘制内层的指向线就很简单了，在这里我指向了第5个刻度线，根据角度进行换算获取
~~~
                //画指示器
                canvas.drawLine(
                    (width / 2).toFloat(),
                    (height / 2).toFloat(),
                    width / 2 + Math.cos(Math.toRadians(getAngle(5))).toFloat() * DimensionUtils.dp2px(
                        60f
                    ),
                    height / 2 + Math.sin(Math.toRadians(getAngle(5))).toFloat() * DimensionUtils.dp2px(
                        60f
                    ), mPaint
                )
~~~

至此，刻度盘的效果实现，总体实现代码如下：
~~~
/**
 * @author Alex
 * @date 2019/9/3.
 * GitHub：https://github.com/wangshuaialex
 */
class DashBoardView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    //半径
    val mRadius = DimensionUtils.dp2px(100f)
    //椭圆外矩形
    val mRectF =
        RectF(width / 2 - mRadius, height / 2 - mRadius, width / 2 + mRadius, height / 2 + mRadius)
    //扇形角度
    val mArcAngle = 120
    //刻度条所依赖的线
    var mPath = Path()
    //刻度条
    lateinit var mPathDashPathEffect: PathDashPathEffect
    //刻度线数量
    val mDashCount: Int = 20


    init {
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 3f
        //对仪表盘添加每一个小刻度矩形
        mPath.addRect(
            0F,
            0F,
            DimensionUtils.dp2px(3f),
            DimensionUtils.dp2px(8f),
            Path.Direction.CCW
        )


    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mPathDashPathEffect = PathDashPathEffect(
            mPath,
            PathMeasure(mPath, false).length - DimensionUtils.dp2px(3f) / mDashCount,
            0F,
            PathDashPathEffect.Style.ROTATE
        )

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var resources = resources

        if (canvas != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //先画原始的圆
                mPath.addArc(
                    width / 2 - mRadius,
                    height / 2 - mRadius,
                    width / 2 + mRadius,
                    height / 2 + mRadius,
                    (90 + mArcAngle / 2).toFloat(),
                    (360 - mArcAngle).toFloat()
                )

                canvas.drawPath(mPath, mPaint)
                //设置刻度条
                mPaint.setPathEffect(mPathDashPathEffect)
                //然后再画刻度条
                mPath.addArc(
                    width / 2 - mRadius,
                    height / 2 - mRadius,
                    width / 2 + mRadius,
                    height / 2 + mRadius,
                    (90 + mArcAngle / 2).toFloat(),
                    (360 - mArcAngle).toFloat()
                )
                canvas.drawPath(mPath, mPaint)
                mPaint.setPathEffect(null)
                //画指示器
                canvas.drawLine(
                    (width / 2).toFloat(),
                    (height / 2).toFloat(),
                    width / 2 + Math.cos(Math.toRadians(getAngle(5))).toFloat() * DimensionUtils.dp2px(
                        60f
                    ),
                    height / 2 + Math.sin(Math.toRadians(getAngle(5))).toFloat() * DimensionUtils.dp2px(
                        60f
                    ), mPaint
                )
            }
        }
    }

    fun getAngle(pCurrentPosition: Int): Double {
        return (90 + mArcAngle / 2 + (360 - mArcAngle) / mDashCount * pCurrentPosition).toDouble()
    }
}
~~~
***
#二、折页效果

![图2-1 折页效果](https://upload-images.jianshu.io/upload_images/3520331-fef0b6cb249736ad.gif?imageMogr2/auto-orient/strip)

##1、思路分析
###①图片拆分为上部，只做图片切割
![图2-1-1 图片上下两部分的拆分](https://upload-images.jianshu.io/upload_images/3520331-bc43b238d64caee1.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
1.使用**canvas.clipRec()**系列方法对原始图片做切割，对上下两个部分分别做切割；
2.对于上半部分的图片，不做任何转换，以下为实现部分；
~~~
        //在onDraw()绘制方法中进行处理
        //上半部分
        if (canvas != null) {
            canvas.save()
            mCamera.save()
            canvas.clipRect(0f, 0f, mImageWidth, mImageWidth / 2)
            //图片绘制
            var avatarBitmap = BitmapConvertUtils.getAvatarBitmap(
                resources,
                DimensionUtils.dp2px(mImageWidth).toInt()
            )
            canvas.drawBitmap(avatarBitmap, 0f, 0f, mPaint)
            mCamera.restore()
            canvas.restore()
        }
~~~
###②图片拆分为下部，做图片切割与图像转换
1.对于下半部分的图片，做切割后，借助于**Camera**的Api对视角做变换，变换完毕后为了可以正常显示，对画布进行平移转换，以下为代码实现。
~~~
        //下半部分
        if (canvas != null) {
            canvas.save()
            mCamera.save()
            mCamera.rotateX(mBottomAngle)
            //画布右下平移，位置重新变换，坐标系位置改动
            canvas.translate(mImageWidth / 2, mImageWidth / 2)
            mCamera.applyToCanvas(canvas)
            canvas.clipRect(-mImageWidth / 2, 0F, mImageWidth / 2, mImageWidth / 2)
            canvas.translate(-mImageWidth / 2, -mImageWidth / 2)
            //图片绘制
            var avatarBitmap =
                BitmapConvertUtils.getAvatarBitmap(
                    resources,
                    DimensionUtils.dp2px(mImageWidth).toInt()
                )
            canvas.drawBitmap(avatarBitmap, 0f, 0f, mPaint)
            mCamera.restore()
            canvas.restore()
        }
~~~
* 在这里需要强调，对视图设置的角度，关键性参数：**mBottomAngle**，提供给用户设置；
* 在Kotlin中，我将mBottomAngle定义为成员变量，提供给调用者进行改变，在调用属性的**set()**方法时，进行**invalidate()**设置。
~~~
    //定义折页顶部动画属性
    var mBottomAngle: Float = 0f
        set(value) {
            field = value
            invalidate()
        }
        get() = field
~~~
###③做动画转场处理
最后，在Fragment的展示中，对于折页效果进行角度的改变处理，这里我使用属性动画进行展示，关键代码如下：
~~~
        //底部折页动画
        var bottomAngleAnimator = ObjectAnimator.ofFloat(ccv_convertView, "mBottomAngle", 120f)
        var animatorSet = AnimatorSet()
        animatorSet.startDelay = 1000
        animatorSet.duration = 800
        animatorSet
            .playSequentially(bottomAngleAnimator)
        animatorSet.start()
~~~
最后效果即实现，如下图，其中间的变化效果参考效果演示的Gif图：
![图2-1-3 折页图最终效果](https://upload-images.jianshu.io/upload_images/3520331-2810be63d8d5308f.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

至此，折页效果已实现，附上实现代码的类文件内容：
~~~
class CameraConvertView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var mPaint: Paint
    var mCamera: Camera
    //定义宽度动画属性
    var mImageWidth: Float = 600F
        //手动设置set方法
        set(value) {
            field = value
            invalidate()
        }
        get() = field
    //定义折页顶部动画属性
    var mTopAngle: Float = 0f
        set(value) {
            field = value
            invalidate()
        }
        get() = field
    //定义折页顶部动画属性
    var mBottomAngle: Float = 0f
        set(value) {
            field = value
            invalidate()
        }
        get() = field

    //定义画布的折叠角度动画属性
    var mCanvasAngle: Float = 0f
        set(value) {
            field = value
            invalidate()
        }
        get() = field

    init {
        mPaint = Paint()
        mCamera = Camera()
        //mCamera.setLocation(0f, 0f, -6 * resources.displayMetrics.scaledDensity)
        //mCamera.rotateX(45f)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //上半部分
        if (canvas != null) {
            canvas.save()
            mCamera.save()
            canvas.clipRect(0f, 0f, mImageWidth, mImageWidth / 2)
            //图片绘制
            var avatarBitmap = BitmapConvertUtils.getAvatarBitmap(
                resources,
                DimensionUtils.dp2px(mImageWidth).toInt()
            )
            canvas.drawBitmap(avatarBitmap, 0f, 0f, mPaint)
            mCamera.restore()
            canvas.restore()
        }


        //下半部分
        if (canvas != null) {
            canvas.save()
            mCamera.save()
            mCamera.rotateX(mBottomAngle)
            //画布右下平移，位置重新变换，坐标系位置改动
            canvas.translate(mImageWidth / 2, mImageWidth / 2)
            mCamera.applyToCanvas(canvas)
            canvas.clipRect(-mImageWidth / 2, 0F, mImageWidth / 2, mImageWidth / 2)
            canvas.translate(-mImageWidth / 2, -mImageWidth / 2)
            //图片绘制
            var avatarBitmap =
                BitmapConvertUtils.getAvatarBitmap(
                    resources,
                    DimensionUtils.dp2px(mImageWidth).toInt()
                )
            canvas.drawBitmap(avatarBitmap, 0f, 0f, mPaint)
            mCamera.restore()
            canvas.restore()
        }
    }
}
~~~
***
#三、结语
1. 案例代码已上传Github，案例代码详情可戳—>[代码案例内容传送门](https://github.com/wangshuaialex/Kotlin-CustomView)
2. 小米联合创始人黎万强《参与感》中提到：**互联网是注意力经济，一个品牌和事件的关注度，一定要有碰撞，有矛盾，有张力才起得来。所以，传播途中有不同声音不但正常，还可能是好事，在其中因势利导，抓主流就可以了。一个传播事件中，如果有七成是正面声音就很好了，剩下的三成负面的其实也无所谓。**
* 希望看完内容的你提出最真实的建议和意见，这是促进我更博的最大动力☺，希望能提供优质的内容与你分享！
