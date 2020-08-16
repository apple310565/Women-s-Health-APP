package flag.com.gooood1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.io.IOException;
import java.util.ArrayList;

public class graph extends AppCompatActivity {
    private SQLiteDatabase db;
    private StdDBHelper dbHelper;
    private BarChart barChart;
    private YAxis leftAxis;             //左侧Y轴
    private YAxis rightAxis;            //右侧Y轴
    private XAxis xAxis;                //X轴
    private Legend legend;              //图例
    private LimitLine limitLine;        //限制线
    int [] len={312,208,104,260,40};
    int [] len2={5,3,2,4,1};
    String [] color={"#5A5555","#FF2424","#F3877F","#981047","#490255"};
    String [] quality={"清","常","稠"};
    String [] carry={"無","黏","塊"};
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        //建立SQLOHleper物件
        dbHelper = new StdDBHelper(this);
        db =dbHelper.getWritableDatabase();//開啟資料庫
        String str="SELECT * FROM Data ORDER BY _date DESC";
        produce();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void produce(){
        Cursor c=db.rawQuery("SELECT * FROM Data ORDER BY _date DESC",null);
        c.moveToFirst();
        LinearLayout date=(LinearLayout)findViewById(R.id.date);
        LinearLayout graph=(LinearLayout)findViewById(R.id.graph);

        int t=c.getCount();
        for(int i=0;i<t;i++){
            int Flow,cc,Quality,Carry;
            String Date=c.getString(0);
            cc=Integer.parseInt(c.getString(2));
            Flow=Integer.parseInt(c.getString(1));
            Quality=Integer.parseInt(c.getString(3));
            Carry=Integer.parseInt(c.getString(6));
            LinearLayout date_in=new LinearLayout(this);
            date.addView(date_in, LinearLayout.LayoutParams.MATCH_PARENT,150);
            date_in.setGravity(Gravity.CENTER);
            TextView day=new TextView(this);
            day.setText(Date);
            day.setTextColor(Color.parseColor("#000000"));
            date_in.addView(day);
            LinearLayout graph_in =new LinearLayout(this);
            graph.addView(graph_in, LinearLayout.LayoutParams.MATCH_PARENT,150);
            graph_in.setGravity(Gravity.CENTER_VERTICAL);
            graph_in.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout gg_out =new LinearLayout(this);
            graph_in.addView(gg_out,320,LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout gg_in =new LinearLayout(this);

            /*if(Flow>0)gg_out.addView(gg_in,len[Flow-1],120);
            else gg_out.addView(gg_in,0,120);*/
            if(Flow>0){
                for(int j=0;j<len2[Flow-1];j++){
                    LinearLayout gg_in2 =new LinearLayout(this);
                    gg_in2.setPadding(3,5,3,5);
                    LinearLayout gg_inn =new LinearLayout(this);
                    if(cc>0)gg_inn.setBackgroundColor(Color.parseColor(color[cc-1]));
                    else gg_inn.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gg_in2.addView(gg_inn,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                    gg_out.addView(gg_in2,62,120);
                }
            }

            if(Flow==4){
                gg_out.setBackground(getResources().getDrawable( R.drawable.graph_frame2 ));
            }
            else {
                gg_out.setBackground(getResources().getDrawable( R.drawable.graph_frame ));
            }
            gg_out.setPadding(4,4,4,4);
            /*if(cc>0)gg_in.setBackgroundColor(Color.parseColor(color[cc-1]));
            else gg_in.setBackgroundColor(Color.parseColor("#FFFFFF"));*/

            //質地和挾帶物
            LinearLayout qc_out =new LinearLayout(this);
            qc_out.setPadding(20,0,20,0);
            qc_out.setGravity(Gravity.CENTER);
            TextView Q =new TextView(this);
            if(Quality>0){
                Q.setText(quality[Quality-1]);
                Q.setTextColor(Color.parseColor("#000000"));
            }
            else {
                Q.setText("N");
                Q.setTextColor(Color.parseColor("#FF2424"));
            }
            Q.setTextSize(18);
            qc_out.addView(Q);
            TextView QC =new TextView(this);
            QC.setText(" | ");
            QC.setTextSize(15);
            QC.setTextColor(Color.parseColor("#000000"));
            qc_out.addView(QC);
            TextView C =new TextView(this);
            if(Carry>0){
                C.setText(carry[Carry-1]);
                C.setTextColor(Color.parseColor("#000000"));
            }
            else {
                C.setText("N");
                C.setTextColor(Color.parseColor("#FF2424"));
            }
            C.setTextSize(18);
            qc_out.addView(C);
            graph_in.addView(qc_out,180,LinearLayout.LayoutParams.MATCH_PARENT);

            //症狀

            LinearLayout sym_out =new LinearLayout(this);
            graph_in.addView(sym_out,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            sym_out.setGravity(Gravity.CENTER);
            Cursor data2 = db.rawQuery("SELECT * FROM Data2 WHERE _date = '" + Date + "'", null);
            int f=0;
            data2.moveToFirst();
            if(data2.getCount()!=0){
                if(data2.getString(1).equals("1"))f=1;
                if(data2.getString(2).equals("1"))f=1;
                if(data2.getString(3).equals("1"))f=1;
                if(data2.getString(4).equals("1"))f=1;
                if(data2.getString(5).equals("1"))f=1;
                if(data2.getString(6).equals("1"))f=1;
                if(data2.getString(7).equals("1"))f=1;
                if(data2.getString(8).equals("1"))f=1;
                if(data2.getString(9).equals("1"))f=1;
            }
            TextView SYM =new TextView(this);
            if(f==1)SYM.setText("◯");
            else SYM.setText("✕");
            SYM.setTextSize(20);
            data2.close();

            SYM.setTextColor(Color.parseColor("#000000"));
            sym_out.addView(SYM);

            if(c.getString(4).equals("1")){
                LinearLayout p_d=new LinearLayout(this);
                LinearLayout p_g=new LinearLayout(this);
                date.addView(p_d, LinearLayout.LayoutParams.MATCH_PARENT,150);
                graph.addView(p_g, LinearLayout.LayoutParams.MATCH_PARENT,150);
                p_g.setGravity(Gravity.CENTER_VERTICAL);
                p_g.setPadding(20,4,20,4);
                TextView peroid=new TextView(this);

                Cursor tmp = db.rawQuery("SELECT * FROM Period WHERE _date = '" + Date + "'", null);
                tmp.moveToFirst();
                if(tmp.getCount()!=0)peroid.setText("經期間隔: " + tmp.getString(1) + "天");
                else peroid.setText("經期間隔: 無紀錄");
                p_g.addView(peroid);
                tmp.close();
            }
            c.moveToNext();
        }
        c.close();
    }

    }
