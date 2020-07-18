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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Day_add extends AppCompatActivity {
    private SQLiteDatabase db;
    private StdDBHelper dbHelper;
    int t=0;
    String Str="";
    String P=" P=0 ";
    Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_add);

        //建立SQLOHleper物件
        dbHelper = new StdDBHelper(this);
        db =dbHelper.getWritableDatabase();//開啟資料庫

        Str="SELECT * FROM Day WHERE selected=0 ORDER BY priority";
        SqlQuery(Str);
    }

    public void SqlQuery(String sql) {
        int a=0;
        try {
            c = db.rawQuery(sql, null);
            c.moveToFirst();
            a=c.getCount();
            if(a!=0) {
                for(int i=0;i<a;i++) {
                    String name = c.getString(1);
                    String article = c.getString(2);
                    final String id = c.getString(0);

                    LinearLayout LL = (LinearLayout) findViewById(R.id.LL);
                    LinearLayout Lout = new LinearLayout(this);
                    LL.addView(Lout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    Lout.setGravity(Gravity.CENTER_VERTICAL);
                    Lout.setPadding(20, 60, 20, 20);
                    final ImageView im =new ImageView(this);
                    im.setImageDrawable(getResources().getDrawable( R.drawable.star1 ));
                    Lout.addView(im, 200, 200);
                    LinearLayout Loout = new LinearLayout(this);
                    Loout.setPadding(10,10,10,10);
                    LinearLayout Lin = new LinearLayout(this);
                    Loout.addView(Lin, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    Lout.addView(Loout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    Lin.setOrientation(LinearLayout.VERTICAL);

                    LinearLayout Liin=new LinearLayout(this);
                    Liin.setOrientation(LinearLayout.HORIZONTAL);
                    Lin.addView(Liin,LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    Liin.setGravity(Gravity.CENTER_VERTICAL);
                    final ImageView im2 =new ImageView(this);
                    im2.setImageDrawable(getResources().getDrawable( R.drawable.rabbit ));
                    Liin.addView(im2,100,100);
                    Liin.setPadding(20,20,20,20);
                    TextView NAME = new TextView(this);
                    NAME.setText(name);
                    NAME.setTextSize(22);
                    NAME.setTextColor(Color.parseColor("#FFFFFF"));
                    TextView ART = new TextView(this);
                    ART.setText(article);
                    ART.setTextSize(20);
                    ART.setBackgroundColor(Color.parseColor("#97FFFFFF"));
                    Liin.addView(NAME);
                    Lin.addView(ART);
                    Liin.setBackgroundColor(Color.parseColor("#F37C57"));
                    c.moveToNext();

                    im.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            int f;
                            Cursor tmp = db.rawQuery("SELECT selected FROM Day WHERE _ID ='"+id+"'", null);
                            tmp.moveToFirst();
                            if(tmp.getString(0).equals("1"))f=0;
                            else f=1;

                            ContentValues cv = new ContentValues();
                            cv.put("selected",f);
                            db.update("Day",cv, "_ID='"+id+"'",null);

                            if(f==0)im.setImageDrawable(getResources().getDrawable( R.drawable.star1 ));
                            else im.setImageDrawable(getResources().getDrawable( R.drawable.star2 ));
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public void goto_Day(View view){
        Intent intent = new Intent();
        intent.setClass(Day_add.this,DayDay.class);
        startActivity(intent);
        Day_add.this.finish();
    }

    public void  ADD(View view){
        LayoutInflater inflater = LayoutInflater.from(Day_add.this);
        final View v = inflater.inflate(R.layout.alertdialog_use, null);
        new AlertDialog.Builder(Day_add.this)
                .setIcon(R.drawable.ic_launcher_background)
                .setIcon(R.drawable.leaf)
                .setTitle("新增自訂內容")
                .setView(v)
                .setNegativeButton("取消新增", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("確定新增", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int selected=1;
                        int P=0;
                        int D=0;
                        int priority=0;
                        Cursor Day=db.rawQuery("SELECT * FROM Day", null);
                        Day.moveToFirst();
                        String ID= "HA"+Integer.toString(Day.getCount()+10000);

                        EditText Name=(EditText) (v.findViewById(R.id.A_name));
                        String name=Name.getText().toString();
                        EditText ART=(EditText) (v.findViewById(R.id.A_art));
                        String article=ART.getText().toString();
                        EditText Max=(EditText) (v.findViewById(R.id.A_max));
                        String Digit = "[0-9]+";
                        if(!Max.getText().toString().matches(Digit)){
                            new AlertDialog.Builder(Day_add.this)
                                    .setIcon(R.drawable.ic_launcher_background)
                                    .setTitle("Message")
                                    .setMessage("進度條隔數必須填數字呦(*´▽`*)")
                                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .show();
                        }
                        else {
                            int max = Integer.parseInt(Max.getText().toString());

                            ContentValues cv = new ContentValues();
                            cv.put("name", name);
                            cv.put("_ID", ID);
                            cv.put("P", P);
                            cv.put("D", D);
                            cv.put("priority", priority);
                            cv.put("max", max);
                            cv.put("selected", selected);
                            cv.put("article", article);
                            db.insert("Day", null, cv);

                            Toast.makeText(getApplicationContext(), "新增自訂內容成功(*´▽`*)y", Toast.LENGTH_SHORT).show();


                            //切換頁面
                            Intent intent = new Intent();
                            intent.setClass(Day_add.this, DayDay.class);
                            startActivity(intent);
                            Day_add.this.finish();
                        }
                    }
                })
                .show();
    }
}


