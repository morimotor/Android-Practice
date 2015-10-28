package jp.morimotor.androidpractice;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GraphView extends View {

    float div = 0;
    int position = 0;

    int[] data = new int[Resources.getSystem().getDisplayMetrics().widthPixels];

    public GraphView(Context context) {
        super(context);
    }

    public GraphView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDiv(float div){
        this.div = div;
        this.position++;
        if (data.length <= position)position = 0;

        data[position] = (int) (div * 8);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = this.getWidth();
        int height = this.getHeight();
        int base = height / 2;

        Paint paint = new Paint();


        // グリッド
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(1);

        for (int y = 0; y < height; y = y + 20)
            canvas.drawLine(0, y, width, y, paint);

        for (int x = 0; x < width; x = x + 20)
            canvas.drawLine(x, 0, x, height, paint);


        // グラフの生成
        paint.setColor(Color.RED);
        paint.setStrokeWidth(2);

        for ( int i = 0; i < data.length - 1 ; i++ )
            canvas.drawLine(i, base + data[i], i + 1, base + data[i + 1], paint);

        // 先頭
        paint.setColor(Color.BLUE);
        canvas.drawLine(position, 0, position, height, paint);

    }


}