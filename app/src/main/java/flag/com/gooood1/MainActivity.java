package flag.com.gooood1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView m1DisplayDate;
    private static String DATABASE_TABLE="Data";
    private SQLiteDatabase db;
    private StdDBHelper dbHelper;
    private DatePickerDialog.OnDateSetListener m1DataSetListerner;
    int [] Mon = {0,31,59,90,120,151,181,212,243,273,304,334};
    int Y,M,D,peroid=29,flag=0,a,flag2=0,day1=0,day2=0,i_M;
    String account,passWD,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String line="User not exist";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //建立SQLOHleper物件
        dbHelper = new StdDBHelper(this);
        db =dbHelper.getWritableDatabase();//開啟資料庫
        //若是第一次登入沒有帳密，則切換成註冊頁面
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
            email=name[5];
        }
        catch (IOException e){
            new AlertDialog.Builder(MainActivity.this)
                    .setIcon(R.drawable.ic_launcher_background)
                    .setTitle("Message")
                    .setMessage("第一次使用請先設置帳密喔")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this, Main2Activity.class);
                            startActivity(intent);
                            MainActivity.this.finish();
                        }
                    })
                    .show();
        }
        //選擇日期
        /*參考網址: https://www.youtube.com/watch?v=hwe1abDO2Ag*/
        if(flag==1){
            m1DisplayDate = (TextView)findViewById(R.id.tvDate);
            Calendar cal = Calendar.getInstance();
            int year =cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            m1DisplayDate.setText(year + "/" + (month+1)+"/"+day);
            isExist(year + "/" + (month+1)+"/"+day);

            day1 = Mon[month]+day;day2 = Mon[M-1]+D;
            int days = (year-Y)*365+(day1-day2);
            int mense =peroid - (days%peroid);
            if(mense>=peroid)mense=mense-peroid;
            TextView Days = (TextView)findViewById(R.id.tvDay);
            Days.setText(Integer.toString(mense));
            if(is_mense()==0)out_memse();
            else in_memse();


            m1DisplayDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    Calendar cal = Calendar.getInstance();
                    int year =cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog dialog = new DatePickerDialog(
                            MainActivity.this,
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            m1DataSetListerner,
                            year,month,day);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            });

            m1DataSetListerner = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    //New();
                    Log.d("MainActivity","onDate: date = " +month+" / "+dayOfMonth+ " / "+year);
                    String date =year + "/" + (month+1)+"/"+dayOfMonth;
                    m1DisplayDate.setText(date);

                    day1 = Mon[month] + dayOfMonth;
                    day2 = Mon[M - 1] + D;
                    int days = (year - Y) * 365 + (day1 - day2);
                    int mense = peroid - (days % peroid);
                    if (mense >= peroid) mense = mense - peroid;
                    TextView Days = (TextView) findViewById(R.id.tvDay);
                    if(is_mense()==0)Days.setText(Integer.toString(mense));
                    else Days.setText(Integer.toString(days+1));

                    isExist(date);
                }
            };

        }

    }
    @Override
    protected void onStop(){
        super.onStop();
        db.close();
    }
    //紀錄
    public void submit_click(View view){
        int f=-1,c=-1,q=-1,i_S=0,i_E=0;

        RadioGroup flow = (RadioGroup)findViewById(R.id.RGflow);
        if(flow.getCheckedRadioButtonId()==R.id.f1)f=1;
        else if(flow.getCheckedRadioButtonId()==R.id.f2)f=2;
        else if(flow.getCheckedRadioButtonId()==R.id.f3)f=3;
        else if(flow.getCheckedRadioButtonId()==R.id.f4)f=4;
        else if(flow.getCheckedRadioButtonId()==R.id.f5)f=5;

        RadioGroup color = (RadioGroup)findViewById(R.id.RGcolor);
        if(color.getCheckedRadioButtonId()==R.id.c1)c=1;
        else if(color.getCheckedRadioButtonId()==R.id.c2)c=2;
        else if(color.getCheckedRadioButtonId()==R.id.c3)c=3;
        else if(color.getCheckedRadioButtonId()==R.id.c4)c=4;
        else if(color.getCheckedRadioButtonId()==R.id.c5)c=5;

        RadioGroup quality = (RadioGroup)findViewById(R.id.RGqaulity);
        if(quality.getCheckedRadioButtonId()==R.id.q1)q=1;
        else if(quality.getCheckedRadioButtonId()==R.id.q2)q=2;
        else if(quality.getCheckedRadioButtonId()==R.id.q3)q=3;
        else if(quality.getCheckedRadioButtonId()==R.id.q4)q=4;
        else if(quality.getCheckedRadioButtonId()==R.id.q5)q=5;

        Switch isStart = (Switch)findViewById(R.id.switch3);
        Switch isEnd = (Switch)findViewById(R.id.end);
        if(isStart.isChecked())i_S=1;
        if(isEnd.isChecked())i_E=1;

        ContentValues cv = new ContentValues();
        if(flag2==0)cv.put("_date",m1DisplayDate.getText().toString());
        cv.put("flow",f);
        cv.put("color",c);
        cv.put("quality",q);
        cv.put("isStart",i_S);
        cv.put("isEnd",i_E);
        String id =m1DisplayDate.getText().toString();
        if(flag2==0)db.insert(DATABASE_TABLE,null,cv);
        else {
            try {
                db.update(DATABASE_TABLE,cv, "_date='"+id+"'",null);
            } catch (Exception e) {
                Toast.makeText(this,"更新失敗，我也很絕望啊orz"+e.toString(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        if(((day2+i_M)==day1)&&i_E==0)cancel_END();
        if(i_S==1&&i_M!=-1)firstDay();
        if(i_E==1)END();
        Toast.makeText(this,"感謝親的紀錄~ (,,・ω・,,)",Toast.LENGTH_SHORT).show();
        SqlQuery("SELECT * FROM "+DATABASE_TABLE);
    }
    public int is_mense(){
        if(day1>=day2){
            if(i_M==-1)return 1;
            else if(i_M>=(day1-day2))return 1;
        }
        return 0;
    }
    public void in_memse(){
        ConstraintLayout bg =(ConstraintLayout)findViewById(R.id.day);
        bg.setBackgroundColor(Color.parseColor("#FFB0E0E6"));
        Button Bt = (Button)findViewById(R.id.button2);
        Bt.setBackgroundColor(Color.parseColor("#FF6DD0CD"));
        Bt.setTextColor(Color.parseColor("#FFFFFFFF"));
        Button Bt2 = (Button)findViewById(R.id.button3);
        Bt2.setBackgroundColor(Color.parseColor("#FF6DD0CD"));
        Bt2.setTextColor(Color.parseColor("#FFFFFFFF"));
        TextView text1 = (TextView)findViewById(R.id.text1);
        text1.setText("經期已經開始");
        text1.setTextSize(18);
        TextView tvDay = (TextView)findViewById(R.id.tvDay);
        int days=day1-day2;
        tvDay.setText(Integer.toString(days+1));
        Switch i_S = (Switch)findViewById(R.id.switch3);
        i_S.setChecked(false);
        i_S.setEnabled(false);
        Switch i_E = (Switch)findViewById(R.id.end);
        if(day1==day2||(i_M!=-1&&day2+i_M!=day1))i_E.setEnabled(false);
        else i_E.setEnabled(true);

        //Toast.makeText(this,"IN_MEMSE!!!!",Toast.LENGTH_SHORT).show();
    }
    public void out_memse(){
        ConstraintLayout bg =(ConstraintLayout)findViewById(R.id.day);
        bg.setBackgroundColor(Color.parseColor("#FFFCDF"));
        Button Bt = (Button)findViewById(R.id.button2);
        Bt.setBackgroundColor(Color.parseColor("#B8B8B8"));
        Bt.setTextColor(Color.parseColor("#FFFFFFFF"));
        Button Bt2 = (Button)findViewById(R.id.button3);
        Bt2.setBackgroundColor(Color.parseColor("#B8B8B8"));
        Bt2.setTextColor(Color.parseColor("#FFFFFFFF"));
        TextView text1 = (TextView)findViewById(R.id.text1);
        text1.setText("經期倒數");
        text1.setTextSize(24);
        Switch i_S = (Switch)findViewById(R.id.switch3);
        if(day1>day2)i_S.setEnabled(true);
        else i_S.setEnabled(false);
        i_S.setChecked(false);
        Switch i_E = (Switch)findViewById(R.id.end);
        i_E.setChecked(false);
        i_E.setEnabled(false);

        if(day1-day2>peroid&&day1-day2<peroid+15){
            text1.setText("經期已經晚到");
            text1.setTextSize(18);
            TextView tv = (TextView)findViewById(R.id.tvDay);
            tv.setText(Integer.toString(day1-day2-peroid));
        }

    }
    public void firstDay(){
        peroid=day1-day2;
        String str=account+","+passWD+","+peroid+","+m1DisplayDate.getText().toString()+","+"-1,"+email+",";
        try {
            FileOutputStream out = openFileOutput("passwd.txt",MODE_PRIVATE);
            out.write(str.getBytes());
            out.close();
            Toast.makeText(this,"In_memse",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this,"文件创建失败"+e.toString(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        String [] Date = m1DisplayDate.getText().toString().split("/");
        Y=Integer.parseInt(Date[0]);
        M=Integer.parseInt(Date[1]);
        D=Integer.parseInt(Date[2]);
        day2=Mon[M-1]+D;
        i_M=-1;
        in_memse();
    }
    public void END(){
        i_M=day1-day2;
        String str=account+","+passWD+","+peroid+","+Y+"/"+M+"/"+D+","+i_M+","+email+",";
        try {
            FileOutputStream out = openFileOutput("passwd.txt",MODE_PRIVATE);
            out.write(str.getBytes());
            out.close();
            Toast.makeText(this,"成功寫入檔案",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this,"文件创建失败"+e.toString(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void SqlQuery(String sql){
        String[] colNames;
        String str="";
        Cursor c = db.rawQuery(sql,null);
        colNames =c.getColumnNames();
        str+=colNames[0]+"\t\t\t\t\t";
        for(int i=1;i<colNames.length;i++)
            str+=colNames[i]+"\t\t";
        str+="\n";
        c.moveToFirst();

        for(int i=0;i<c.getCount();i++){
            str+=c.getString(0)+"\t\t\t";
            str+=c.getString(1)+"\t\t\t\t";
            str+=c.getString(2)+"\t\t\t\t\t";
            str+=c.getString(3)+"\t\t\t\t\t";
            str+=c.getString(4)+"\t\t\t\t\t";
            str+=c.getString(5)+"\n";
            c.moveToNext();
        }
        new AlertDialog.Builder(MainActivity.this)
                .setIcon(R.drawable.ic_launcher_background)
                .setTitle("Save sucessful")
                .setMessage(str)
                .show();
    }
    public void New(){
        RadioGroup flow = (RadioGroup)findViewById(R.id.RGflow);
        if(flow.getCheckedRadioButtonId()==R.id.f1){RadioButton f1 = (RadioButton)findViewById(R.id.f1);f1.setChecked(false);}
        else if(flow.getCheckedRadioButtonId()==R.id.f2){RadioButton f1 = (RadioButton)findViewById(R.id.f2);f1.setChecked(false);}
        else if(flow.getCheckedRadioButtonId()==R.id.f3){RadioButton f1 = (RadioButton)findViewById(R.id.f3);f1.setChecked(false);}
        else if(flow.getCheckedRadioButtonId()==R.id.f4){RadioButton f1 = (RadioButton)findViewById(R.id.f4);f1.setChecked(false);}
        else if(flow.getCheckedRadioButtonId()==R.id.f5){RadioButton f1 = (RadioButton)findViewById(R.id.f5);f1.setChecked(false);}

        RadioGroup color = (RadioGroup)findViewById(R.id.RGcolor);
        if(color.getCheckedRadioButtonId()==R.id.c1){RadioButton c1 = (RadioButton)findViewById(R.id.c1);c1.setChecked(false);}
        else if(color.getCheckedRadioButtonId()==R.id.c2){RadioButton c1 = (RadioButton)findViewById(R.id.c2);c1.setChecked(false);}
        else if(color.getCheckedRadioButtonId()==R.id.c3){RadioButton c1 = (RadioButton)findViewById(R.id.c3);c1.setChecked(false);}
        else if(color.getCheckedRadioButtonId()==R.id.c4){RadioButton c1 = (RadioButton)findViewById(R.id.c4);c1.setChecked(false);}
        else if(color.getCheckedRadioButtonId()==R.id.c5){RadioButton c1 = (RadioButton)findViewById(R.id.c5);c1.setChecked(false);}

        RadioGroup quality = (RadioGroup)findViewById(R.id.RGqaulity);
        if(quality.getCheckedRadioButtonId()==R.id.q1){RadioButton q1 = (RadioButton)findViewById(R.id.q1);q1.setChecked(false);}
        else if(quality.getCheckedRadioButtonId()==R.id.q2){RadioButton q1 = (RadioButton)findViewById(R.id.q2);q1.setChecked(false);}
        else if(quality.getCheckedRadioButtonId()==R.id.q3){RadioButton q1 = (RadioButton)findViewById(R.id.q3);q1.setChecked(false);}
        else if(quality.getCheckedRadioButtonId()==R.id.q4){RadioButton q1 = (RadioButton)findViewById(R.id.q4);q1.setChecked(false);}
        else if(quality.getCheckedRadioButtonId()==R.id.q5){RadioButton q1 = (RadioButton)findViewById(R.id.q5);q1.setChecked(false);}
    }
    public void isExist(String date){
        if(is_mense()==1)in_memse();
        else out_memse();
        String queryStr = "select * FROM Data where _date = '%s'";
        queryStr = String.format(queryStr, date);
        Cursor c = db.rawQuery(queryStr, null);
        c.moveToFirst();
        if (c.isLast()) {
            flag2=1;

            Switch s = (Switch) findViewById(R.id.switch3);
            if(Integer.parseInt(c.getString(4))==1)s.setChecked(true);
            else s.setChecked(false);

            Switch e = (Switch) findViewById(R.id.end);
            if(Integer.parseInt(c.getString(5))==1)e.setChecked(true);
            else e.setChecked(false);

            int F =Integer.parseInt(c.getString(1));
            RadioButton f_b= (RadioButton)findViewById(R.id.f1);;
            if(F==1) f_b = (RadioButton)findViewById(R.id.f1);
            else if (F==2) f_b = (RadioButton)findViewById(R.id.f2);
            else if (F==3) f_b = (RadioButton)findViewById(R.id.f3);
            else if (F==4) f_b = (RadioButton)findViewById(R.id.f4);
            else if (F==5) f_b = (RadioButton)findViewById(R.id.f5);
            if(F!=-1)f_b.setChecked(true);

            int C =Integer.parseInt(c.getString(2));
            RadioButton c_b= (RadioButton)findViewById(R.id.c1);;
            if(C==1) c_b = (RadioButton)findViewById(R.id.c1);
            else if (C==2)c_b = (RadioButton)findViewById(R.id.c2);
            else if (C==3) c_b = (RadioButton)findViewById(R.id.c3);
            else if (C==4) c_b = (RadioButton)findViewById(R.id.c4);
            else if (C==5) c_b = (RadioButton)findViewById(R.id.c5);
            if(C!=-1)c_b.setChecked(true);

            int Q =Integer.parseInt(c.getString(3));
            RadioButton q_b= (RadioButton)findViewById(R.id.q1);;
            if(Q==1) q_b = (RadioButton)findViewById(R.id.q1);
            else  if(Q==2) q_b = (RadioButton)findViewById(R.id.q2);
            else  if(Q==3) q_b = (RadioButton)findViewById(R.id.q3);
            else  if(Q==4) q_b = (RadioButton)findViewById(R.id.q4);
            else  if(Q==5) q_b = (RadioButton)findViewById(R.id.q5);
            if(Q!=-1)q_b.setChecked(true);
        }
        else {
            flag2=0;
            //New();
        }
    }
    public void cancel_END(){
        i_M=-1;
        String str=account+","+passWD+","+peroid+","+Y+"/"+M+"/"+D+","+i_M+","+email+",";
        try {
            FileOutputStream out = openFileOutput("passwd.txt",MODE_PRIVATE);
            out.write(str.getBytes());
            out.close();
            Toast.makeText(this,"成功寫入檔案",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this,"文件创建失败"+e.toString(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        ContentValues cv = new ContentValues();
        cv.put("isEnd",0);
        String id =m1DisplayDate.getText().toString();
        db.update(DATABASE_TABLE,cv, "_date='"+id+"'",null);
    }
    public void change_firstday(View view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month =  calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        String DateTime="???";
        new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final String DateTime = String.valueOf(year)+"/"+String.valueOf(month+1)+"/"+String.valueOf(dayOfMonth);
                String [] Date = DateTime.split("/");
                int tY=Integer.parseInt(Date[0]);
                int tM=Integer.parseInt(Date[1]);
                int tD=Integer.parseInt(Date[2]);
                int tday= Mon[tM-1] + tD;
                if(i_M!=-1&&i_M+(day2-tday)<=0){
                    new AlertDialog.Builder(MainActivity.this)
                            .setIcon(R.drawable.ic_launcher_background)
                            .setTitle("Message")
                            .setMessage("經期開始日期不可以在結束日期之後喔(*´▽`*)")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
                else if((day2-tday)>=peroid){
                    new AlertDialog.Builder(MainActivity.this)
                            .setIcon(R.drawable.ic_launcher_background)
                            .setTitle("Message")
                            .setMessage("經期開始日期不可以在上一次的經期之前喔(*´▽`*)")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
                else{
                    new AlertDialog.Builder(MainActivity.this)
                            .setIcon(R.drawable.ic_launcher_background)
                            .setTitle("Message")
                            .setMessage("請問是否確定將經期開始日期由"+Y+"/"+M+"/"+D+"改為"+DateTime+"?")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    /*將原日期is_Start改為0*/
                                    ContentValues cv = new ContentValues();
                                    cv.put("isStart",0);
                                    String id =Y+"/"+M+"/"+D;
                                    db.update(DATABASE_TABLE,cv, "_date='"+id+"'",null);
                                    /*獲取新日期*/
                                    String [] Date = DateTime.split("/");
                                    Y=Integer.parseInt(Date[0]);
                                    M=Integer.parseInt(Date[1]);
                                    D=Integer.parseInt(Date[2]);
                                    /*將新日期is_Start改為1*/
                                    ContentValues cv2 = new ContentValues();
                                    cv2.put("isStart",1);
                                    String id2 =Y+"/"+M+"/"+D;
                                    db.update(DATABASE_TABLE,cv2, "_date='"+id2+"'",null);

                                    cv2.put("_Date",id2);
                                    cv2.put("flow",0);
                                    cv2.put("color",0);
                                    cv2.put("quality",0);
                                    cv2.put("isEnd",0);
                                    db.insert(DATABASE_TABLE,null,cv2);

                                    /*修改i_M 及 period*/
                                    int day3= Mon[M-1] + D;
                                    peroid=peroid+(day3-day2);
                                    if(i_M!=-1)i_M=i_M+(day2-day3);

                                    String str=account+","+passWD+","+peroid+","+id2+","+i_M+","+email+",";
                                    try {
                                        FileOutputStream out = openFileOutput("passwd.txt",MODE_PRIVATE);
                                        out.write(str.getBytes());
                                        out.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    day2=day3;
                                }
                            })
                            .show();
                }

            }
        },year,month,day).show();

    }
}

