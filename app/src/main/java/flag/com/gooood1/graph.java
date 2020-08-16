package flag.com.gooood1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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
            final int Flow,cc,Quality,Carry;
            final String Date=c.getString(0);
             cc=Integer.parseInt(c.getString(2));
             Flow=Integer.parseInt(c.getString(1));
             Quality=Integer.parseInt(c.getString(3));
            Carry=Integer.parseInt(c.getString(6));
            final LinearLayout date_in=new LinearLayout(this);
            date.addView(date_in, LinearLayout.LayoutParams.MATCH_PARENT,150);
            date_in.setGravity(Gravity.CENTER);
            TextView day=new TextView(this);
            day.setText(Date);
            day.setTextColor(Color.parseColor("#000000"));
            date_in.addView(day);
            final LinearLayout graph_in =new LinearLayout(this);
            graph.addView(graph_in, LinearLayout.LayoutParams.MATCH_PARENT,150);
            graph_in.setGravity(Gravity.CENTER_VERTICAL);
            graph_in.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout gg_out =new LinearLayout(this);
            graph_in.addView(gg_out,320,LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout gg_in =new LinearLayout(this);

            //設監聽
            graph_in.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    pick(Date,Flow,cc,Quality,Carry);
                }
            });
            date_in.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    pick(Date,Flow,cc,Quality,Carry);
                }
            });

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
    public void pick(String date,int flow,int color,int quality,int carry){
        String [] F={"多","正常","少","或多或少","點滴性出血"};
        String [] C ={"黝深","鮮紅","淡紅","紫紅","紫黑"};
        String [] Q={"清稀","正常","黏稠"};
        String [] CA ={"無","黏液","血塊"};
        String [] str2={"腰臀部脹痛","乳房脹痛","面目浮腫","肢軟無力","肢體腫脹不適","肢體麻木疼痛","關節疼痛"};
        String []str3={"皮膚起疹","膚色焮紅","身癢"};
        String [] str4={"口糜舌爛","口臭","口燥咽乾"};
        String [] str5={"吐血","衄血","齒衄","咯血"};
        String [] str6={"無故悲傷","煩躁易怒","神志不清"};
        LayoutInflater inflater = LayoutInflater.from(graph.this);
        final View v = inflater.inflate(R.layout.graph_data, null);
        TextView tv_color=(TextView) (v.findViewById(R.id.v_color));
        TextView tv_flow=(TextView) (v.findViewById(R.id.v_flow));
        TextView tv_quality=(TextView) (v.findViewById(R.id.v_quality));
        TextView tv_carry=(TextView) (v.findViewById(R.id.v_carry));
        TextView tv_sym=(TextView) (v.findViewById(R.id.v_sym));
        if(color>0)tv_color.setText(C[color-1]);
        else tv_color.setText("無資料");
        if(flow>0)tv_flow.setText(F[flow-1]);
        else tv_flow.setText("無資料");
        if(quality>0)tv_quality.setText(Q[quality-1]);
        else tv_quality.setText("無資料");
        if(carry>0)tv_carry.setText(CA[carry-1]);
        else tv_carry.setText("無資料");
        int f=0;String str="";
        Cursor data2 = db.rawQuery("SELECT * FROM Data2 WHERE _date = '" + date + "'", null);
        data2.moveToFirst();
        int flag=0;
        if(data2.getCount()!=0){
            if(data2.getString(1).equals("1")){
                str+="經痛";
                f=1;
                flag=1;
            }
            if(data2.getString(7).equals("1")){
                if(f!=0)str+="、";
                str+="月經氣味異常";
                f=1;
                flag=1;
            }
            if(data2.getString(8).equals("1")){
                if(f!=0)str+="、";
                str+="頭暈";
                f=1;
                flag=1;
            }
            if(data2.getString(9).equals("1")){
                if(f!=0)str+="、";
                str+="頭痛";
                f=1;
                flag=1;
            }
            if(f==1)str+="。\n";
            if(data2.getString(2).equals("1")){
                f=0;
                flag=1;
                str+="身痛: ";
                Cursor Ch2 = db.rawQuery("SELECT * FROM Ch2 WHERE _date = '" + date + "'", null);
                Ch2.moveToFirst();
                for(int i=0;i<7;i++){
                    if(Ch2.getString(i+1).equals("1")){
                        if(f>0)str+="、";
                        str+=str2[i];
                        f=1;
                    }
                }
                str+="。\n";
            }
            if(data2.getString(3).equals("1")){
                f=0;
                flag=1;
                str+="皮膚異常: ";
                Cursor Ch3 = db.rawQuery("SELECT * FROM Ch3 WHERE _date = '" + date + "'", null);
                Ch3.moveToFirst();
                for(int i=0;i<3;i++){
                    if(Ch3.getString(i+1).equals("1")){
                        if(f>0)str+="、";
                        str+=str3[i];
                        f=1;
                    }
                }
                str+="。\n";
            }
            if(data2.getString(4).equals("1")){
                f=0;
                flag=1;
                str+="口腔異常: ";
                Cursor Ch4 = db.rawQuery("SELECT * FROM Ch4 WHERE _date = '" + date + "'", null);
                Ch4.moveToFirst();
                for(int i=0;i<3;i++){
                    if(Ch4.getString(i+1).equals("1")){
                        if(f>0)str+="、";
                        str+=str4[i];
                        f=1;
                    }
                }
                str+="。\n";
            }
            if(data2.getString(5).equals("1")){
                f=0;
                flag=1;
                str+="出血: ";
                Cursor Ch5 = db.rawQuery("SELECT * FROM Ch5 WHERE _date = '" + date + "'", null);
                Ch5.moveToFirst();
                for(int i=0;i<4;i++){
                    if(Ch5.getString(i+1).equals("1")){
                        if(f>0)str+="、";
                        str+=str5[i];
                        f=1;
                    }
                }
                str+="。\n";
            }
            if(data2.getString(6).equals("1")){
                f=0;
                flag=1;
                str+="情緒不受控: ";
                Cursor Ch6 = db.rawQuery("SELECT * FROM Ch6 WHERE _date = '" + date + "'", null);
                Ch6.moveToFirst();
                for(int i=0;i<3;i++){
                    if(Ch6.getString(i+1).equals("1")){
                        if(f>0)str+="、";
                        str+=str6[i];
                        f=1;
                    }
                }
                str+="。\n";
            }
            if(flag==0)str+="無。\n";

            tv_sym.setText(str);
        }



        new AlertDialog.Builder(graph.this)
                .setView(v)
                .setIcon(getResources().getDrawable( R.drawable.leaf ))
                .setTitle(date+" 詳細資料")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    }
