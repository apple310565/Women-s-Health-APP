package flag.com.gooood1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;

public class Main3Activity extends AppCompatActivity {
    private SQLiteDatabase db;
    private StdDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        //建立SQLOHleper物件
        dbHelper = new StdDBHelper(this);
        db =dbHelper.getWritableDatabase();//開啟資料庫
        LinearLayout mm = (LinearLayout)findViewById(R.id.linearLayout8);
        mm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goto_HABIT();
            }
        });

        list_habit();
    }

    public void goto_EAT(View view){
        Intent intent = new Intent();
        intent.setClass(Main3Activity.this, eat.class);
        startActivity(intent);
        Main3Activity.this.finish();
    }

    public void goto_ACUP(View view){
        Intent intent = new Intent();
        intent.setClass(Main3Activity.this, acup.class);
        startActivity(intent);
        Main3Activity.this.finish();
    }
    public void goto_SPORT(View view){
        Intent intent = new Intent();
        intent.setClass(Main3Activity.this, sport.class);
        startActivity(intent);
        Main3Activity.this.finish();
    }
    public void goto_HABIT(){
        Intent intent = new Intent();
        intent.setClass(Main3Activity.this, HABIT.class);
        startActivity(intent);
        Main3Activity.this.finish();
    }

    public void  goto_home(View view){
        Intent intent = new Intent();
        intent.setClass(Main3Activity.this,home.class);
        startActivity(intent);
        Main3Activity.this.finish();
    }

    public void Nan(){
        Toast.makeText(this,"還未完工，敬請期待~ (,,・ω・,,)",Toast.LENGTH_SHORT).show();
    }

    public void list_habit(){
        String str = "";
        int a=0;
        try {
            String Str="";
            String P=" P=0 ";
            //設定搜尋條件
            String bb="";
            Cursor c;
            int F;
            String sql="";
            try{
                FileInputStream in = openFileInput("PPP.txt");
                byte[] data = new byte[128];
                in.read(data);
                in.close();
                bb = new String(data);
                String[] tmp = bb.split(",");
                String line=tmp[0];
                if(line.equals("1")){P+=" OR P=1 OR P=12 OR P=13 OR P=14 OR P=123 OR P=124 OR P=134 ";}
                else if(line.equals("2")){P+=" OR P=2 OR P=12 OR P=23 OR P=24 OR P=123 OR P=124 OR P=234 ";}
                else if(line.equals("3")){P+=" OR P=3 OR P=13 OR P=23 OR P=34 OR P=123 OR P=134 OR P=234 ";}
                else if(line.equals("4")){P+=" OR P=4 OR P=14 OR P=24 OR P=34 OR P=124 OR P=134 OR P=234 ";}
            } catch (IOException e) {
                e.printStackTrace();
            }

            //預設成減肥
            String sub="減肥塑身";
            F=1;

            sql = "SELECT * FROM HABIT WHERE (("+P+") AND subset='"+sub+"'"+") ORDER BY priority DESC";
            c = db.rawQuery(sql, null);
            str = "";
            c.moveToFirst();
            a=c.getCount();
            //Toast.makeText(this,"a = "+Integer.toString(a),Toast.LENGTH_SHORT).show();
            if(a!=0) {
                int t=1;
                while(!c.isLast()) {
                    if (!c.getString(0).equals("NULL")) {
                        str += Integer.toString(t) + ". " + c.getString(0) + "\n";
                        t++;
                    }
                    c.moveToNext();
                }
                if (!c.getString(0).equals("NULL")) {
                    str += Integer.toString(t) + ". " + c.getString(0) + "\n";
                    t++;
                }
                TextView article = (TextView) findViewById(R.id.textView17);
                article.setText(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }

    }
}
