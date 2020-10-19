package flag.com.gooood1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class men_sym extends AppCompatActivity {
    String [][] qestion1={/*{"經期正常","經期提前","經期延後","經來先後無定期"},*/{"經期長度正常","月經經期過長","月經經期過短"},{"月經週期正常","月經先期","月經後期","月經先後不定期"},
            {"月經色淡紅","月經色紅","月經色深紅","月經色紫","月經色黑"},{"月經質地正常","月經質稀","月經質濃"},{"月經量正常","月經量多","月經量少","月經量或多或少","點滴性出血"},
            {"無挾帶物","月經挾血塊","月經挾黏液","月經挾血水"},{"月經氣味正常","月經味臭"},
            {"白帶量正常","白帶量多","白帶量少","陰道乾燥"},{"白帶質正常","白帶質稀","白帶質濃"},{"白帶正常無色","白帶色白","白帶色黃"},{"白帶味正常","白帶味臭"},
            {"情緒狀態正常","煩躁憤怒","悲傷憂鬱","心慌","失眠","疲憊嗜睡","少氣懶言","恍惚呆滯"},
            /*全身症狀*/{"無發熱","全身發熱","低熱","潮熱","手足心熱","冒汗"},{"無寒冷","畏寒"},{"無頭部症狀","頭痛","頭暈目眩","耳鳴"},{"無胸部症狀","心悸","乳房脹痛","胸脹痛","脅脹痛"},
            {"肢體狀態正常","四肢痠痛","四肢麻木","四肢無力","四肢腫脹","四肢寒冷","下肢痠痛無力","下肢腫脹"},{"臉部狀態正常","面色紅","面色黃","面色白","面色暗","面目浮腫"},
            {"無胃腹部症狀","胃悶脹","食欲低落","嘔吐","腹脹痛","小腹脹痛","小腹空墜","小腹冷","小腹灼熱"},{"口腔狀態正常","口腔破洞","舌頭破洞","口臭","口苦","口乾渴少"},
            {"喉嚨狀態正常","喉嚨乾渴","痰量多","痰量少","咳嗽","咳嗽挾痰","咳嗽挾血"},{"小便量正常","小便量多","小便量少"},{"大便量正常","大便量少","大便量多"},
            /*舌診*/{"舌下脈顏色紫色","舌尖顏色深紅","舌尖顏色紅","舌體顏色淡白色","舌體顏色淡紅色","舌體顏色深紅色","舌體顏色紅色","舌體顏色紫色","舌體顏色青色","舌體顏色黑色"},
            {"舌苔顏色灰","舌苔顏色白","舌苔顏色黃","舌苔顏色黑"},{"舌苔厚度厚","舌苔厚度薄"},{"舌苔質地浮貼","舌苔質地粗糙","舌苔質地緊貼","舌苔質地腐垢","舌苔質地黏膩"},
            /*脈診*/{"脈利度滑","脈利度澀"},{"脈力度弱","脈力度強"},{"脈寬度寬","脈寬度細"},{"脈止率亂","脈止率定"},{"脈緊度散","脈緊度緊"},
            {"脈速度快","脈速度慢","脈速度疾"},{"脈部位中","脈部位伏","脈部位沉","脈部位浮"},{"脈長度短","脈長度長"},{"脈幅異常", "脈形中空"}};
    String [] qq1={/*"經期",*/"經期長度","月經週期","月經顏色","月經質地","月經量","挾帶物","月經氣味","帶下量","帶下質地","帶下顏色","帶下氣味","情緒狀態",
            /*全身*/"發熱","寒冷","頭部症狀","胸部症狀","肢體狀態","臉部狀態","胃腹部狀態","口腔狀態","喉嚨狀態","小便量","大便量",
            /*舌診*/"舌體顏色","舌苔顏色","舌苔厚度","舌苔質地",
            /*脈診*/ "脈利度","脈力度","脈寬度","脈止率","脈緊度","脈速度","脈部位","脈長度","其他"};
    int [] isSingle={/*婦科症狀*/1,1,1,1,1,1,1,1,1,1,1,0,/*全身症狀*/1,1,0,0,0,0,0,0,0,1,1,/*舌診*/1,1,1,1,/*脈診症狀*/1,1,1,1,1,1,1,1,0};
    int [] normal  ={/*婦科症狀*/1,1,0,0,0,0,0,1,1,1,1,1,/*全身症狀*/1,1,1,1,1,1,1,1,1,1,1,/*舌診*/0,0,0,0,/*脈診症狀*/0,0,0,0,0,0,0,0,0};
    CheckBox [][] qq1_v;
    String table="men_sym";
    private SQLiteDatabase db;
    private StdDBHelper dbHelper;
    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_men_sym);
        dbHelper = new StdDBHelper(this);
        db =dbHelper.getWritableDatabase();//開啟資料庫
        qq1_v=new CheckBox[qestion1.length][];
        for(int i=0;i<qestion1.length;i++){
            qq1_v[i]=new CheckBox[qestion1[i].length];
        }
        produce_question1();
        for(int i=0;i<qestion1.length;i++){
            if(normal[i]==1) {
                qq1_v[i][0].setChecked(true);
                if (isSingle[i]== 0) {
                    final int I = i;
                    qq1_v[i][0].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            ch_check(I, qq1_v[I][0]);
                        }
                    });
                    for (int j = 1; j < qq1_v[i].length; j++) {
                        qq1_v[i][j].setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                qq1_v[I][0].setChecked(false);
                            }
                        });
                    }
                }
            }
        }
    }
    public void goto_sym_search(View view){
        Intent intent = new Intent();
        intent.setClass(men_sym.this, sym_search.class);
        startActivity(intent);
        men_sym.this.finish();
    }
    public void produce_question1(){
        LinearLayout LL=(LinearLayout)findViewById(R.id.L1);
        LinearLayout L= new LinearLayout(this);
        L.setOrientation(LinearLayout.VERTICAL);
        LL.addView(L, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        //L.setBackgroundColor(Color.parseColor("#444444"));
        for(int i=0;i<qestion1.length;i++){
            LinearLayout Q= new LinearLayout(this);
            L.addView(Q,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView tv=new TextView(this);
            if(isSingle[i]==1)tv.setText(qq1[i]+"(單選)");
            else tv.setText(qq1[i]+"(複選)");
            tv.setTextSize(18);
            tv.setTextColor(Color.parseColor("#FFFFFF"));
            Q.addView(tv);
            Q.setGravity(Gravity.CENTER);
            Q.setBackgroundColor(Color.parseColor("#75FF3D00"));

            LinearLayout Lout= new LinearLayout(this);
            L.addView(Lout,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            Lout.setPadding(5,5,5,5);
            LinearLayout Lin= new LinearLayout(this);
            Lout.addView(Lin,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            Lin.setOrientation(LinearLayout.HORIZONTAL);
            int t=0;
            for(int j=0;j<qestion1[i].length;j++){
                CheckBox ch=new CheckBox(this);
                ch.setText(qestion1[i][j]);
                qq1_v[i][j]=ch;
                final CheckBox ch1=ch;
                final int I=i;
                if(isSingle[i]==1) {
                    ch1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            ch_check(I, ch1);
                        }
                    });
                }
                if(t>2){
                    Lout= new LinearLayout(this);
                    L.addView(Lout,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    Lout.setPadding(5,5,5,5);
                    Lin= new LinearLayout(this);
                    Lout.addView(Lin,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    Lin.setOrientation(LinearLayout.HORIZONTAL);
                    t=0;
                }
                Lin.addView(ch);
                t++;
            }
            //Lout.setBackgroundColor(Color.parseColor("#444444"));
        }
    }

    public void count(View view){
        try {
            Cursor c = db.rawQuery("select * from " + table, null);
            String str = "";
            c.moveToFirst();
            //AA [] x = new AA[c.getCount()];

            int t = c.getCount();
            int [][] num=new int[t][5];
            int [][] total=new int[t][5];
            float [][] score=new float[t][5];
            float []T_score=new float[t];
            String [] name=new String[t];

           for (int i = 0; i <t; i++) {
               for(int j=0;j<5;j++) {
                   num[i][j] = 0;
                   score[i][j] = 0;
                   name[i] = "";
               }
           }

                 c.moveToFirst();
                for (int i = 0; i < t; i++) {
                    name[i] = c.getString(0);
                    for (int l = 0; l < 5; l++) {
                        String[] tmp = c.getString(l+1).split("。");
                        if(c.getString(l+1).equals("NULL"))total[i][l] =0;
                        else total[i][l] = tmp.length;
                        for (int j = 0; j < qestion1.length; j++) {
                            for (int k = 0; k < qestion1[j].length; k++) {
                                if (qq1_v[j][k].isChecked()) {
                                    //Toast.makeText(this,"in!!!",Toast.LENGTH_SHORT).show();
                                    Cursor cc = db.rawQuery("select A"+Integer.toString(l+1)+" from " + table + " where name = '" + name[i]+ "'" + " and A" + Integer.toString(l+1) + " like '%" + qestion1[j][k] + "%'", null);
                                    //str+=qestion1[j][k]+":"+c.getString(5);
                                    cc.moveToFirst();
                                    cc.moveToFirst();
                                    if (cc.getCount() > 0) {
                                        num[i][l]++;
                                    }
                                    cc.close();
                                    break;
                                }
                            }
                        }
                }
                    c.moveToNext();
            }
            c.close();
            String ans = "";
            for (int i = 0; i < t; i++) {
                float ex=1;
                T_score[i]=0;
                float [] weight={(float)0.1,(float)0.1,(float)0.2,(float)0.2,(float)0.4};
                for (int j = 0; j < 5; j++) {
                    if(total[i][j]!=0){
                        if(num[i][j]==0)score[i][j] =0;
                        else if(num[i][j]==total[i][j])score[i][j] =1;
                        else {
                            float A1=(float)1/(float)total[i][j];
                            int A2_1=2 << num[i][j];
                            float A2=(float)(A2_1-1)/(float)A2_1;
                            float A3=(float)(total[i][j]-1)/(float)total[i][j];
                            score[i][j] =A1+A2*A3;
                            //score[i][j] = (float) num[i][j] / (float) total[i][j];
                        }
                    }
                    else {
                        score[i][j] =0;
                        ex-=weight[j];
                    }
                    T_score[i]+=score[i][j]*weight[j];
                }
                T_score[i]=T_score[i]/ex;
            }
            //sort(t,name,T_score,total,num,score);
            for(int i=0;i<t;i++){
                for(int j=0;j<t-1;j++){
                    if(T_score[j]<T_score[j+1]){
                        String tmp_name=name[j];
                        name[j]=name[j+1];
                        name[j+1]=tmp_name;
                        float  tmp_T_score=T_score[j];
                        T_score[j]=T_score[j+1];
                        T_score[j+1]=tmp_T_score;
                        int [] tmp_total=total[j];
                        total[j]=total[j+1];
                        total[j+1]=tmp_total;
                        int [] tmp_num=num[j];
                        num[j]=num[j+1];
                        num[j+1]=tmp_num;
                        float [] tmp_score=score[j];
                        score[j]=score[j+1];
                        score[j+1]=tmp_score;
                    }
                }
            }


            for (int i = 0; i < t; i++) {
                for(int j=0;j<5;j++)ans+=num[i][j] + "/" + total[i][j] + "\t";
                ans +=  name[i] + ":" + T_score[i] + "\n";
            }
            new AlertDialog.Builder(men_sym.this)
                    .setIcon(R.drawable.ic_launcher_background)
                    .setTitle("辨證結果")
                    .setMessage(ans)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }catch (Exception e){
            new AlertDialog.Builder(men_sym.this)
                    .setIcon(R.drawable.ic_launcher_background)
                    .setTitle("Error")
                    .setMessage(e.toString())
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }
}
    public void ch_check(int i,CheckBox ch){
            int flag=0;
           if(ch.isChecked())flag=1;
           for(int k=0;k<qq1_v[i].length;k++)qq1_v[i][k].setChecked(false);
           if(flag==1)ch.setChecked(true);
    }
}
