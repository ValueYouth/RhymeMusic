package priv.valueyouth.rhymemusic.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.widget.ImageView;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;

/**
 * 处理图片的工具类
 * Created by Idea on 2016/6/12.
 */
public class ImageUtil
{
    /**
     * 得到音乐专辑的封面图片
     * @param context 应用环境
     * @param audioId 歌曲的ID
     * @return 专辑封面图片
     */
    public static Bitmap getAlbumCover(Context context, int audioId)
    {
        String str = "content://media/external/audio/media/" + audioId + "/albumart";
        Uri uri = Uri.parse(str);
        ParcelFileDescriptor pfd = null;
        Bitmap bitmap;

        try
        {
            pfd = context.getContentResolver().openFileDescriptor(uri, "r");
        }
        catch (FileNotFoundException e)
        {
            return null;
        }

        if (pfd != null)
        {
            FileDescriptor fd = pfd.getFileDescriptor();
            bitmap = BitmapFactory.decodeFileDescriptor(fd);
            return bitmap;
        }
        else
        {
            return null;
        }
    }

    /**
     * 绘制圆角矩形封面方法
     * @param bitmap 源图片
     * @param roundPx 边角平滑度
     * @return 输出圆角矩形图片
     */
    public static Bitmap getRoundCornerBitmap(Bitmap bitmap, float roundPx)
    {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }


    //获得带倒影的图片方法
    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap)
    {
        final int reflectionGap = 4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap,
                0, height/2, width, height/2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height/2), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, height,width,height + reflectionGap,
                deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0,
                bitmap.getHeight(), 0, bitmapWithReflection.getHeight()
                + reflectionGap, 0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);

        return bitmapWithReflection;
    }

    /**
     * 将Drawable转化为Bitmap
     * @param drawable drawable
     * @return bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable)
    {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0,0,width,height);
        drawable.draw(canvas);

        return bitmap;
    }

}
