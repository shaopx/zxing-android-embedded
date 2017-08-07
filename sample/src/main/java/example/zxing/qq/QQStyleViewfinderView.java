package example.zxing.qq;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.ViewfinderView;

import java.util.List;

import example.zxing.R;

/**
 * I think this custom ViewfinderView subsclass can be a help for someone who want a qq-scan look in China
 *
 * @auther shaopx
 * @date 2017/8/5.
 */

public class QQStyleViewfinderView extends ViewfinderView {
    protected static final long ANIMATION_DELAY = 80L;

    protected int decorColor;
    private int descrStrokeWidth = 15;
    private int descrStrokeHeight = 100;


    private boolean scanAddUp = true;

    public QQStyleViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        decorColor = context.getResources().getColor(R.color.scan_window_dec_color);
        descrStrokeWidth = context.getResources().getDimensionPixelSize(R.dimen.stroke_width);
        descrStrokeHeight = context.getResources().getDimensionPixelSize(R.dimen.stroke_height);
    }

    @Override
    public void onDraw(Canvas canvas) {
        refreshSizes();
        if (framingRect == null || previewFramingRect == null) {
            return;
        }

        final Rect frame = framingRect;
        final Rect previewFrame = previewFramingRect;

        final int width = canvas.getWidth();
        final int height = canvas.getHeight();

        // Draw the exterior (i.e. outside the framing rect) darkened
        paint.setColor(resultBitmap != null ? resultColor : maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        if (decorColor != 0) {
            paint.setColor(decorColor);

            // left top
            int startX = frame.left-descrStrokeWidth;
            int startY = frame.top-descrStrokeWidth;
            canvas.drawRect(startX, startY, startX+descrStrokeHeight, startY+descrStrokeWidth, paint);
            canvas.drawRect(startX, startY, startX+descrStrokeWidth, startY+descrStrokeHeight, paint);

            // right top
            startX = frame.right+descrStrokeWidth -descrStrokeHeight;
            startY = frame.top-descrStrokeWidth;
            canvas.drawRect(startX, startY, startX+descrStrokeHeight, startY+descrStrokeWidth, paint);
            startX = frame.right;
            startY = frame.top;
            canvas.drawRect(startX, startY, startX+descrStrokeWidth, startY+85, paint);

            // left bottom
            startX = frame.left-descrStrokeWidth;
            startY = frame.bottom-(descrStrokeHeight-descrStrokeWidth);
            canvas.drawRect(startX, startY, startX+descrStrokeWidth, startY+descrStrokeHeight, paint);
            startX = frame.left;
            startY = frame.bottom;
            canvas.drawRect(startX, startY, startX+(descrStrokeHeight-descrStrokeWidth), startY+descrStrokeWidth, paint);

            // right bottom
            startX = frame.right;
            startY = frame.bottom-(descrStrokeHeight-descrStrokeWidth);
            canvas.drawRect(startX, startY, startX+descrStrokeWidth, startY+descrStrokeHeight, paint);
            startX = frame.right-(descrStrokeHeight-descrStrokeWidth);
            startY = frame.bottom;
            canvas.drawRect(startX, startY, startX+(descrStrokeHeight-descrStrokeWidth), startY+descrStrokeWidth, paint);

        }

        if (resultBitmap != null) {
            // Draw the opaque result bitmap over the scanning rectangle
            paint.setAlpha(CURRENT_POINT_OPACITY);
            canvas.drawBitmap(resultBitmap, null, frame, paint);
        } else {


            // Request another update at the animation interval, but only repaint the laser line,
            // not the entire viewfinder mask.
            postInvalidateDelayed(ANIMATION_DELAY);

        }
    }



}
