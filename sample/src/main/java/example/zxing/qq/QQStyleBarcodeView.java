package example.zxing.qq;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

import com.journeyapps.barcodescanner.BarcodeView;
import com.journeyapps.barcodescanner.Size;

import example.zxing.R;

/**
 * Created by SHAOPENGXIANG on 2017/8/7.
 */

public class QQStyleBarcodeView extends BarcodeView {

    private static final String TAG = "QQStyleBarcodeView";
    private Size framingRectSize = null;
    private double marginFraction = 0.1d;
    private int framingRectWidth;
    private int framingRectHeight;
    private int framingRectMarginTop;
    private int framingRectMarginLeft;
    private int framingRectMarginRight;
    private boolean framingRectCenterVertical = false;

    public QQStyleBarcodeView(Context context) {
        this(context, null);
    }

    public QQStyleBarcodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStyleBarcodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray styledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.zxing_custom);
        framingRectWidth = (int) styledAttributes.getDimension(R.styleable.zxing_custom_zxing_framing_rect_width, -1);
        framingRectHeight = (int) styledAttributes.getDimension(R.styleable.zxing_custom_zxing_framing_rect_height, -1);
        framingRectMarginTop = (int) styledAttributes.getDimension(R.styleable.zxing_custom_zxing_framing_rect_marginTop, -1);
        framingRectMarginLeft = (int) styledAttributes.getDimension(R.styleable.zxing_custom_zxing_framing_rect_layout_marginLeft, -1);
        framingRectMarginRight = (int) styledAttributes.getDimension(R.styleable.zxing_custom_zxing_framing_rect_layout_marginRight, -1);
        framingRectCenterVertical = styledAttributes.getBoolean(R.styleable.zxing_custom_zxing_framing_rect_layout_centerVertical, false);
        framingRectSize = new Size(framingRectWidth, framingRectHeight);
        styledAttributes.recycle();
    }

    protected Rect calculateFramingRect(Rect container, Rect surface) {
//        Log.d(TAG, "calculateFramingRect: container:" + container);
        // intersection is the part of the container that is used for the preview
        Rect intersection = new Rect(container);
        if (framingRectSize != null) {
            int verticalMargin = Math.max(0, (intersection.height() - framingRectSize.height) / 2);
            int horizontalMargin = Math.max(0, (intersection.width() - framingRectSize.width) / 2);

            if (framingRectMarginLeft < horizontalMargin) {
                framingRectMarginLeft = horizontalMargin;
            }

            if (framingRectMarginRight < horizontalMargin) {
                framingRectMarginRight = horizontalMargin;
            }

            intersection.top = framingRectMarginTop;
            intersection.left = (intersection.width() - framingRectSize.width) / 2;
            intersection.right = intersection.left + framingRectSize.width;
            intersection.bottom = intersection.top + framingRectSize.width;

//
            return intersection;
        }
        return intersection;
    }
}
