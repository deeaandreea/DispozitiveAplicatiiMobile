package dam.surdubobandreea1091.proiect3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.List;

public class BarChartGraph extends View {

    public static final String GRAFIC_PRETURI_CURSE = "Grafic preturi curse";
    private List<Float> valori;

    public BarChartGraph(Context context, List<Float> valori) {
        super(context);
        this.valori = valori;
    }

    protected void verticalBarChart(Canvas canvas) {

        final float margine_vert = 200;
        final float baza_grafic = canvas.getHeight() - margine_vert;
        final float inaltime_max_bara = baza_grafic - 2*margine_vert;

        float maxim = valori.get(0);
        for (int i = 1; i < valori.size(); i++) {
            if (maxim < valori.get(i))
                maxim = valori.get(i);
        }

        float latime = canvas.getWidth() / (2*valori.size() + 1);
        float x = 0f;
        int j = 10; int y = 5; int b = 77;

        Paint bara = new Paint();
        for (Float val : valori){
            bara.setColor(Color.rgb(j,y,b));
            j = (int) (j*45%255);
            y = (int) (y*7925%255);
            b = (int) (b*450%255);
            bara.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawRect(x + latime, margine_vert + (maxim - val)*inaltime_max_bara/maxim,
                    x + 2*latime, baza_grafic, bara);

            bara.setTextSize(36);
            canvas.drawText(val.toString(), x + latime, 150 + (maxim - val)*inaltime_max_bara/maxim, bara);

            x += 2 * latime;
        }

        bara.setTextSize(50);
        canvas.drawText(GRAFIC_PRETURI_CURSE, 75, baza_grafic + 150, bara);
    }

    protected void horizontalBarChart(Canvas canvas) {

        final float xleft_grafic = 100;
        final float latime_max_bara = canvas.getWidth()-200;

        float maxim = valori.get(0);
        for (int i = 1; i < valori.size(); i++) {
            if (maxim < valori.get(i))
                maxim = valori.get(i);
        }

        float latime = canvas.getHeight() / (2*valori.size() + 1);
        float y = latime;
        int j = 10; int k = 5; int b = 77;

        Paint bare = new Paint();
        for (Float val : valori){
            bare.setColor(Color.rgb(j,y,b));
            j = (int) (j*45%255);
            k = (int) (k*7925%255);
            b = (int) (b*450%255);
            bare.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawRect(xleft_grafic, y,
                    xleft_grafic + val*latime_max_bara/maxim, y + latime, bare);

            final float textSize = 48f;
            bare.setTextSize(textSize);
            bare.setColor(Color.BLUE);
            canvas.drawText(val.toString(), xleft_grafic + val*latime_max_bara/maxim + 5, y + latime/2, bare);

            y += 2 * latime;
        }

        bare.setTextSize(50);
        bare.setColor(Color.rgb(0,0,255));
        canvas.drawText(GRAFIC_PRETURI_CURSE, 75, canvas.getHeight() - 50, bare);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        verticalBarChart(canvas);
//        horizontalBarChart(canvas);
   }
}
