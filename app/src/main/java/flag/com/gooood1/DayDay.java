package flag.com.gooood1;

import androidx.appcompat.app.AppCompatActivity;

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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.IDN;
import java.text.NumberFormat;
import java.util.Calendar;

public class DayDay extends AppCompatActivity {
    private SQLiteDatabase db;
    private StdDBHelper dbHelper;
    int t=0;
    String Date;
    Cursor c;
    int[] Mon={31,28,31,30,31,30,31,31,30,31,30,31};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_day);
        //建立SQLOHleper物件
        dbHelper = new StdDBHelper(this);
        db =dbHelper.getWritableDatabase();//開啟資料庫

        Calendar cd = Calendar.getInstance();
        int year = cd.get(Calendar.YEAR);
        int month =  cd.get(Calendar.MONTH);
        int day = cd.get(Calendar.DAY_OF_MONTH);
        int date = cd.get(Calendar.DAY_OF_WEEK);
        if(date==1)day=day-6;
        else day=day-date+2;
        if(day<=0){
            if(month>0)month--;
            else {month=12;year--;}
            day=Mon[month]-day;
        }


        Date= Integer.toString(year)+'/'+Integer.toString(month+1)+'/'+Integer.toString(day);
        SqlQuery("SELECT * FROM Day WHERE selected=1");

    }

    public void SqlQuery(String sql) {
        String str = "";
        int a=0;
        int progress;
        try {
            c = db.rawQuery(sql, null);
            str = "";
            c.moveToFirst();
            a=c.getCount();
            if(a!=0) {
                for(int i=0;i<a;i++) {
                    String ID = c.getString(0);
                    String name = c.getString(1);
                    int max = Integer.parseInt(c.getString(6));
                    Cursor tmp = db.rawQuery("SELECT * FROM DayIn WHERE _ID = '" + ID + "' AND _date = '" + Date + "'", null);
                    tmp.moveToFirst();
                    if (tmp.getCount() == 0) {
                        ContentValues cv = new ContentValues();
                        cv.put("_date", Date);
                        cv.put("_ID", ID);
                        cv.put("progress", 0);
                        cv.put("complete", 0);
                        cv.put("d1", "NULL");
                        cv.put("d2", "NULL");
                        cv.put("d3", "NULL");
                        cv.put("d4", "NULL");
                        cv.put("d5", "NULL");
                        cv.put("d6", "NULL");
                        cv.put("d7", "NULL");
                        db.insert("DayIn", null, cv);
                         progress =0;
                    }
                    else {
                         progress = Integer.parseInt(tmp.getString(2));
                    }
                    //int progress=1;
                    cc(ID,name,max,progress);
                    c.moveToNext();
                }
            }
            else{
                new AlertDialog.Builder(DayDay.this)
                        .setIcon(R.drawable.ic_launcher_background)
                        .setTitle("Message")
                        .setMessage("目前你暫時沒有收藏任何想養成的習慣，將前往新增習慣介面去選擇想養成的習慣喔(*ﾟ∀ﾟ*)")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setClass(DayDay.this, Day_add.class);
                                startActivity(intent);
                                DayDay.this.finish();
                            }
                        })
                        .show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public   void cc( final String ID, String name,  int max,  int progress){
        LinearLayout LL =(LinearLayout)findViewById(R.id.LL);
        LinearLayout Lout = new LinearLayout(this);
        Lout.setPadding(20,20,20,20);
        LL.addView(Lout, RelativeLayout.LayoutParams.MATCH_PARENT,280);
        LinearLayout Lin = new LinearLayout(this);
        Lin.setOrientation(LinearLayout.HORIZONTAL);
        Lout.addView(Lin, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        if(t%2==0)Lin.setBackgroundColor(Color.parseColor("#FF9800"));
        else Lin.setBackgroundColor(Color.parseColor("#FF5722"));
        final ImageView im =new ImageView(this);
        im.setImageDrawable(getResources().getDrawable( R.drawable.r1 ));
        Lin.addView(im, 230,230);
        LinearLayout L2 =new LinearLayout(this);
        L2.setOrientation(LinearLayout.VERTICAL);
        Lin.addView(L2, RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        Lin.setPadding(10,10,5,5);
        final TextView tv =new TextView(this);
        tv.setTextColor(Color.parseColor("#FFFFFF"));
        if(t<6)tv.setText(name);
        tv.setTextSize(20);
        L2.setGravity(Gravity.CENTER_VERTICAL);

        if(name.length()>10)tv.setTextSize(16);

        L2.addView(tv, RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout L3 =new LinearLayout(this);
        L3.setOrientation(LinearLayout.HORIZONTAL);
        L2.addView(L3,RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        L3.setGravity(Gravity.CENTER);
        final SeekBar SB =new SeekBar(this);
        SB.setMax(max);
        SB.setProgress(progress);
        final Button add = new Button(this);
        final Button sub = new Button(this);
        final String id = tv.getText().toString();
        add.setText("+");
        sub.setText("-");
        add.setTextSize(18);
        sub.setTextSize(18);
        L3.addView(SB,420,50);
        L3.addView(add,120,120);
        L3.addView(sub,120,120);
        //SB.setEnabled(false);

        float i = ((float) SB.getMax()/4);
        int p=SB.getProgress();
        if(p==SB.getMax())im.setImageDrawable(getResources().getDrawable( R.drawable.r5 ));
        else if(p>=SB.getMax()-(int)(i))im.setImageDrawable(getResources().getDrawable( R.drawable.r4 ));
        else if(p>=SB.getMax()-(int)(2*i))im.setImageDrawable(getResources().getDrawable( R.drawable.r3 ));
        else if(p>=SB.getMax()-(int)(3*i))im.setImageDrawable(getResources().getDrawable( R.drawable.r2 ));
        else im.setImageDrawable(getResources().getDrawable( R.drawable.r1 ));


        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                float i = ((float) SB.getMax()/4);
                int p=SB.getProgress();
                if(p<SB.getMax())SB.setProgress(p+1);
                //tv.setText(id);
                p=SB.getProgress();
                if(p==SB.getMax())im.setImageDrawable(getResources().getDrawable( R.drawable.r5 ));
                else if(p>=SB.getMax()-(int)(i))im.setImageDrawable(getResources().getDrawable( R.drawable.r4 ));
                else if(p>=SB.getMax()-(int)(2*i))im.setImageDrawable(getResources().getDrawable( R.drawable.r3 ));
                else if(p>=SB.getMax()-(int)(3*i))im.setImageDrawable(getResources().getDrawable( R.drawable.r2 ));
                else im.setImageDrawable(getResources().getDrawable( R.drawable.r1 ));

                ContentValues cv = new ContentValues();
                cv.put("progress",p);
                cv.put("complete",(float)p/(float)SB.getMax());
                db.update("DayIn",cv, "_ID = '"+ID+"' AND _date = '"+Date+"'" ,null);
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                float i = ((float) SB.getMax()/4);
                int p=SB.getProgress();;
                if(p>0)SB.setProgress(p-1);
                //tv.setText(id);
                p=SB.getProgress();
                if(p==SB.getMax())im.setImageDrawable(getResources().getDrawable( R.drawable.r5 ));
                else if(p>=SB.getMax()-(int)(i))im.setImageDrawable(getResources().getDrawable( R.drawable.r4 ));
                else if(p>=SB.getMax()-(int)(2*i))im.setImageDrawable(getResources().getDrawable( R.drawable.r3 ));
                else if(p>=SB.getMax()-(int)(3*i))im.setImageDrawable(getResources().getDrawable( R.drawable.r2 ));
                else im.setImageDrawable(getResources().getDrawable( R.drawable.r1 ));

                ContentValues cv = new ContentValues();
                cv.put("progress",p);
                cv.put("complete",(float)p/(float)SB.getMax());
                db.update("DayIn",cv, "_ID = '"+ID+"' AND _date = '"+Date+"'" ,null);
            }
        });

        SB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                float i = ((float) SB.getMax()/4);
                int p=SB.getProgress();;
                if(p==SB.getMax())im.setImageDrawable(getResources().getDrawable( R.drawable.r5 ));
                else if(p>=SB.getMax()-(int)(i))im.setImageDrawable(getResources().getDrawable( R.drawable.r4 ));
                else if(p>=SB.getMax()-(int)(2*i))im.setImageDrawable(getResources().getDrawable( R.drawable.r3 ));
                else if(p>=SB.getMax()-(int)(3*i))im.setImageDrawable(getResources().getDrawable( R.drawable.r2 ));
                else im.setImageDrawable(getResources().getDrawable( R.drawable.r1 ));
                ContentValues cv = new ContentValues();
                cv.put("progress",p);
                cv.put("complete",(float)p/(float)SB.getMax());
                db.update("DayIn",cv, "_ID = '"+ID+"' AND _date = '"+Date+"'" ,null);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        Lin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //goto_DayIn
                Intent intent = new Intent();
                intent.setClass(DayDay.this,Day_in.class);
                Bundle bundle = new Bundle();
                bundle.putString("_ID", ID);
                bundle.putString("_date", Date);
                intent.putExtras(bundle);
                startActivity(intent);
                DayDay.this.finish();
            }
        });
        t++;
    }



    public void goto_ADD(View view){
        Intent intent = new Intent();
        intent.setClass(DayDay.this,Day_add.class);
        startActivity(intent);
        DayDay.this.finish();
    }

    public void goto_M3(View view){
        Intent intent = new Intent();
        intent.setClass(DayDay.this,Main3Activity.class);
        startActivity(intent);
        DayDay.this.finish();
    }

    public void rank(View view){

        Cursor DayDay = db.rawQuery("SELECT * FROM Day WHERE selected=1", null);
        DayDay.moveToFirst();
        float total=0,avg,rank;
        int t=0;
        for(int i=0;i<DayDay.getCount();i++){
            String ID = DayDay.getString(0);
            Cursor tmp = db.rawQuery("SELECT * FROM DayIn WHERE _ID = '" + ID + "' AND _date = '" + Date + "'", null);
            tmp.moveToFirst();
            total+=Float.parseFloat(tmp.getString(3));
            t++;
            DayDay.moveToNext();
        }
        avg=(total/(float)t)*100;
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits( 2 );    //小數後兩位

        rank= (float)Math.sqrt((double) avg);
        rank=rank*(float) 9.7;
        rank=rank+(float)((Math.random()*300)*0.001);

        LayoutInflater inflater = LayoutInflater.from(DayDay.this);
        final View v = inflater.inflate(R.layout.rank, null);
        TextView com=(TextView) (v.findViewById(R.id.r_com));
        com.setText("本週平均完成度: "+ nf.format(avg)+"%");
        TextView ran=(TextView) (v.findViewById(R.id.r_rank));
        ImageView V_img=(ImageView) (v.findViewById(R.id.r_img));
        if(rank>90)V_img.setImageDrawable(getResources().getDrawable( R.drawable.r5 ));
        else if(rank>80)V_img.setImageDrawable(getResources().getDrawable( R.drawable.r4 ));
        else if(rank>60)V_img.setImageDrawable(getResources().getDrawable( R.drawable.r3 ));
        else if(rank>40)V_img.setImageDrawable(getResources().getDrawable( R.drawable.r2 ));
        else V_img.setImageDrawable(getResources().getDrawable( R.drawable.r1 ));
        ran.setText("你比"+nf.format(rank)+"%的人完成度高!");
        new AlertDialog.Builder(DayDay.this)
                .setView(v)
                .setTitle("Message")
                .setIcon(getResources().getDrawable( R.drawable.crown2 ))
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

}
