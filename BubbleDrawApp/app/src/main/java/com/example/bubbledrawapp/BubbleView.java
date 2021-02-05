package com.example.bubbledrawapp;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.Random;

public class BubbleView extends ImageView implements View.OnTouchListener {
    private Random rand = new Random();
    private ArrayList<Bubble> bubbleList;
    private int size = 300;
    private int delay = 33;
    private Paint myPaint = new Paint();
    private Handler h = new Handler();

    public BubbleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        bubbleList = new ArrayList<Bubble>();
        // testBubbles();
        setOnTouchListener(this);
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            for (Bubble b : bubbleList)
                b.update();
            invalidate();
        }
    };
    
    protected void onDraw(Canvas canvas) {
        for (Bubble b : bubbleList)
            b.draw(canvas);
        h.postDelayed(r, delay);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int count = motionEvent.getPointerCount();
        int s = size / (int) Math.pow(count, 2);
        for (int i = 0; i < count; i++) {
            int x = (int) motionEvent.getX(i);
            int y = (int) motionEvent.getY(i);
            if (count > 1)
                bubbleList.add(new Bubble(x, y, s));
            else
                bubbleList.add(new Bubble(x, y, s, 15, 15));
        }
        return true;
    }

    public void testBubbles() {
        for (int i = 0; i < 100; i++) {
            int x = rand.nextInt(500) + 50;
            int y = rand.nextInt(500) + 50;
            int s = rand.nextInt(size) + size;
            bubbleList.add(new Bubble(x, y, s));
        }
        invalidate();
    }

    private class Bubble {
        private int x;
        private int y;
        private int size;
        private int color;
        private int xspeed, yspeed;
        private final int MAX_SPEED = 15;

        public Bubble(int newX, int newY, int newSize) {
            x = newX;
            y = newY;
            size = newSize;
            color = Color.argb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256),
                    rand.nextInt(256));
            xspeed = rand.nextInt(MAX_SPEED * 2 + 1) - MAX_SPEED;
            yspeed = rand.nextInt(MAX_SPEED * 2 + 1) - MAX_SPEED;
            if (xspeed == 0)
                xspeed = 1;
            if (yspeed == 0)
                yspeed = 1;
        }

        public Bubble(int newX, int newY, int newSize, int newXspeed, int newYspeed) {
            x = newX;
            y = newY;
            size = newSize;
            color = Color.argb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256),
                    rand.nextInt(256));
            xspeed = newXspeed;
            yspeed = newYspeed;
        }

        public void draw(Canvas canvas) {
            myPaint.setColor(color);
            canvas.drawOval(x - size / 2, y - size / 2, x + size / 2, y + size / 2, myPaint);
        }

        public void update() {
            x += xspeed;
            y += yspeed;
            if (x - size / 2 <= 0 || x + size / 2 >= getWidth())
                xspeed = -xspeed;
            if (y - size / 2 <= 0 || y + size / 2 >= getHeight())
                yspeed = -yspeed;
        }
    }
}
