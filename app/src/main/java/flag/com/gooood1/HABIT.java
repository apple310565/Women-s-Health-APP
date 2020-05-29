package flag.com.gooood1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class HABIT extends AppCompatActivity {

    private SQLiteDatabase db;
    private StdDBHelper dbHelper;
    String Str="";
    String P=" P=0 ";
    Cursor c;
    int F=0;
    String sub;
    int size=22,pp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_a_b_i_t);
        //建立SQLOHleper物件
        dbHelper = new StdDBHelper(this);
        db =dbHelper.getWritableDatabase();//開啟資料庫

        //設定搜尋條件
        String bb="";
        try{
            FileInputStream in = openFileInput("PPP.txt");
            byte[] data = new byte[128];
            in.read(data);
            in.close();
            bb = new String(data);
            String[] tmp = bb.split(",");
            String line=tmp[0];
            if(line.equals("1")){P+=" OR P=1 OR P=12 OR P=13 OR P=14 OR P=123 OR P=124 OR P=134 ";pp=1;}
            else if(line.equals("2")){P+=" OR P=2 OR P=12 OR P=23 OR P=24 OR P=123 OR P=124 OR P=234 ";pp=2;}
            else if(line.equals("3")){P+=" OR P=3 OR P=13 OR P=23 OR P=34 OR P=123 OR P=134 OR P=234 ";pp=3;}
            else if(line.equals("4")){P+=" OR P=4 OR P=14 OR P=24 OR P=34 OR P=124 OR P=134 OR P=234 ";pp=4;}
            sub = tmp[1];
            if(sub.equals("日常保健"))F=0;
            else F=1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        Str = "SELECT * FROM HABIT WHERE (("+P+") AND subset='"+sub+"'"+") ORDER BY priority DESC";

        //下拉式選單設定
        Spinner spinner2 = (Spinner)findViewById(R.id.spinner_select);
        final String[] lunch2 = {"日常保健", "減肥塑身"};
        ArrayAdapter<String> lunchList2 = new ArrayAdapter<>(HABIT.this,
                android.R.layout.simple_spinner_dropdown_item,
                lunch2);spinner2.setAdapter(lunchList2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(lunch2[position].equals("日常保健")){
                    Str="SELECT * FROM HABIT WHERE (("+P+") AND subset='"+lunch2[position]+"'"+") ORDER BY priority DESC";
                    F=0;
                    //SqlQuery(Str);
                }
                else if(lunch2[position].equals("減肥塑身")){
                    Str="SELECT * FROM HABIT WHERE (("+P+") AND subset='"+lunch2[position]+"'"+") ORDER BY priority DESC";
                    F=1;
                    //SqlQuery(Str);
                }
                //Toast.makeText(eat.this, str, Toast.LENGTH_SHORT).show();
                SqlQuery(Str);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}

        });


        SqlQuery(Str);
        spinner2.setSelection(F);

    }

    public void SqlQuery(String sql) {
        String str = "";
        int a=0;
        try {
            c = db.rawQuery(sql, null);
            str = "";
            c.moveToFirst();
            a=c.getCount();
            //Toast.makeText(this,"a = "+Integer.toString(a),Toast.LENGTH_SHORT).show();
            if(a!=0) {
                if(F==1){
                    if(pp==1)str+="[瘦身滯留期]\n\n";
                    else if(pp==2)str+="[瘦身高峰期]\n\n";
                    else if(pp==3)str+="[瘦身緩和期]\n\n";
                    else if(pp==4)str+="[瘦身停滯期]\n\n";
                }
                int t=1;
                while(!c.isLast()) {
                    if (!c.getString(0).equals("NULL")) {
                        str += Integer.toString(t) + ". " + c.getString(0) + "\n\n";
                        t++;
                    }
                    c.moveToNext();
                }
                if (!c.getString(0).equals("NULL")) {
                    str += Integer.toString(t) + ". " + c.getString(0) + "\n\n";
                    t++;
                }
                TextView article = (TextView) findViewById(R.id.article);
                article.setText(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
        if(a==0){
            Toast.makeText(this,"找不到符合條件的資料QwQ",Toast.LENGTH_SHORT).show();
            Spinner spinner2=(Spinner)findViewById(R.id.spinner_select);
            spinner2.setSelection(1);
            Str="SELECT * FROM HABIT WHERE ("+ P +") ORDER BY priority DESC";
            SqlQuery(Str);
        }
        //c.moveToNext();
    }

    public void goto_M3(View view){
        Intent intent = new Intent();
        intent.setClass(HABIT.this,Main3Activity.class);
        startActivity(intent);
        HABIT.this.finish();
    }
    public void ZoomIn(View view){
        size=size+2;
        TextView tv =(TextView)findViewById(R.id.article);
        tv.setTextSize(size);
    }
    public void ZoomOut(View view){
        size=size-2;
        TextView tv =(TextView)findViewById(R.id.article);
        tv.setTextSize(size);
    }
}

