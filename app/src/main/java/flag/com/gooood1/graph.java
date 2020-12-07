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
import android.widget.Toast;

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
    int [] len={40,104,208,260,40};
    int [] len2={1,2,3,4,1};
    String [] color={"#ffc2c2","#ffc2c2","#feb4b3","#fe8783","#fc5450","#f42f2f","#e61a2e","#d31034","#bd0b41","#a60642","#8b053c","#6f0533","#59052a","#46061f","#350711","#310d08","#391c09","#391c09","#8a582b","#ab6f3a"};
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

    @SuppressLint("RtlHardcoded")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void produce(){
        int [][]x={{1,2,3,4,5},{1,3,6,10,15},{1,3,6,10,15},{1,3,6,10,15}};

        //取得用月經週期平均長度
        Cursor avg = db.rawQuery("SELECT AVG(p) FROM Period", null);
        avg.moveToFirst();
        String AVG=avg.getString(0);
        avg.close();

        Cursor c=db.rawQuery("SELECT * FROM Data ORDER BY _date DESC",null);
        c.moveToFirst();
        LinearLayout date=(LinearLayout)findViewById(R.id.date);
        LinearLayout graph=(LinearLayout)findViewById(R.id.graph);

        int total_score=0;
        int t=c.getCount();
        boolean is_end=false;
        for(int i=0;i<t;i++){
             int Flow=2,cc=0,Quality=0,Carry=0;
            final String Date=c.getString(0);
            try {
                //取出現次數最多的顏色
                /*
                Cursor c2 = db.rawQuery("SELECT color from Tampon where _date = '" + Date + "' group by color order by count(color) DESC", null);
                c2.moveToFirst();
                if (c2.getCount() > 0) cc = Integer.parseInt(c2.getString(0));
                else cc = 0;
                if(cc>19)cc=19;
                c2.close();*/
                //依score的比例混合顏色
                Cursor c2 = db.rawQuery("SELECT * from Tampon where _date = '" + Date + "' ", null);
                c2.moveToFirst();
                int t2=c2.getCount();
                int ttt=0;
                for(int j=0;j<t2;j++){
                    int s,type;
                    type=c2.getInt(3);
                    s=c2.getInt(4);
                    cc+=x[type-1][s-1]*c2.getInt(7);
                    ttt+=x[type-1][s-1];
                    c2.moveToNext();
                }
                cc=(int)((float)cc/(float)ttt);
                if(cc>19)cc=19;
                c2.close();

                c2 = db.rawQuery("SELECT quality from Tampon where _date = '" + Date + "' group by quality order by count(quality) DESC", null);
                c2.moveToFirst();
                if (c2.getCount() > 0) Quality = Integer.parseInt(c2.getString(0));
                else Quality = 0;
                c2.close();

                c2 = db.rawQuery("SELECT carry from Tampon where _date = '" + Date + "' group by carry order by count(carry) DESC", null);
                c2.moveToFirst();
                if (c2.getCount() > 0) Carry = Integer.parseInt(c2.getString(0));
                else Carry = 0;
                for(int k=0;k<c2.getCount();k++){
                    if(Carry<Integer.parseInt(c2.getString(0)))Carry=Integer.parseInt(c2.getString(0));
                    c2.moveToNext();
                }
                c2.close();

                //算FLOW
                Cursor TAN=db.rawQuery("SELECT * FROM Tampon WHERE _date = '"+Date+"'",null);
                TAN.moveToFirst();
                int score=0;
                for(int j=0;j<TAN.getCount();j++){
                    int type,s;
                    type=TAN.getInt(3);
                    s=TAN.getInt(4);
                    if(s!=0&&type!=0)score+=x[type-1][s-1];
                    TAN.moveToNext();
                }
                TAN.close();
                if(score==0)Flow=0;
                else if(score<5)Flow=1;
                else if(score<15)Flow=2;
                else Flow=3;
                total_score+=score;


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
                final int Flow2=Flow;
                final int cc2=cc;
                final int Quality2=Quality;
                final int Carry2=Carry;
            date_in.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    pick(Date,Flow2,cc2,Quality2,Carry2);
                }
            });
            gg_out.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    tampon_veiw(Date);
                }
            });

            gg_out.setGravity(Gravity.LEFT);
            if(Flow>0){
                for(int j=0;j<Flow;j++){
                    LinearLayout gg_in2 =new LinearLayout(this);
                    gg_in2.setPadding(3,5,3,5);
                    gg_in2.setGravity(Gravity.LEFT);
                    LinearLayout gg_inn =new LinearLayout(this);
                    if(cc>0)gg_inn.setBackgroundColor(Color.parseColor(color[cc-1]));
                    else gg_inn.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gg_in2.addView(gg_inn,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                    gg_out.addView(gg_in2,102,120);
                }
            }
            gg_out.setBackgroundColor(Color.parseColor("#FFFFFF"));

            if(Flow==4){
                gg_out.setBackground(getResources().getDrawable( R.drawable.graph_frame2 ));
            }
            else {
                gg_out.setBackground(getResources().getDrawable( R.drawable.graph_frame ));
            }
            gg_out.setPadding(4,4,4,4);
            if(cc>0)gg_in.setBackgroundColor(Color.parseColor(color[cc-1]));
            else gg_in.setBackgroundColor(Color.parseColor("#FFFFFF"));

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
            qc_out.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    tampon_veiw(Date);
                }
            });

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
                if(data2.getString(10).equals("1"))f=1;
                if(data2.getString(11).equals("1"))f=1;
            }
            TextView SYM =new TextView(this);
            if(f==1)SYM.setText("◯");
            else SYM.setText("✕");
            SYM.setTextSize(20);
            data2.close();
                sym_out.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        pick(Date,Flow2,cc2,Quality2,Carry2);
                    }
                });

            SYM.setTextColor(Color.parseColor("#000000"));
            sym_out.addView(SYM);


            if(c.getString(2).equals("1")){
                is_end=true;
            }
            if(c.getString(1).equals("1")){
                LinearLayout p_d=new LinearLayout(this);
                LinearLayout p_g=new LinearLayout(this);
                date.addView(p_d, LinearLayout.LayoutParams.MATCH_PARENT,150);
                graph.addView(p_g, LinearLayout.LayoutParams.MATCH_PARENT,150);
                p_g.setGravity(Gravity.CENTER_VERTICAL);
                p_g.setPadding(20,4,20,4);
                TextView peroid=new TextView(this);

                Cursor tmp = db.rawQuery("SELECT * FROM Period WHERE _date = '" + Date + "'", null);
                tmp.moveToFirst();
                if(tmp.getCount()!=0)peroid.setText("經期間隔: " + tmp.getString(1) + "天 , 平均間隔: "+AVG+"天\n");
                else peroid.setText("經期間隔: 無紀錄 , 平均間隔: "+AVG+"天\n");
                if(is_end){
                    peroid.setText(peroid.getText()+"總積分: "+Integer.toString(total_score));
                    if(total_score<20)peroid.setText(peroid.getText()+" => 經量過少");
                    else if(total_score<80)peroid.setText(peroid.getText()+" => 經量正常");
                    else peroid.setText(peroid.getText()+" => 經量過多");
                    p_g.addView(peroid);
                    is_end=false;
                }
                tmp.close();
            }


            c.moveToNext();

            }catch (Exception e){

                Toast.makeText(this,"error: "+e.toString(),Toast.LENGTH_LONG);
            }
        }
        c.close();
    }
    public void pick(String date,int flow,int color,int quality,int carry){
        try {
            Cursor temp=db.rawQuery("select temperature from Temperature where _date = '"+date+"'",null);
            temp.moveToFirst();
            float temperature=0;
            if (temp.getCount() > 0) temperature = Float.parseFloat(temp.getString(0));
            else temperature=-1;
            temp.close();
            if (color > 16) color = 5;
            else if (color > 12) color = 4;
            else if (color > 8) color = 3;
            else if (color > 4) color = 2;
            else color = 1;
            String[] F = {"少", "正常", "多"};
            String[] C = {"淡紅", "鮮紅", "紫紅", "紫黑", "黝深"};
            String[] Q = {"清稀", "正常", "黏稠"};
            String[] CA = {"無", "黏液", "血塊"};
            String[] str2 = {"腰臀部脹痛", "乳房脹痛", "面目浮腫", "肢軟無力", "肢體腫脹不適", "肢體麻木疼痛", "關節疼痛"};
            String[] str3 = {"皮膚起疹", "膚色焮紅", "身癢"};
            String[] str4 = {"口糜舌爛", "口臭", "口燥咽乾"};
            String[] str5 = {"吐血", "衄血", "齒衄", "咯血"};
            String[] str6 = {"無故悲傷", "煩躁易怒", "神志不清"};
            String [] str11_1={"額頭","鼻子","左臉","右臉","下巴","唇邊"};
            String [] str11_2={"有頭膿包","無頭膿包","粉刺","色素沉澱","痘疤"};

            LayoutInflater inflater = LayoutInflater.from(graph.this);
            final View v = inflater.inflate(R.layout.graph_data, null);
            TextView tv_color = (TextView) (v.findViewById(R.id.v_color));
            TextView tv_flow = (TextView) (v.findViewById(R.id.v_flow));
            TextView tv_quality = (TextView) (v.findViewById(R.id.v_quality));
            TextView tv_carry = (TextView) (v.findViewById(R.id.v_carry));
            TextView tv_sym = (TextView) (v.findViewById(R.id.v_sym));
            TextView tv_temp = (TextView) (v.findViewById(R.id.v_temperature));
            if (temperature > 0)tv_temp.setText(Float.toString(temperature) + " °C");
            else tv_temp.setText("無資料");
            if(flow==0)color=0;
            if (color > 0) tv_color.setText(C[color - 1]);
            else tv_color.setText("無資料");
            if (flow > 0) tv_flow.setText(F[flow - 1]);
            else tv_flow.setText("無資料");
            if (quality > 0) tv_quality.setText(Q[quality - 1]);
            else tv_quality.setText("無資料");
            if (carry > 0) tv_carry.setText(CA[carry - 1]);
            else tv_carry.setText("無資料");
            int f = 0;
            String str = "";
            Cursor data2 = db.rawQuery("SELECT * FROM Data2 WHERE _date = '" + date + "'", null);
            data2.moveToFirst();
            int flag = 0;
            if (data2.getCount() != 0) {
                if (data2.getString(1).equals("1")) {
                    str += "經痛";
                    f = 1;
                    flag = 1;
                }
                if (data2.getString(7).equals("1")) {
                    if (f != 0) str += "、";
                    str += "月經氣味異常";
                    f = 1;
                    flag = 1;
                }
                if (data2.getString(8).equals("1")) {
                    if (f != 0) str += "、";
                    str += "頭暈";
                    f = 1;
                    flag = 1;
                }
                if (data2.getString(9).equals("1")) {
                    if (f != 0) str += "、";
                    str += "頭痛";
                    f = 1;
                    flag = 1;
                }
                if (data2.getString(10).equals("1")) {
                    if (f != 0) str += "、";
                    str += "大便溏瀉";
                    f = 1;
                    flag = 1;
                }
                if (f == 1) str += "。\n";
                if (data2.getString(2).equals("1")) {
                    f = 0;
                    flag = 1;
                    str += "身痛: ";
                    Cursor Ch2 = db.rawQuery("SELECT * FROM Ch2 WHERE _date = '" + date + "'", null);
                    Ch2.moveToFirst();
                    for (int i = 0; i < 7; i++) {
                        if (Ch2.getString(i + 1).equals("1")) {
                            if (f > 0) str += "、";
                            str += str2[i];
                            f = 1;
                        }
                    }
                    str += "。\n";
                }
                if (data2.getString(3).equals("1")) {
                    f = 0;
                    flag = 1;
                    str += "皮膚異常: ";
                    Cursor Ch3 = db.rawQuery("SELECT * FROM Ch3 WHERE _date = '" + date + "'", null);
                    Ch3.moveToFirst();
                    for (int i = 0; i < 3; i++) {
                        if (Ch3.getString(i + 1).equals("1")) {
                            if (f > 0) str += "、";
                            str += str3[i];
                            f = 1;
                        }
                    }
                    str += "。\n";
                }
                if (data2.getString(4).equals("1")) {
                    f = 0;
                    flag = 1;
                    str += "口腔異常: ";
                    Cursor Ch4 = db.rawQuery("SELECT * FROM Ch4 WHERE _date = '" + date + "'", null);
                    Ch4.moveToFirst();
                    for (int i = 0; i < 3; i++) {
                        if (Ch4.getString(i + 1).equals("1")) {
                            if (f > 0) str += "、";
                            str += str4[i];
                            f = 1;
                        }
                    }
                    str += "。\n";
                }
                if (data2.getString(5).equals("1")) {
                    f = 0;
                    flag = 1;
                    str += "出血: ";
                    Cursor Ch5 = db.rawQuery("SELECT * FROM Ch5 WHERE _date = '" + date + "'", null);
                    Ch5.moveToFirst();
                    for (int i = 0; i < 4; i++) {
                        if (Ch5.getString(i + 1).equals("1")) {
                            if (f > 0) str += "、";
                            str += str5[i];
                            f = 1;
                        }
                    }
                    str += "。\n";
                }
                if (data2.getString(6).equals("1")) {
                    f = 0;
                    flag = 1;
                    str += "情緒不受控: ";
                    Cursor Ch6 = db.rawQuery("SELECT * FROM Ch6 WHERE _date = '" + date + "'", null);
                    Ch6.moveToFirst();
                    for (int i = 0; i < 3; i++) {
                        if (Ch6.getString(i + 1).equals("1")) {
                            if (f > 0) str += "、";
                            str += str6[i];
                            f = 1;
                        }
                    }
                    str += "。\n";
                }
                if (data2.getString(11).equals("1")) {
                    f = 0;
                    str += "痘痘: ";
                    Cursor Ch11_1 = db.rawQuery("SELECT * FROM Ch11_1 WHERE _date = '" + date + "'", null);
                    Ch11_1.moveToFirst();
                    for (int i = 0; i < 6; i++) {
                        if (Ch11_1.getString(i + 1).equals("1")) {
                            if (f > 0) str += "、";
                            str += str11_1[i];
                            f = 1;
                        }
                    }
                    Ch11_1.close();
                    Cursor Ch11_2 = db.rawQuery("SELECT * FROM Ch11_2 WHERE _date = '" + date + "'", null);
                    Ch11_2.moveToFirst();
                    for (int i = 0; i < 5; i++) {
                        if (Ch11_2.getString(i + 1).equals("1")) {
                            if (f > 0) str += "、";
                            str += str11_2[i];
                            f = 1;
                        }
                    }
                    str += "。\n";
                    flag = 1;
                }

                if (flag == 0) str += "無。\n";

                tv_sym.setText(str);
            }


            new AlertDialog.Builder(graph.this)
                    .setView(v)
                    .setIcon(getResources().getDrawable(R.drawable.leaf))
                    .setTitle(date + " 詳細資料")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }catch (Exception e){
            Toast.makeText(this,"ERROR"+e.toString(),Toast.LENGTH_SHORT);
            new AlertDialog.Builder(graph.this)
                    .setIcon(getResources().getDrawable(R.drawable.leaf))
                    .setMessage("ERROR: "+e.toString())
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void tampon_veiw(String Date111){
        int [][]x={{1,2,3,4,5},{1,3,6,10,15},{1,3,6,10,15},{1,3,6,10,15}};
        String [] color={"#ffc2c2","#ffc2c2","#feb4b3","#fe8783","#fc5450","#f42f2f","#e61a2e","#d31034","#bd0b41","#a60642","#8b053c","#6f0533","#59052a","#46061f","#350711","#310d08","#391c09","#391c09","#8a582b","#ab6f3a"};
        String [] quality={"清","常","稠"};
        String [] carry={"無","黏","塊"};
        Cursor TAN=db.rawQuery("SELECT * FROM Tampon WHERE _date = '"+Date111+"'",null);
        TAN.moveToFirst();
        int score=0;
        for(int i=0;i<TAN.getCount();i++){
            int type,s;
            type=TAN.getInt(3);
            s=TAN.getInt(4);
            if(s!=0&&type!=0)score+=x[type-1][s-1];
            TAN.moveToNext();
        }
        LayoutInflater inflater = LayoutInflater.from(graph.this);
        final View v = inflater.inflate(R.layout.tampon_view, null);
        TextView tv=v.findViewById(R.id.tv);
        tv.setText(tv.getText()+Integer.toString(score));

        Cursor c=db.rawQuery("SELECT * FROM Tampon WHERE _date = '"+Date111+"' ORDER BY t DESC",null);
        c.moveToFirst();
        LinearLayout date=(LinearLayout)v.findViewById(R.id.date);
        LinearLayout graph=(LinearLayout)v.findViewById(R.id.graph);

        int t=c.getCount();
        for(int i=0;i<t;i++){
            int cc=0,Quality=0,Carry=0;
            final String Date=c.getString(2);
            cc=c.getInt(7);
            Quality=c.getInt(5);
            Carry=c.getInt(6);
            int type=c.getInt(3);
            int s=c.getInt(4);
            score=x[type-1][s-1];

            try {
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

                gg_out.setGravity(Gravity.LEFT);
                //if(score>0){
                //for(int j=0;j<score;j++){
                LinearLayout gg_in2 =new LinearLayout(this);
                gg_in2.setPadding(3,5,3,5);
                gg_in2.setGravity(Gravity.LEFT);
                LinearLayout gg_inn =new LinearLayout(this);
                if(cc>0)gg_inn.setBackgroundColor(Color.parseColor(color[cc-1]));
                else gg_inn.setBackgroundColor(Color.parseColor("#FFFFFF"));
                gg_in2.addView(gg_inn,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                gg_out.addView(gg_in2,score*20,120);
                // }
                //}
                gg_out.setBackgroundColor(Color.parseColor("#FFFFFF"));

                gg_out.setBackground(getResources().getDrawable( R.drawable.graph_frame ));


                gg_out.setPadding(4,4,4,4);
                if(cc>0)gg_in.setBackgroundColor(Color.parseColor(color[cc-1]));
                else gg_in.setBackgroundColor(Color.parseColor("#FFFFFF"));

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

                c.moveToNext();

            }catch (Exception e){

                Toast.makeText(this,"error: "+e.toString(),Toast.LENGTH_LONG);
            }
        }
        c.close();

        new AlertDialog.Builder(graph.this)
                .setTitle("衛生棉使用紀錄")
                .setView(v)
                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
    }
