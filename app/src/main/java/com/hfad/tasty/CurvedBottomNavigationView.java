package com.hfad.tasty;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class CurvedBottomNavigationView extends BottomNavigationView {

    //Declare variable
    private Path mPath;
    private Paint mPaint;

    // The radius of fab button
    public final int CURVE_CIRCLE_RADIUS = 90;

    // The coordinates of the first curve
    public Point mFirstCurveStartPoint = new Point();
    public Point mFirstCurveEndPoint = new Point();
    public Point mFirstCurveControlPoint1 = new Point();
    public Point mFirstCurveControlPoint2 = new Point();

    //The coordinates of the second curve
    public Point mSecondCurveStartPoint = new Point();
    public Point mSecondCurveEndPoint = new Point();
    public Point mSecondCurveControlPoint1 = new Point();
    public Point mSecondCurveControlPoint2 = new Point();

    public int mNavigationBarWidth,mNavigationBarHeight;


    public CurvedBottomNavigationView(Context context) {
        super(context);
        initView();
    }

    public CurvedBottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CurvedBottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.WHITE);
        setBackgroundColor(Color.TRANSPARENT);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //Get width and height of navigation bar
        mNavigationBarWidth = getWidth();
        mNavigationBarHeight = getHeight();

        // the coordinates(x,y) of the start point before curve
        mFirstCurveStartPoint.set((mNavigationBarWidth/2)
                -(CURVE_CIRCLE_RADIUS*2)
                -(CURVE_CIRCLE_RADIUS/3),0);

        // the coordinates (x,y) of the end point after curve
        mFirstCurveEndPoint.set((mNavigationBarWidth/2),CURVE_CIRCLE_RADIUS+(CURVE_CIRCLE_RADIUS/4));

        //same for second curve
        mSecondCurveStartPoint = mFirstCurveEndPoint;

        mSecondCurveEndPoint.set((mNavigationBarWidth/2)+(CURVE_CIRCLE_RADIUS*2)+(CURVE_CIRCLE_RADIUS/3)
                ,0);

        // The coordinates (x,y_ of the first control point on cubic curve
        mFirstCurveControlPoint1.set(mFirstCurveStartPoint.x + (CURVE_CIRCLE_RADIUS)+(CURVE_CIRCLE_RADIUS/4),
                mFirstCurveStartPoint.y);
        // // The coordinates (x,y_ of the second control point on cubic curve
        mFirstCurveControlPoint2.set(mFirstCurveEndPoint.x-(CURVE_CIRCLE_RADIUS*2)+CURVE_CIRCLE_RADIUS,
                mFirstCurveEndPoint.y);

        mSecondCurveControlPoint1.set(mSecondCurveStartPoint.x+(CURVE_CIRCLE_RADIUS*2)-CURVE_CIRCLE_RADIUS,mSecondCurveStartPoint.y);
        mSecondCurveControlPoint2.set(mSecondCurveEndPoint.x-(CURVE_CIRCLE_RADIUS+(CURVE_CIRCLE_RADIUS/4)),mSecondCurveEndPoint.y);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(0,0);
        mPath.lineTo(mFirstCurveStartPoint.x,mFirstCurveStartPoint.y);

        mPath.cubicTo(mFirstCurveControlPoint1.x,mFirstCurveControlPoint1.y,
                mFirstCurveControlPoint2.x,mFirstCurveControlPoint2.y,
                mFirstCurveEndPoint.x,mFirstCurveEndPoint.y);

        mPath.cubicTo(mSecondCurveControlPoint1.x,mSecondCurveControlPoint1.y,
                mSecondCurveControlPoint2.x,mSecondCurveControlPoint2.y,
                mSecondCurveEndPoint.x,mSecondCurveEndPoint.y);

        mPath.lineTo(mNavigationBarWidth,0);
        mPath.lineTo(mNavigationBarWidth,mNavigationBarHeight);
        mPath.lineTo(0,mNavigationBarHeight);
        mPath.close();

        canvas.drawPath(mPath,mPaint);

    }
}