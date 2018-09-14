package com.example.administrator.picorvideotoasciidemo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class CommonUtil {

    /**
     * 產生特殊文字
     */
    public static Bitmap createAsciiPic(Bitmap image, Context context) {
        final String base = "#8XOHLTI)i=+;:,.";                                                     // 字符串由复杂到简单
        StringBuilder text = new StringBuilder();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int width0 = image.getWidth();
        int height0 = image.getHeight();
        int width1, height1;
        int scale = 7;
        if (width0 <= width / scale) {
            width1 = width0;
            height1 = height0;
        } else {
            width1 = width / scale;
            height1 = width1 * height0 / width0;
        }
        image = scale(image, width1, height1);                                                        // 读取图片

        /** 输出到指定文件中 */
        for (int y = 0; y < image.getHeight(); y += 2) {
            for (int x = 0; x < image.getWidth(); x++) {
                final int pixel = image.getPixel(x, y);
                final int r = (pixel & 0xff0000) >> 16, g = (pixel & 0xff00) >> 8, b = pixel & 0xff;
                final float gray = 0.299f * r + 0.578f * g + 0.114f * b;
                final int index = Math.round(gray * (base.length() + 1) / 255);
                String s = index >= base.length() ? " " : String.valueOf(base.charAt(index));
                text.append(s);
            }
            text.append("\n");
        }
        return textAsBitmap(text, context);
    }

    /**
     * 將文字畫進bitmap
     */
    public static Bitmap textAsBitmap(StringBuilder text, Context context) {
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(0xff000000);
        textPaint.setAntiAlias(true);
        textPaint.setTypeface(Typeface.MONOSPACE);
        textPaint.setTextSize(12);

        /** 得到屏幕尺寸 */
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);     // 獲取WindowManager来管理窗口
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        StaticLayout layout = new StaticLayout(text, textPaint, width,                              // StaticLayout是android中处理文字换行的一个工具类, 已经实现了文本绘制换行处理
                Layout.Alignment.ALIGN_CENTER, 1f, 0.0f, true);
        Bitmap bitmap = Bitmap.createBitmap(layout.getWidth() + 20,
                layout.getHeight() + 20, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.translate(10, 10);
        canvas.drawColor(Color.WHITE);
        layout.draw(canvas);
        return bitmap;
    }

    /**
     * 將Bitmap縮放
     */
    public static Bitmap scale(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap ret = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        return ret;
    }

    /**
     * Drawable轉Bitmap
     */
    public static Bitmap convertDrawable2BitmapByCanvas(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
