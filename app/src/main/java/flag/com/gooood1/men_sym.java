package flag.com.gooood1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;

public class men_sym extends AppCompatActivity {
    String [][] qestion1={/*{"經期正常","經期提前","經期延後","經來先後無定期"},*/{"經期長度正常","月經經期過長","月經經期過短"},{"月經週期正常","月經先期","月經後期","月經先後不定期"},
            {"月經色紅","月經色淡紅","月經色深紅","月經色紫","月經色黑"},{"月經質地正常","月經質稀","月經質濃"},{"月經量正常","月經量多","月經量少","月經量或多或少","點滴性出血"},
            {"無挾帶物","月經挾血塊","月經挾黏液","月經挾血水"},{"月經氣味正常","月經味臭"},
            {"白帶量正常","白帶量多","白帶量少","陰道乾燥"},{"白帶質正常","白帶質稀","白帶質濃"},{"白帶正常無色","白帶色白","白帶色黃"},{"白帶味正常","白帶味臭"},
            {"情緒狀態正常","煩躁憤怒","悲傷憂鬱","心慌","失眠","疲憊嗜睡","少氣懶言","恍惚呆滯"},
            /*全身症狀*/{"無發熱","全身發熱","低熱","潮熱","手足心熱","冒汗"},{"無寒冷","畏寒"},{"無頭部症狀","頭痛","頭暈目眩","耳鳴"},{"無胸部症狀","心悸","乳房脹痛","胸脹痛","脅脹痛"},
            {"肢體狀態正常","四肢痠痛","四肢麻木","四肢無力","四肢腫脹","四肢寒冷","下肢痠痛無力","下肢腫脹"},{"臉部狀態正常","面色紅","面色黃","面色白","面色暗","面目浮腫"},
            {"無胃腹部症狀","胃悶脹","食欲低落","嘔吐","腹脹痛","小腹脹痛","小腹空墜","小腹冷","小腹灼熱"},{"口腔狀態正常","口腔破洞","舌頭破洞","口臭","口苦","口乾渴少"},
            {"喉嚨狀態正常","喉嚨乾渴","痰量多","痰量少","咳嗽","咳嗽挾痰","咳嗽挾血"},{"小便量正常","小便量多","小便量少"},{"大便量正常","大便量少","大便量多"},
            /*舌診*/{"舌下脈顏色紫色","舌尖顏色深紅","舌尖顏色紅","舌體顏色淡白色","舌體顏色淡紅色","舌體顏色深紅色","舌體顏色紅色","舌體顏色紫色","舌體顏色青色","舌體顏色黑色"},
            {"舌苔顏色白","舌苔顏色灰","舌苔顏色黃","舌苔顏色黑"},{"舌苔厚度薄","舌苔厚度厚"},{"舌苔質地正常","舌苔質地浮貼","舌苔質地粗糙","舌苔質地緊貼","舌苔質地腐垢","舌苔質地黏膩"},
            /*脈診*/{"脈利度正常","脈利度滑","脈利度澀"},{"脈力度正常","脈力度弱","脈力度強"},{"脈寬度正常","脈寬度寬","脈寬度細"},{"脈止率亂","脈止率定"},{"脈緊度正常","脈緊度散","脈緊度緊"},
            {"脈速度正常","脈速度快","脈速度慢","脈速度疾"},{"脈部位正常","脈部位中","脈部位伏","脈部位沉","脈部位浮"},{"脈長度正常","脈長度短","脈長度長"},{"脈幅異常", "脈形中空"}};
    String [] qq1={/*"經期",*/"經期長度","月經週期","月經顏色","月經質地","月經量","挾帶物","月經氣味","帶下量","帶下質地","帶下顏色","帶下氣味","情緒狀態",
            /*全身*/"發熱","寒冷","頭部症狀","胸部症狀","肢體狀態","臉部狀態","胃腹部狀態","口腔狀態","喉嚨狀態","小便量","大便量",
            /*舌診*/"舌體顏色","舌苔顏色","舌苔厚度","舌苔質地",
            /*脈診*/ "脈利度","脈力度","脈寬度","脈止率","脈緊度","脈速度","脈部位","脈長度","其他"};
    int [] isSingle={/*婦科症狀*/1,1,1,1,1,1,1,1,1,1,1,0,/*全身症狀*/1,1,0,0,0,0,0,0,0,1,1,/*舌診*/1,1,1,1,/*脈診症狀*/1,1,1,1,1,1,1,1,0};
    int [] normal  ={/*婦科症狀*/1,1,1,1,1,1,1,1,1,1,1,1,/*全身症狀*/1,1,1,1,1,1,1,1,1,1,1,/*舌診*/0,1,1,1,/*脈診症狀*/1,1,1,0,1,1,1,1,0};
    CheckBox [][] qq1_v;
    String title;
    String table="men_sym";
    private SQLiteDatabase db;
    private StdDBHelper dbHelper;
    int ID=-1;
    int isch=0;
    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_men_sym);
        dbHelper = new StdDBHelper(this);
        db =dbHelper.getWritableDatabase();//開啟資料庫

        // 取得前一個Activity傳過來的資料
        Bundle bundle = this.getIntent().getExtras();
        // 將取得的Bundle資料設定
        title = bundle.getString("title");
        change(title);

        Cursor history=db.rawQuery("Select * from history",null);
        history.moveToFirst();
        ID=history.getCount()+1;

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
        if(isch==1)note_store();
        else{
            Intent intent = new Intent();
            intent.setClass(men_sym.this, sym_search.class);
            startActivity(intent);
            men_sym.this.finish();
        }
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
            isch=1;
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
                Switch isbigger=(Switch)findViewById(R.id.switch1);
                for (int j = 0; j < 5; j++) {
                    if (total[i][j] != 0) {
                        if (num[i][j] == 0) score[i][j] = 0;
                        else if (num[i][j] == total[i][j]) score[i][j] = 1;
                        else {
                            if(isbigger.isChecked()){
                                float A1 = (float) 1 / (float) total[i][j];
                                int A2_1 = 2 << num[i][j];
                                float A2 = (float) (A2_1 - 1) / (float) A2_1;
                                float A3 = (float) (total[i][j] - 1) / (float) total[i][j];
                                score[i][j] = A1 + A2 * A3;
                            }
                            else score[i][j] = (float) num[i][j] / (float) total[i][j];
                        }
                    } else {
                        score[i][j] = 0;
                        ex -= weight[j];
                    }
                    T_score[i] += score[i][j] * weight[j];
                }
                T_score[i] = T_score[i] / ex;
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
                /*for(int j=0;j<5;j++)ans+=num[i][j] + "/" + total[i][j] + "\t";*/
                ans += Integer.toString(i+1)+". "+name[i] + ":" + T_score[i] + "\n";
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

            Store(ans);
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
    public  void change(String title) {
        if(title.equals("乳病")) {
            TextView Title=(TextView)findViewById(R.id.Title);
            Title.setText("乳病辨證");
            String[][] qestion2 = {/*婦科症狀*/{"乳房狀態正常", "乳房脹痛", "乳房柔軟", "乳房發育不良", "乳房豐滿", "乳房有腫塊", "乳房硬"}, {"乳頭狀態正常", "乳頭脹痛", "乳頭流紅色血", "乳頭流蛋黃血水"},
                    {"乳汁狀態正常", "乳汁稀少", "乳汁驟減", "乳汁全無", "乳汁驟減", "自覺乳汁流出", "自覺乳汁點滴流出"}, {"乳汁清稀", "乳汁稠"}, {"乳汁色正常", "乳汁色白", "乳汁清澈", "乳汁色黃"},
                    {"月經正常", "月經量少", "月經量多", "月經提前", "月經不調", "月經先後不定"}, {"無惡露", "惡露多", "惡露不絕"}, {"無其他症狀", "不孕", "胎不長", "流產", "痛經"},
                    /*全身症狀*/{"無發熱", "手足心熱"}, {"無頭部症狀", "頭痛", "頭暈", "目乾", "目炫"}, {"無脅部症狀", "脅脹", "脅痛"}, {"無胸部症狀", "胸悶", "胸脹", "胸痛"},
                    {"無少腹症狀", "少腹脹", "少腹痛"}, {"無小腹症狀", "小腹脹", "小腹痛"}, {"無腰臀膝部症狀", "腰酸", "腰無力", "膝酸"}, {"肢體狀態正常", "下肢無力", "下肢酸", "四肢無力"},
                    {"臉部狀態正常", "面色暗", "面色白"}, {"口腔狀態正常", "口乾", "口苦", "喉乾", "痰量多", "咽阻"}, {"情緒狀態正常", "情緒憂鬱", "情緒多嘆", "情緒煩躁", "情緒憤怒", "情緒神疲"},
                    {"小便色清", "小便色紅", "小便色黃"}, {"小便量正常", "小便量少"}, {"大便次數正常", "大便次少", "大便次多"}, {"大便量正常", "大便量多", "大便量少"}, {"大便質正常", "大便質稀"},
                    {"飲食狀態正常", "食後困倦", "食欲低落"}, {"無全身其他症狀", "呼吸微弱", "全身肥胖", "大便次少按壓時加重"},
                    /*舌診*/{"舌下脈顏色紫色", "舌尖顏色深紅", "舌尖顏色紅", "舌體顏色淡白色", "舌體顏色淡紅色", "舌體顏色深紅色", "舌體顏色紅色", "舌體顏色紫色", "舌體顏色青色", "舌體顏色黑色"},
                    {"舌苔顏色白", "舌苔顏色灰", "舌苔顏色黃", "舌苔顏色黑"}, {"舌苔厚度薄", "舌苔厚度厚"}, {"舌苔質地正常", "舌苔質地浮貼", "舌苔質地粗糙", "舌苔質地緊貼", "舌苔質地腐垢", "舌苔質地黏膩"},
                    /*脈診*/{"脈利度正常", "脈利度滑", "脈利度澀"}, {"脈力度正常", "脈力度弱", "脈力度強"}, {"脈寬度正常", "脈寬度寬", "脈寬度細"}, {"脈止率亂", "脈止率定"}, {"脈緊度正常", "脈緊度散", "脈緊度緊"},
                    {"脈速度正常", "脈速度快", "脈速度慢", "脈速度疾"}, {"脈部位正常", "脈部位中", "脈部位伏", "脈部位沉", "脈部位浮"}, {"脈長度正常", "脈長度短", "脈長度長"}, {"脈幅異常", "脈形中空"}
            };
            String[] qq2 = {/*婦科症狀*/"乳房狀態", "乳頭狀態", "乳汁狀態", "乳汁質地", "乳汁顏色", "月經狀態", "惡露量", "婦科其他症狀",
                    /*全身*/"發熱", "頭部症狀", "脅部症狀", "胸部症狀", "少腹症狀", "小腹症狀", "腰臀膝部症狀", "肢體狀態", "臉部狀態", "口腔喉嚨狀態", "情緒狀態", "小便顏色", "小便量", "大便次數", "大便量", "大便質地", "飲食狀態", "全身其他症狀",
                    /*舌診*/"舌體顏色", "舌苔顏色", "舌苔厚度", "舌苔質地",
                    /*脈診*/ "脈利度", "脈力度", "脈寬度", "脈止率", "脈緊度", "脈速度", "脈部位", "脈長度", "其他"};
            int[] isSingle2 = {/*婦科症狀*/0, 0, 0, 1, 0, 0, 0, 0,/*全身症狀*/1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0,/*舌診*/1, 1, 1, 1,/*脈診症狀*/1, 1, 1, 1, 1, 1, 1, 1, 0};
            int[] normal2 = {/*婦科症狀*/1, 1, 1, 0, 1, 1, 1, 1,/*全身症狀*/1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,/*舌診*/0, 1, 1, 1,/*脈診症狀*/1, 1, 1, 0, 1, 1, 1, 1, 0};
            table="breast_sym";
            qestion1=qestion2;
            qq1=qq2;
            isSingle=isSingle2;
            normal=normal2;
        }
        else if(title.equals("前陰疾病"))
        {
            TextView Title=(TextView)findViewById(R.id.Title);
            Title.setText("前陰疾病辨證");
            String [][] qestion2={/*婦科症狀*/{"陰戶無物","陰戶有物"},{"陰戶無異狀","陰戶腫","陰戶脹","陰戶疼","陰戶墜","陰戶重"},{"陰部狀態正常","陰部有灼熱感","陰部乾澀","陰部搔癢"},{"無陰吹","有陰吹"},{"無陰吹狀況","陰吹較劇","陰吹時斷時續","陰吹較輕"},{"帶下量正常","帶下量多"},{"帶下透明","帶下色白","帶下色黃","帶下血樣"},{"帶下清澈","帶下質稀","帶下質黏膩"},{"帶下無異味","帶下腥臭"},{"無陰痛","有陰痛"},{"外陰無異狀","外陰充血","外陰潮紅","外陰搔癢","外陰萎縮","外陰潰瘍","外陰皮膚變白","外陰脆薄","外陰增厚粗糙","外陰皮膚變粉","外陰乾燥","外陰澀痛","外陰有皺裂"},
                    {"陰道無異狀","陰道潮紅","陰道充血","陰道有潰傷","陰道口狹窄","陰有少量黃水","陰有乾澀灼熱感"},{"無婦科其他症狀","大陰唇變平","大陰唇上端黏連","小陰唇部分或全部消失","陰部皮膚黏膜變粗糙增厚","白帶增多","濕疹"},
                    /*全身症狀*/{"無發熱","手足心熱","發熱"},{"無頭部症狀","頭暈","目炫","目乾","耳鳴","頭昏"},{"無胸部症狀","胸悶","脅痛"},
                    {"無腰臀膝部症狀","腰酸","腰痛","膝酸","膝痛","腰冷"},{"肢體狀態正常","下肢無力","下肢浮腫","四肢無力"},
                    {"臉部狀態正常","面色暗","面浮腫"},{"口腔狀態正常","口乾","口苦","口膩","喉乾","痰量多"},
                    {"情緒狀態正常","情緒憂鬱","情緒多嘆","情緒煩躁","情緒憤怒","情緒神疲"},{"大小便狀況正常","小便不暢","大便不暢"},
                    {"小便色清","小便色黃"},{"小便量正常","小便量少"},{"小便次數正常","小便次少","小便次多"},
                    {"大便次數正常","大便次少","大便次多"},{"大便質正常","大便質硬","大便質稀"},{"飲食狀態正常","食後困倦","食後腹脹"},{"意識狀況正常","意識懶言","意識呆滯"},{"胃腹部狀況正常","腹脹","胃脹","胃悶"},{"無心部症狀","心悸"},{"無特別全身狀況","全身無力","全身冷","全身酸","全身痛","全身癢"},{"無全身其他症狀","呼吸微弱","全身肥胖","大便次少按壓時加重","易醒","妊娠心煩","月經煩躁","難眠","嗜睡","渴不欲飲","渴而少飲","自汗","寒戰","惡寒","畏寒","關節痛"},
                    /*舌診*/{"舌下脈顏色紫色","舌尖顏色深紅","舌尖顏色紅","舌體顏色淡白色","舌體顏色淡紅色","舌體顏色深紅色","舌體顏色紅色","舌體顏色紫色","舌體顏色青色","舌體顏色黑色"},
                    {"舌苔顏色白","舌苔顏色灰","舌苔顏色黃","舌苔顏色黑"},{"舌苔厚度薄","舌苔厚度厚"},{"舌苔質地正常","舌苔質地浮貼","舌苔質地粗糙","舌苔質地緊貼","舌苔質地腐垢","舌苔質地黏膩"},
                    /*脈診*/{"脈利度正常","脈利度滑","脈利度澀"},{"脈力度正常","脈力度弱","脈力度強"},{"脈寬度正常","脈寬度寬","脈寬度細"},{"脈止率亂","脈止率定"},{"脈緊度正常","脈緊度散","脈緊度緊"},
                    {"脈速度正常","脈速度快","脈速度慢","脈速度疾"},{"脈部位正常","脈部位中","脈部位伏","脈部位沉","脈部位浮"},{"脈長度正常","脈長度短","脈長度長"},{"脈幅異常", "脈形中空"}};
            String [] qq2={/*婦科症狀*/"陰戶有無物","陰戶狀態","陰部狀態","陰吹","陰吹狀況","帶下量","帶下顏色","帶下質地","帶下氣味","陰痛","外陰狀況","陰道狀況","婦科其他症狀",
                    /*全身*/"發熱","頭部症狀","胸部症狀","腰臀膝部症狀","肢體狀態","臉部狀態","口腔喉嚨狀態","情緒狀態","大小便狀況","小便顏色","小便量","小便次數","大便次數","大便質地","飲食狀態","意識狀況","胃腹部症狀","心部症狀","全身狀況","全身其他症狀",
                    /*舌診*/"舌體顏色","舌苔顏色","舌苔厚度","舌苔質地",
                    /*脈診*/ "脈利度","脈力度","脈寬度","脈止率","脈緊度","脈速度","脈部位","脈長度","其他"};
             int [] isSingle2={/*婦科症狀*/1,0,0,1,0,1,0,1,1,1,0,0,0,/*全身症狀*/0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,0,0,/*舌診*/1,1,1,1,/*脈診症狀*/1,1,1,1,1,1,1,1,0};
             int [] normal2  ={/*婦科症狀*/1,1,1,1,1,1,1,1,1,1,1,1,1,/*全身症狀*/1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,/*舌診*/0,1,1,1,/*脈診症狀*/1,1,1,0,1,1,1,1,0};
            table="geni_sym";
            qestion1=qestion2;
            qq1=qq2;
            isSingle=isSingle2;
            normal=normal2;
        }
        else if(title.equals("產後病"))
        {
            TextView Title=(TextView)findViewById(R.id.Title);
            Title.setText("產後病辨證");
            String [][] qestion2={/*婦科症狀*/{"惡露無其他狀況","惡露不下","惡露過期不止","惡露澀滯不暢","惡露有塊","惡露淋漓","惡露不絕"},{"惡露色正常","惡露色紫","惡露色暗","惡露色紅","惡露色淡"},{"惡露量正常","惡露量多","惡露量少","惡露量時多時少"},{"惡露無臭氣","惡露有臭穢氣"},{"惡露質正常","惡露質黏稠"},{"無胃腹部症狀","少腹脹","少腹痛","小腹脹","小腹痛","小腹空墜","小腹急"},{"無按壓偏好","少腹拒按","小腹喜按","小腹拒按"},{"無腰背部症狀","腰痛","背痛","腰痠","背痠","腰乏力","背強直"},{"無腿足部症狀","腿乏力","足跟痛"},{"無發熱","發熱惡寒"},{"無流血","流血特多","出血過多"},{"流血質地正常","流血質稀"},
                    {"無頭部症狀","頭強直","頭強痛","頭汗出","項強直","項強痛"},{"肢體狀態正常","四肢抽搐","肢體痠楚","肢體麻木","肢體腫脹","下肢疼痛","肢體重著","關節疼痛"},{"無心部症狀","心悸","心下急滿"},{"無口腔牙齒症狀","牙關緊閉","口角搐動"},{"小便狀況正常","小便不通","小便頻數","失禁","夜間遺尿","小便失約而自遺","排尿淋漓夾血絲","小便溺黃"},{"無婦科其他症狀","突然暈眩","驟然發痙","缺乳","乳汁不足","汗出","尿道灼熱","澀痛"},
                    /*全身症狀*/{"臉部狀態正常","面色白","面色紫","面色暗","面色黃","面色青","面色紅","面浮腫"},{"口腔狀態正常","口噤","口乾","喉乾","口苦"},{"情緒狀態正常","情緒神疲","情緒痛苦","情緒煩躁"},{"無腰臀膝部症狀","腰酸","腰無力","膝酸"},{"肢體狀態正常","四肢冷","四肢無力","下肢無力","下肢畏寒"},{"無頭部症狀","頭昏","頭暈","目瞑","目花","耳鳴"},{"無發熱","手足心熱","產後發熱"},{"無胸部症狀","胸悶","脅脹","脅痛","胸脹","胸痛"},{"無胸痛減輕因子","溫暖時減輕","按壓時減輕"},{"無脅痛減輕因子","按壓時減輕"},{"無脅痛加重因子","按壓時加重","天氣變化時加重"},{"無脅脹加重因子","按壓時加重"},{"呼吸狀況正常","呼吸急促","呼吸微弱"},{"意識狀況正常","意識昏迷","意識懶言","意識呆滯"},{"無心部症狀","心悸"},{"無其他全身症狀","冷汗","腹脹","腹痛","食欲低落","大便質硬","大便不暢","惡風","畏風","全身冷","畏寒","寒戰","小便量多","全身痛","小便次多"},
                    /*舌診*/{"舌下脈顏色紫色","舌尖顏色深紅","舌尖顏色紅","舌體顏色淡白色","舌體顏色淡紅色","舌體顏色深紅色","舌體顏色紅色","舌體顏色紫色","舌體顏色青色","舌體顏色黑色"},
                    {"舌苔顏色白","舌苔顏色灰","舌苔顏色黃","舌苔顏色黑"},{"舌苔厚度薄","舌苔厚度厚"},{"舌苔質地正常","舌苔質地浮貼","舌苔質地粗糙","舌苔質地緊貼","舌苔質地腐垢","舌苔質地黏膩"},
                    /*脈診*/{"脈利度正常","脈利度滑","脈利度澀"},{"脈力度正常","脈力度弱","脈力度強"},{"脈寬度正常","脈寬度寬","脈寬度細"},{"脈止率亂","脈止率定"},{"脈緊度正常","脈緊度散","脈緊度緊"},
                    {"脈速度正常","脈速度快","脈速度慢","脈速度疾"},{"脈部位正常","脈部位中","脈部位伏","脈部位沉","脈部位浮"},{"脈長度正常","脈長度短","脈長度長"},{"脈幅異常", "脈形中空"}};
            String [] qq2={/*婦科症狀*/"惡露其他狀況","惡露顏色","惡露量","惡露氣味","惡露質地","胃腹部症狀","胃腹部按壓","腰背部症狀","腿足部症狀","發熱","流血量","流血質地","頭部症狀","肢體狀態","心部症狀","口腔牙齒症狀","小便狀況","婦科其他症狀",
                    /*全身*/"臉部狀態","口腔喉嚨狀態","情緒狀態","腰臀部症狀","肢體狀態","頭部症狀","發熱","胸部症狀","胸痛減輕因子","脅痛減輕因子","脅痛加重因子","脅脹加重因子","呼吸狀況","意識狀況","心部症狀","其他全身症狀",
                    /*舌診*/"舌體顏色","舌苔顏色","舌苔厚度","舌苔質地",
                    /*脈診*/ "脈利度","脈力度","脈寬度","脈止率","脈緊度","脈速度","脈部位","脈長度","其他"};
            int [] isSingle2={/*婦科症狀*/0,0,1,1,1,0,0,0,0,1,0,1,0,0,0,0,0,0,/*全身症狀*/0,0,0,0,0,0,0,0,0,1,0,1,0,0,1,0,/*舌診*/1,1,1,1,/*脈診症狀*/1,1,1,1,1,1,1,1,0};
            int [] normal2  ={/*婦科症狀*/1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,/*全身症狀*/1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,/*舌診*/0,1,1,1,/*脈診症狀*/1,1,1,0,1,1,1,1,0};
            table="post_sym";
            qestion1=qestion2;
            qq1=qq2;
            isSingle=isSingle2;
            normal=normal2;
        }
        else if(title.equals("妊娠病")){
            TextView Title=(TextView)findViewById(R.id.Title);
            Title.setText("妊娠病辨證");
            String [][] qestion2={	/*婦科症狀*/
                    {"陰道無出血","陰道出血色淡紅","陰道出血色紅","陰道出血色深紅","陰道出血色紫","陰道出血色黑"},
                    {"陰道無出血","陰道出血質正常","陰道出血質稀","陰道出血質濃","陰道出血挾塊"},
                    {"陰道無出血","陰道出血量正常","陰道出血量多","陰道出血量少"},
                    {"懷孕狀況正常","屢次墮胎","難受孕","懷孕期間有外傷","胎塊排出但陰道仍持續流血","久產不下","產程進度緩慢","胎動停止","胎死腹中"},
                    {"月經正常","月經初潮較晚","月經閉經","月經崩漏"},
                    {"月經週期正常","月經後期","月經先期","月經先後不定期"},
                    {"月經量正常","月經量多","月經量少"},
                    {"月經色淡紅","月經色紅","月經色紫","月經色黑"},
                    {"月經質正常","月經質濃","月經質稀"},
                    {"子宮頸狀態正常","子宮頸已擴張","胎盤組織堵塞於子宮頸口"},
                    {"子宮狀態正常","子宮較孕周小","子宮較停經週數小"},
                    {"無明顯宮縮或宮縮規律","宮縮時間短","宮縮間歇時間長","宮縮強而間歇不勻"},
                    {"腹形大小正常","腹形小於正常妊娠月份","腹中不再繼續長大"},
                    {"無嘔吐","噁心","嘔吐","吐清水","吐酸水"},
                    /*全身症狀*/
                    {"無胃部症狀","食欲低落","胃悶","胃脹","胃痛"},
                    {"無腰腹部症狀","腹痛","腹脹","腹冷","腰酸","腰痛","腰冷","腰無力","腰腹陣痛微弱","腰腹疼痛劇烈"},
                    {"無小腹症狀","小腹痛","小腹脹","小腹冷"},
                    {"無頭部症狀","頭暈目眩","頭脹","頭重","頭顫","頭痛","耳鳴"},
                    {"無胸部症狀","胸脹","胸悶","胸痛","脅脹","脅痛","呼吸微弱","呼吸急促","呼吸痰鳴","心悸"},
                    {"無四肢症狀","四肢浮腫","四肢抽搐","四肢冷","四肢無力","下肢無力","下肢酸","下肢冷","下肢浮腫"},
                    {"無膝足部症狀","膝腫脹","膝酸","膝痛","足腫脹","手足心熱"},
                    {"無全身症狀","全身浮腫","全身色黃","全身消瘦","畏寒","冷汗","大汗淋漓","潮熱","發熱"},
                    {"無面部症狀","面浮腫","面乾","面色青","面色白","面色黃","面色紅","面色紫","面色暗","面垢","面生斑"},
                    {"無口喉症狀","聲啞","喉乾","咳嗽無痰","咳嗽挾痰","口中淡膩","口乾","口苦","口渴","渴不欲飲","口臭"},
                    {"無痰","痰色質正常","痰挾血","痰色黃","痰質濃"},
                    {"情緒意識正常","煩躁憤怒","緊張心慌","疲憊嗜睡","多夢難眠","憂鬱多嘆","意識昏迷","意識懶言"},
                    {"無小便問題","小便不暢","小便疼痛","小便灼熱","小便失禁"},
                    {"小便量正常","小便量多","小便量少"},
                    {"小便色清","小便色淡黃","小便色黃","小便色紅","小便色棕"},
                    {"小便次數正常","小便次數多","小便次數少"},
                    {"大便質地正常","大便質稀","大便質硬"},
                    {"無大便問題","大便次數少","大便不盡"},
                    /*舌診*/
                    {"舌下脈顏色紫色","舌尖顏色深紅","舌尖顏色紅","舌體顏色淡白色","舌體顏色淡紅色","舌體顏色深紅色","舌體顏色紅色","舌體顏色紫色","舌體顏色青色","舌體顏色黑色"},
                    {"舌苔顏色白","舌苔顏色灰","舌苔顏色黃","舌苔顏色黑"},{"舌苔厚度薄","舌苔厚度厚"},{"舌苔質地正常","舌苔質地浮貼","舌苔質地粗糙","舌苔質地緊貼","舌苔質地腐垢","舌苔質地黏膩"},
                    /*脈診*/
                    {"脈利度正常","脈利度滑","脈利度澀"},{"脈力度正常","脈力度弱","脈力度強"},{"脈寬度正常","脈寬度寬","脈寬度細"},{"脈止率亂","脈止率定"},{"脈緊度正常","脈緊度散","脈緊度緊"},
                    {"脈速度正常","脈速度快","脈速度慢","脈速度疾"},{"脈部位正常","脈部位中","脈部位伏","脈部位沉","脈部位浮"},{"脈長度正常","脈長度短","脈長度長"},{"脈幅異常", "脈形中空"}
            };

             String [] qq2={	/*婦科症狀*/"陰道出血顏色","陰道出血質地","陰道出血量","懷孕狀況","月經狀況","月經週期","月經量","月經顏色","月經質地","子宮頸狀態","子宮狀態","子宮收縮時間","腹形大小","妊娠嘔吐狀況",
                    /*全身*/"胃部症狀","腰腹部症狀","小腹症狀","頭部症狀","呼吸及心胸部症狀","四肢症狀","膝足部症狀","全身症狀","面部症狀","口腔喉嚨症狀","痰狀況","情緒意識狀態","小便問題","小便量","小便顏色","小便次數","大便質地","大便問題",
                    /*舌診*/"舌體顏色","舌苔顏色","舌苔厚度","舌苔質地",
                    /*脈診*/"脈利度","脈力度","脈寬度","脈止率","脈緊度","脈速度","脈部位","脈長度","其他"};

             int [] isSingle2={/*婦科症狀*/0,0,1,0,0,1,1,0,1,0,0,0,0,0,/*全身症狀*/0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,1,0,/*舌診*/1,1,1,1,/*脈診症狀*/1,1,1,1,1,1,1,1,0};

             int [] normal2  ={/*婦科症狀*/0,0,0,1,1,1,1,0,1,1,1,0,1,1,/*全身症狀*/1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,/*舌診*/0,1,1,1,/*脈診症狀*/1,1,1,0,1,1,1,1,0};
            table="gest_sym";
            qestion1=qestion2;
            qq1=qq2;
            isSingle=isSingle2;
            normal=normal2;
        }
        else if(title.equals("月經病2")){
            TextView Title=(TextView)findViewById(R.id.Title);
            Title.setText("月經病2辨證");
            table="men_sym2";
        }

    }

    public void Store(String ans){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month =  calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date=Integer.toString(year);
        if(month+1<10)date+="/0"+Integer.toString(month+1);
        else date+="/"+Integer.toString(month+1);
        if(day<10)date+="/0"+Integer.toString(day);
        else date+="/"+Integer.toString(day);

        String sym="";
        for(int i=0;i<qq1.length;i++){
            int f=0;
            for(int j=0;j<qq1_v[i].length;j++){
                if(qq1_v[i][j].isChecked()&&!(normal[i]==1&&j==0)) {
                    if (f == 0) {
                        sym += qq1[i] + ": " + qestion1[i][j];
                        f = 1;
                    }
                    else{
                        sym+="、"+qestion1[i][j];
                    }
                }
            }
            if(f==1)sym+="。\n";
        }
        Switch isbigger=(Switch)findViewById(R.id.switch1) ;
        ContentValues cv = new ContentValues();
        cv.put("sym",sym);
        cv.put("ans",ans);
        cv.put("id",ID);
        if(isbigger.isChecked())cv.put("main",title+"_放大");
        else cv.put("main",title);
        cv.put("date",date);
        cv.put("note","");
        db.insert("history",null,cv);
        db.update("history",cv,"id='"+ID+"'",null);
    }

    public void note_store(){
        new AlertDialog.Builder(men_sym.this)
                .setTitle("Message")
                .setMessage("本次的辨證內容已儲存，請問你要為此次的辨證增加備註嗎?")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LayoutInflater inflater = LayoutInflater.from(men_sym.this);
                        final View v = inflater.inflate(R.layout.qanda, null);
                        final EditText et=v.findViewById(R.id.editText3);
                        new AlertDialog.Builder(men_sym.this)
                                .setTitle("請填寫備註內容")
                                .setView(v)
                                .setPositiveButton("送出", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ContentValues cv = new ContentValues();
                                        cv.put("id",ID);
                                        cv.put("note",et.getText().toString());
                                        db.insert("history",null,cv);
                                        db.update("history",cv,"id='"+ID+"'",null);

                                        Intent intent = new Intent();
                                        intent.setClass(men_sym.this, sym_search.class);
                                        startActivity(intent);
                                        men_sym.this.finish();

                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent();
                                        intent.setClass(men_sym.this, sym_search.class);
                                        startActivity(intent);
                                        men_sym.this.finish();
                                    }
                                })
                                .show();
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setClass(men_sym.this, sym_search.class);
                        startActivity(intent);
                        men_sym.this.finish();
                    }
                })
                .show();
    }

}
