package com.example.lab8_bsmith179_2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.ReplacementSpan;

import androidx.annotation.NonNull;

public class DoubleStrikeSpan extends ReplacementSpan {

    private final float lineThickness;
    private final float lineDistance;
    private final float lineFraction;

    public DoubleStrikeSpan(float lineThickness, float lineDistanceFraction, float lineFraction) {
        this.lineThickness = lineThickness;
        this.lineDistance = lineDistanceFraction;
        this.lineFraction = lineFraction;
    }

    @Override
    public int getSize(@NonNull Paint paint, @NonNull CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return Math.round(paint.measureText(text, start, end));
    }

    @Override
    public void draw(@NonNull Canvas canvas, @NonNull CharSequence text, int start, int end,
                     float x, int top, int y, int bottom, @NonNull Paint paint) {

        String str = text.subSequence(start, end).toString();
        canvas.drawText(str, x, y, paint);

        float width = paint.measureText(str);

        // Vertical center of the character
        Paint.FontMetrics fm = paint.getFontMetrics();
        float centerY = y + (fm.ascent + fm.descent) / 2;

        float offset = paint.getTextSize() * lineDistance;

        float startX = x - width * 0.1f;
        float endX = startX + width * lineFraction;

        float originalStrokeWidth = paint.getStrokeWidth();
        paint.setStrokeWidth(lineThickness);


        canvas.drawLine(startX, centerY + offset +1, endX, centerY + offset +1, paint);
        canvas.drawLine(startX, centerY + offset +5, endX, centerY + offset +5, paint);

        paint.setStrokeWidth(originalStrokeWidth);
    }
}
