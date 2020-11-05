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

public class acup extends AppCompatActivity {

    private SQLiteDatabase db;
    private StdDBHelper dbHelper;
    String Str="";
    String P=" P=0 ";
    Cursor c;
    int size=22;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acup);
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
            if(line.equals("1"))P+=" OR P=1 OR P=12 OR P=13 OR P=14 OR P=123 OR P=124 OR P=134 ";
            else if(line.equals("2"))P+=" OR P=2 OR P=12 OR P=23 OR P=24 OR P=123 OR P=124 OR P=234 ";
            else if(line.equals("3"))P+=" OR P=3 OR P=13 OR P=23 OR P=34 OR P=123 OR P=134 OR P=234 ";
            else if(line.equals("4"))P+=" OR P=4 OR P=14 OR P=24 OR P=34 OR P=124 OR P=134 OR P=234 ";
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Toast.makeText(eat.this, "line = "+line, Toast.LENGTH_SHORT).show();
        Str="SELECT * FROM ACUP WHERE ("+ P +") ORDER BY favor DESC";

        //下拉式選單設定
        Spinner spinner2 = (Spinner)findViewById(R.id.spinner_select);
        final String[] lunch2 = {"全部","我的收藏","日常保健", "減肥塑身"};
        ArrayAdapter<String> lunchList2 = new ArrayAdapter<>(acup.this,
                android.R.layout.simple_spinner_dropdown_item,
                lunch2);spinner2.setAdapter(lunchList2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(lunch2[position].equals("全部")){
                    Str="SELECT * FROM ACUP WHERE ("+ P +") ORDER BY favor DESC";
                }
                if(lunch2[position].equals("我的收藏")){
                    Str="SELECT * FROM ACUP WHERE (("+P+ ") AND favor = 1)";
                }
                else if(lunch2[position].equals("日常保健")){
                    Str="SELECT * FROM ACUP WHERE (("+P+") AND subset='"+lunch2[position]+"'"+") ORDER BY favor DESC";
                }
                else if(lunch2[position].equals("減肥塑身")){
                    Str="SELECT * FROM ACUP WHERE (("+P+") AND subset='"+lunch2[position]+"'"+") ORDER BY favor DESC";
                }
                else{
                    Str="SELECT * FROM ACUP WHERE ("+ P +") ORDER BY favor DESC";
                }
                //Toast.makeText(eat.this, str, Toast.LENGTH_SHORT).show();
                SqlQuery(Str);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}

        });

        try {
            SqlQuery(Str);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public void SqlQuery(String sql) {
        String str = "";
        int a=0;
        try {
            c = db.rawQuery(sql, null);
            str = "";
            c.moveToFirst();
            a=c.getCount();

            if(a!=0) {
                TextView title = (TextView) findViewById(R.id.title);
                title.setText(c.getString(0));

                if (!c.getString(2).equals("NULL")) str += c.getString(2) + "\n\n";
                if (!c.getString(3).equals("NULL")) str += c.getString(3) + "\n\n";
                if (!c.getString(4).equals("NULL")) str += c.getString(4) + "\n\n";
                if (!c.getString(5).equals("NULL")) str += c.getString(5) + "\n\n";
                if (!c.getString(6).equals("NULL")) str += c.getString(6) + "\n\n";
                if (!c.getString(7).equals("NULL")) str += c.getString(7) + "\n\n";
                if (!c.getString(8).equals("NULL")) str += c.getString(8) + "\n\n";
                TextView article = (TextView) findViewById(R.id.article);
                article.setText(str);
                TextView link=(TextView)findViewById(R.id.link);
                if(!c.getString(13).equals("NULL")){
                    link.setText(c.getString(13));
                }
                else link.setText("");

                TextView note =(TextView)findViewById(R.id.note);
                String N="";
                if((!c.getString(12).equals("NULL"))){
                    note.setTextSize(size);
                    N=c.getString(12)+"\n";
                    note.setText(N);
                }
                else{
                    note.setTextSize(1);
                    note.setText("");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
        if(a!=0){
            ImageButton star = (ImageButton) findViewById(R.id.star);
            if (c.getString(9).equals("1")) star.setImageResource(R.drawable.star2);
            else star.setImageResource(R.drawable.star1);
        }
        if(a==0){
            Toast.makeText(this,"找不到符合條件的資料QwQ",Toast.LENGTH_SHORT).show();
            Spinner spinner2=(Spinner)findViewById(R.id.spinner_select);
            spinner2.setSelection(0);
            Str="SELECT * FROM EAT WHERE ("+ P +") ORDER BY favor DESC";
            SqlQuery(Str);
        }
        //c.moveToNext();
    }

    public void Click_Next(View view){
        //if (c.isLast())c.moveToFirst();
        if(c.isLast())SqlQuery(Str);
        else  c.moveToNext();
        String str = "";
        TextView title=(TextView)findViewById(R.id.title);
        title.setText(c.getString(0));
        if(!c.getString(2).equals("NULL"))str += c.getString(2)+"\n\n";
        if(!c.getString(3).equals("NULL"))str += c.getString(3)+"\n\n";
        if(!c.getString(4).equals("NULL"))str += c.getString(4)+"\n\n";
        if(!c.getString(5).equals("NULL"))str += c.getString(5)+"\n\n";
        if(!c.getString(6).equals("NULL"))str += c.getString(6)+"\n\n";
        if(!c.getString(7).equals("NULL"))str += c.getString(7)+"\n\n";
        if(!c.getString(8).equals("NULL"))str += c.getString(8)+"\n\n";
        TextView article=(TextView)findViewById(R.id.article);
        article.setText(str);

        TextView link=(TextView)findViewById(R.id.link);
        if(!c.getString(13).equals("NULL")){
            link.setText(c.getString(13));
        }
        else link.setText("");

        TextView note =(TextView)findViewById(R.id.note);
        String N="";
        if((!c.getString(12).equals("NULL"))){
            note.setTextSize(size);
            N=c.getString(12)+"\n";
            note.setText(N);
        }
        else{
            note.setTextSize(1);
            note.setText("");
        }

        ImageButton star = (ImageButton)findViewById(R.id.star);
        if(c.getString(9).equals("1"))star.setImageResource(R.drawable.star2);
        else star.setImageResource(R.drawable.star1);
    }
    //加到收藏與取消收藏
    public void star_click(View view){
        ImageButton star = (ImageButton)findViewById(R.id.star);
        int f;
        TextView title=(TextView)findViewById(R.id.title);
        String id= title.getText().toString();
        Cursor tmp = db.rawQuery("SELECT favor FROM ACUP WHERE _name ='"+id+"'", null);
        tmp.moveToFirst();
        if(tmp.getString(0).equals("1"))f=0;
        else f=1;
        ContentValues cv = new ContentValues();
        cv.put("favor",f);
        db.update("ACUP",cv, "_name='"+id+"'",null);
        tmp = db.rawQuery("SELECT favor FROM ACUP WHERE _name ='"+id+"'", null);
        tmp.moveToFirst();
        if(tmp.getString(0).equals("1"))star.setImageResource(R.drawable.star2);
        else star.setImageResource(R.drawable.star1);
    }

    public void goto_M3(View view){
        Intent intent = new Intent();
        intent.setClass(acup.this,Main3Activity.class);
        startActivity(intent);
        acup.this.finish();
    }
    public void ZoomIn(View view){
        size=size+2;
        TextView tv =(TextView)findViewById(R.id.article);
        tv.setTextSize(size);
        TextView note =(TextView)findViewById(R.id.note);
        note.setTextSize(size);
    }
    public void ZoomOut(View view){
        size=size-2;
        TextView tv =(TextView)findViewById(R.id.article);
        tv.setTextSize(size);
        TextView note =(TextView)findViewById(R.id.note);
        note.setTextSize(size);
    }
}

