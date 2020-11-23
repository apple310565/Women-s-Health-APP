package flag.com.gooood1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

public class home extends AppCompatActivity {
    int [] Mon = {0,31,59,90,120,151,181,212,243,273,304,334};
    int Y,M,D,peroid=29,flag=0,day1=0,day2=0,i_M,mense;
    String account,passWD;
    private SQLiteDatabase db;
    private StdDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //若是第一次登入沒有帳密，則切換成註冊頁面
        String line="";
        try{
            FileInputStream in = openFileInput("passwd.txt");
            byte[] data = new byte[128];
            in.read(data);
            in.close();
            line = new String(data);

            String[] name = line.split(",");
            String [] Date = name[3].split("/");
            Y=Integer.parseInt(Date[0]);
            M=Integer.parseInt(Date[1]);
            D=Integer.parseInt(Date[2]);
            peroid = Integer.parseInt(name[2]);
            flag=1;
            account=name[0];
            passWD=name[1];
            i_M=Integer.parseInt(name[4]);
        }
        catch (IOException e){
            new AlertDialog.Builder(home.this)
                    .setIcon(R.drawable.ic_launcher_background)
                    .setTitle("Message")
                    .setMessage("第一次使用請先設置帳密喔")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setClass(home.this, Main2Activity.class);
                            startActivity(intent);
                            home.this.finish();
                        }
                    })
                    .show();
        }
        if(flag==1){
            //建立SQLOHleper物件
            dbHelper = new StdDBHelper(this);
            db =dbHelper.getWritableDatabase();//開啟資料庫
            load_eat();
            load_acup();
            load_sport();
            load_habit();
            load_dayday();
            load_men_sym();
            load_breast_sym();
            load_geni_sym();
            load_post_sym();
            load_gest_sym();
            load_link();
            load_men_sym2();
            load_breast_sym2();
            load_geni_sym2();
            load_post_sym2();
            load_gest_sym2();
            load_prescription();
            //初始化

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month =  calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            day1 = Mon[month]+day;
            day2 = Mon[M-1]+D;
            int days = (year-Y)*365+(day1-day2);
            mense =peroid - (days%peroid);
            if(mense>=peroid)mense=mense-peroid;
            if(is_mense()==0)out_memse();
            else in_memse();
            TextView UserName = (TextView)findViewById(R.id.User);
            UserName.setText(account);
            //監聽
            LinearLayout mm = (LinearLayout)findViewById(R.id.MM);
            mm.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                   goto_M1_2();
                }
            });
            LinearLayout P = (LinearLayout)findViewById(R.id.person);
            P.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    goto_Person();
                }
            });
            LinearLayout H = (LinearLayout)findViewById(R.id.health);
            H.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    goto_M3();
                }
            });
            LinearLayout S = (LinearLayout)findViewById(R.id.search);
            S.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    goto_sym_search();
                }
            });

            try{
                String bb;
                FileInputStream in = openFileInput("PPP.txt");
                byte[] data = new byte[128];
                in.read(data);
                in.close();
                bb = new String(data);
                String[] tmp = bb.split(",");
                String select=tmp[1];

                FileOutputStream pout = null;
                String str="";
                int ppp=days%peroid;
                if(ppp<=5)str="1,";
                else if(ppp<=14)str="2,";
                else if(ppp<=21)str="3,";
                else str="4,";
                str+=select+",";
                //Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
                try {
                    pout = openFileOutput("PPP.txt",MODE_PRIVATE);
                    pout.write(str.getBytes());
                    pout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int is_mense(){
        if(day1>=day2){
            if(i_M==-1)return 1;
            else if(i_M>=(day1-day2))return 1;
        }
        return 0;
    }

    public void in_memse(){
        Button bt1 = (Button)findViewById(R.id.button3);
        bt1.setText("經期中");
        TextView day = (TextView) findViewById(R.id.textView2);
        int days=day1-day2;
        day.setText(Integer.toString(days+1));
    }

    public void out_memse(){
        Button bt1 = (Button) findViewById(R.id.button3);
        bt1.setText("經期倒數");
        TextView day = (TextView) findViewById(R.id.textView2);
        day.setText(Integer.toString(mense));
        if(day1-day2>peroid&&day1-day2<peroid+15){
            bt1.setText("經期晚到");
            day.setText(Integer.toString(day1-day2-peroid));
        }
    }

    public void goto_M1(View view){
        Intent intent = new Intent();
        intent.setClass(home.this, MainActivity.class);
        startActivity(intent);
        home.this.finish();
    }
    public void goto_M1_2(){
        Intent intent = new Intent();
        intent.setClass(home.this, MainActivity.class);
        startActivity(intent);
        home.this.finish();
    }
    public void goto_M3(){
        Intent intent = new Intent();
        intent.setClass(home.this, Main3Activity.class);
        startActivity(intent);
        home.this.finish();
    }
    public void goto_Person(){
        Intent intent = new Intent();
        intent.setClass(home.this, person.class);
        startActivity(intent);
        home.this.finish();
    }
    public void goto_sym_search(){
        new AlertDialog.Builder(home.this)
                .setTitle("請問要使用哪種證候")
                .setPositiveButton("內科證候", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setClass(home.this, sym_search.class);
                        startActivity(intent);
                        home.this.finish();
                    }
                })
                .setNegativeButton("婦科證候", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setClass(home.this, sym_search2.class);
                        startActivity(intent);
                        home.this.finish();
                    }
                })
                .show();
    }
    public void load_eat(){
        try {
            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open("EAT.csv") );
            //Toast.makeText(this,"找到檔案了",Toast.LENGTH_SHORT).show();
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            while((line = bufReader.readLine()) != null) {
                String[] Eat = line.split(",");
                    String name=Eat[0];
                    String effect=Eat[1];
                    String who =Eat[2];
                    String method=Eat[3];
                    String main1 =Eat[4];
                    String main2 =Eat[5];
                    String subset=Eat[6];
                    int P = Integer.parseInt(Eat[7]);
                    int D = Integer.parseInt(Eat[8]);
                    String Source=Eat[9];
                    int f=0;
                    ContentValues cv = new ContentValues();
                    cv.put("_name",name);
                    cv.put("effect",effect);
                    cv.put("who",who);
                    cv.put("method",method);
                    cv.put("subset",subset);
                    cv.put("main",main1);
                    cv.put("main2",main2);
                    cv.put("favor",f);
                    cv.put("P",P);
                    cv.put("D",D);
                    cv.put("Source",Source);
                    db.insert("EAT",null,cv);
                    db.update("EAT",cv,"_name='"+name+"'",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void load_acup(){
        try {
            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open("AUCP.csv") );
            //Toast.makeText(this,"找到檔案了",Toast.LENGTH_SHORT).show();
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            while((line = bufReader.readLine()) != null) {
                String[] Eat = line.split(",");
                String name=Eat[0];
                String effect=Eat[1];
                String who =Eat[2];
                String main1 =Eat[3];
                String main2 =Eat[4];
                String method=Eat[5];
                String theory=Eat[6];
                String Source=Eat[7];
                String subset=Eat[8];
                String note=Eat[9];
                String link=Eat[12];
                int P = Integer.parseInt(Eat[10]);
                int D = Integer.parseInt(Eat[11]);
                int f=0;
                ContentValues cv = new ContentValues();
                cv.put("_name",name);
                cv.put("effect",effect);
                cv.put("who",who);
                cv.put("method",method);
                cv.put("subset",subset);
                cv.put("main",main1);
                cv.put("main2",main2);
                cv.put("favor",f);
                cv.put("P",P);
                cv.put("D",D);
                cv.put("Source",Source);
                cv.put("theory",theory);
                cv.put("note",note);
                cv.put("link",link);
                db.insert("ACUP",null,cv);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"ACUP_LOAD: "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void load_sport() {
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open("SPORT.csv"));
            //Toast.makeText(this,"找到檔案了",Toast.LENGTH_SHORT).show();
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null) {
                String[] Eat = line.split(",");
                String name = Eat[0];
                String effect = Eat[1];
                String who = Eat[2];
                String method = Eat[3];
                String main1 = Eat[4];
                String main2 = Eat[5];
                String theory = Eat[6];
                String Source = Eat[7];
                String subset1 = Eat[8];
                String subset2 = Eat[9];
                int P = Integer.parseInt(Eat[10]);
                int D = Integer.parseInt(Eat[11]);
                int f = 0;
                ContentValues cv = new ContentValues();
                cv.put("_name", name);
                cv.put("effect", effect);
                cv.put("who", who);
                cv.put("method", method);
                cv.put("subset1", subset1);
                cv.put("subset2", subset2);
                cv.put("main", main1);
                cv.put("main2", main2);
                cv.put("favor", f);
                cv.put("P", P);
                cv.put("D", D);
                cv.put("Source", Source);
                cv.put("theory", theory);
                db.insert("SPORT", null, cv);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "ACUP_LOAD: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    public void load_habit(){
            try {
                InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open("habit.csv") );
                //Toast.makeText(this,"找到檔案了",Toast.LENGTH_SHORT).show();
                BufferedReader bufReader = new BufferedReader(inputReader);
                String line="";
                while((line = bufReader.readLine()) != null) {
                    String[] Eat = line.split(",");
                    String name=Eat[0];
                    String subset=Eat[1];
                    int priority = Integer.parseInt(Eat[2]);
                    int P = Integer.parseInt(Eat[3]);
                    int D = Integer.parseInt(Eat[4]);
                    int f=0;
                    ContentValues cv = new ContentValues();
                    cv.put("_name",name);
                    cv.put("subset",subset);
                    cv.put("P",P);
                    cv.put("D",D);
                    cv.put("priority",priority);
                    db.insert("HABIT",null,cv);
                    db.update("HABIT",cv,"_name='"+name+"'",null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this,"HABIT_LOAD: "+e.toString(),Toast.LENGTH_SHORT).show();
            }
    }
    public void load_dayday(){
        try {
            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open("DayDay.csv") );
            //Toast.makeText(this,"找到檔案了",Toast.LENGTH_SHORT).show();
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            while((line = bufReader.readLine()) != null) {
                String[] Day = line.split(",");
                String ID = Day[0];
                String name=Day[1];
                String article =Day[2];
                int P=Integer.parseInt(Day[3]);
                int D=Integer.parseInt(Day[4]);
                int priority=Integer.parseInt(Day[5]);
                int max =7;
                int selected=0;
                ContentValues cv = new ContentValues();
                cv.put("name",name);
                cv.put("_ID",ID);
                cv.put("P",P);
                cv.put("D",D);
                cv.put("priority",priority);
                cv.put("max",max);
                cv.put("selected",selected);
                cv.put("article",article);
                db.insert("Day",null,cv);
                //db.update("Day",cv,"_ID='"+ID+"'",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"DayDay_LOAD: "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void load_men_sym(){
        try {
            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open("rr.csv") );
            //Toast.makeText(this,"找到檔案了",Toast.LENGTH_SHORT).show();
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            while((line = bufReader.readLine()) != null) {
                String[] Day = line.split(",");
                ContentValues cv = new ContentValues();
                cv.put("name",Day[0]);
                for(int i=1;i<=5;i++){
                    cv.put("A"+Integer.toString(i),Day[i]);
                }
                db.insert("men_sym",null,cv);
                db.update("men_sym",cv,"name ='"+Day[0]+"'",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"men_sym_LOAD: "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void load_men_sym2(){
        try {
            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open("rr2.csv") );
            //Toast.makeText(this,"找到檔案了",Toast.LENGTH_SHORT).show();
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            while((line = bufReader.readLine()) != null) {
                String[] Day = line.split(",");
                ContentValues cv = new ContentValues();
                cv.put("name",Day[0]);
                for(int i=1;i<=5;i++){
                    cv.put("A"+Integer.toString(i),Day[i]);
                }
                db.insert("men_sym2",null,cv);
                db.update("men_sym2",cv,"name ='"+Day[0]+"'",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"men_sym2_LOAD: "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void load_breast_sym(){
        try {
            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open("breast.csv") );
            //Toast.makeText(this,"找到檔案了",Toast.LENGTH_SHORT).show();
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            while((line = bufReader.readLine()) != null) {
                String[] Day = line.split(",");
                ContentValues cv = new ContentValues();
                cv.put("name",Day[0]);
                for(int i=1;i<=5;i++){
                    cv.put("A"+Integer.toString(i),Day[i]);
                }
                db.insert("breast_sym",null,cv);
                db.update("breast_sym",cv,"name ='"+Day[0]+"'",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"breast_sym_LOAD: "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void load_breast_sym2(){
        try {
            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open("breast2.csv") );
            //Toast.makeText(this,"找到檔案了",Toast.LENGTH_SHORT).show();
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            while((line = bufReader.readLine()) != null) {
                String[] Day = line.split(",");
                ContentValues cv = new ContentValues();
                cv.put("name",Day[0]);
                for(int i=1;i<=5;i++){
                    cv.put("A"+Integer.toString(i),Day[i]);
                }
                db.insert("breast_sym2",null,cv);
                db.update("breast_sym2",cv,"name ='"+Day[0]+"'",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"breast_sym2_LOAD: "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void load_geni_sym(){
        try {
            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open("geni.csv") );
            //Toast.makeText(this,"找到檔案了",Toast.LENGTH_SHORT).show();
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            while((line = bufReader.readLine()) != null) {
                String[] Day = line.split(",");
                ContentValues cv = new ContentValues();
                cv.put("name",Day[0]);
                for(int i=1;i<=5;i++){
                    cv.put("A"+Integer.toString(i),Day[i]);
                }
                db.insert("geni_sym",null,cv);
                db.update("geni_sym",cv,"name ='"+Day[0]+"'",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"geni_sym_LOAD: "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void load_geni_sym2(){
        try {
            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open("geni2.csv") );
            //Toast.makeText(this,"找到檔案了",Toast.LENGTH_SHORT).show();
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            while((line = bufReader.readLine()) != null) {
                String[] Day = line.split(",");
                ContentValues cv = new ContentValues();
                cv.put("name",Day[0]);
                for(int i=1;i<=5;i++){
                    cv.put("A"+Integer.toString(i),Day[i]);
                }
                db.insert("geni_sym2",null,cv);
                db.update("geni_sym2",cv,"name ='"+Day[0]+"'",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"geni_sym2_LOAD: "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void load_post_sym(){
        try {
            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open("post.csv") );
            //Toast.makeText(this,"找到檔案了",Toast.LENGTH_SHORT).show();
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            while((line = bufReader.readLine()) != null) {
                String[] Day = line.split(",");
                ContentValues cv = new ContentValues();
                cv.put("name",Day[0]);
                for(int i=1;i<=5;i++){
                    cv.put("A"+Integer.toString(i),Day[i]);
                }
                db.insert("post_sym",null,cv);
                db.update("post_sym",cv,"name ='"+Day[0]+"'",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"post_sym_LOAD: "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void load_post_sym2(){
        try {
            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open("post2.csv") );
            //Toast.makeText(this,"找到檔案了",Toast.LENGTH_SHORT).show();
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            while((line = bufReader.readLine()) != null) {
                String[] Day = line.split(",");
                ContentValues cv = new ContentValues();
                cv.put("name",Day[0]);
                for(int i=1;i<=5;i++){
                    cv.put("A"+Integer.toString(i),Day[i]);
                }
                db.insert("post_sym2",null,cv);
                db.update("post_sym2",cv,"name ='"+Day[0]+"'",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"post_sym2_LOAD: "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void load_gest_sym(){
        try {
            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open("gest.csv") );
            //Toast.makeText(this,"找到檔案了",Toast.LENGTH_SHORT).show();
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            while((line = bufReader.readLine()) != null) {
                String[] Day = line.split(",");
                ContentValues cv = new ContentValues();
                cv.put("name",Day[0]);
                for(int i=1;i<=5;i++){
                    cv.put("A"+Integer.toString(i),Day[i]);
                }
                db.insert("gest_sym",null,cv);
                db.update("gest_sym",cv,"name ='"+Day[0]+"'",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"gest_sym_LOAD: "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void load_gest_sym2(){
        try {
            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open("gest2.csv") );
            //Toast.makeText(this,"找到檔案了",Toast.LENGTH_SHORT).show();
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            while((line = bufReader.readLine()) != null) {
                String[] Day = line.split(",");
                ContentValues cv = new ContentValues();
                cv.put("name",Day[0]);
                for(int i=1;i<=5;i++){
                    cv.put("A"+Integer.toString(i),Day[i]);
                }
                db.insert("gest_sym2",null,cv);
                db.update("gest_sym2",cv,"name ='"+Day[0]+"'",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"gest_sym2_LOAD: "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void load_link(){
        try {
            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open("link.csv") );
            //Toast.makeText(this,"找到檔案了",Toast.LENGTH_SHORT).show();
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            while((line = bufReader.readLine()) != null) {
                String[] Day = line.split(",");
                ContentValues cv = new ContentValues();
                cv.put("sym_name",Day[0]);
                cv.put("_name",Day[1]);
                cv.put("db_name",Day[2]);
                db.insert("link",null,cv);
                db.update("link",cv,"sym_name ='"+Day[0]+"' and _name = '"+Day[1]+"'",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"gest_link: "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void load_prescription(){
        try {
            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open("prescription.csv") );
            //Toast.makeText(this,"找到檔案了",Toast.LENGTH_SHORT).show();
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            while((line = bufReader.readLine()) != null) {
                String[] Day = line.split(",");
                ContentValues cv = new ContentValues();
                cv.put("name",Day[0]);
                cv.put("A1",Day[1]);
                cv.put("A2",Day[2]);
                db.insert("prescription",null,cv);
                db.update("prescription",cv,"name ='"+Day[0]+"'",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"gest_link: "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
}
