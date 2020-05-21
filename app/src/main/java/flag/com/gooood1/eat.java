package flag.com.gooood1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class eat extends AppCompatActivity {

    private SQLiteDatabase db;
    private StdDBHelper dbHelper;
    Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat);
        //建立SQLOHleper物件
        dbHelper = new StdDBHelper(this);
        db =dbHelper.getWritableDatabase();//開啟資料庫
        //下拉式選單設定
        Spinner spinner = (Spinner)findViewById(R.id.spinner_order);
        final String[] lunch = {"默認", "簡易度", "我那麼愛你"};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>(eat.this,
                android.R.layout.simple_spinner_dropdown_item,
                lunch);spinner.setAdapter(lunchList);

        Spinner spinner2 = (Spinner)findViewById(R.id.spinner_select);
        final String[] lunch2 = {"全部","我的收藏","飲品", "藥膳"};
        ArrayAdapter<String> lunchList2 = new ArrayAdapter<>(eat.this,
                android.R.layout.simple_spinner_dropdown_item,
                lunch2);spinner2.setAdapter(lunchList2);

        SqlQuery("SELECT * FROM EAT ORDER BY favor DESC");
    }

    public void SqlQuery(String sql) {
        String str = "";
        try {
            c = db.rawQuery(sql, null);
            str = "";
            c.moveToFirst();
            TextView title=(TextView)findViewById(R.id.title);
            title.setText(c.getString(0));
            if(!c.getString(1).equals("NULL"))str += c.getString(1)+"\n\n";
            if(!c.getString(2).equals("NULL"))str += c.getString(2)+"\n\n";
            if(!c.getString(3).equals("NULL"))str += c.getString(3)+"\n\n";
            if(!c.getString(4).equals("NULL"))str += c.getString(4)+"\n\n";
            if(!c.getString(5).equals("NULL"))str += c.getString(5)+"\n\n";
            if(!c.getString(6).equals("NULL"))str += c.getString(6)+"\n\n";
            if(!c.getString(10).equals("NULL"))str += c.getString(10)+"\n\n";
            TextView article=(TextView)findViewById(R.id.article);
            article.setText(str);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
        ImageButton star = (ImageButton)findViewById(R.id.star);
        if(c.getString(7).equals("1"))star.setImageResource(R.drawable.star2);
        else star.setImageResource(R.drawable.star1);
        //c.moveToNext();
    }

    public void Click_Next(View view){
        //if (c.isLast())c.moveToFirst();
        if(c.isLast())SqlQuery("SELECT * FROM EAT ORDER BY favor DESC");
        else  c.moveToNext();
        String str = "";
        TextView title=(TextView)findViewById(R.id.title);
        title.setText(c.getString(0));
        if(!c.getString(1).equals("NULL"))str += c.getString(1)+"\n\n";
        if(!c.getString(2).equals("NULL"))str += c.getString(2)+"\n\n";
        if(!c.getString(3).equals("NULL"))str += c.getString(3)+"\n\n";
        if(!c.getString(4).equals("NULL"))str += c.getString(4)+"\n\n";
        if(!c.getString(5).equals("NULL"))str += c.getString(5)+"\n\n";
        if(!c.getString(6).equals("NULL"))str += c.getString(6)+"\n\n";
        if(!c.getString(10).equals("NULL"))str += c.getString(10)+"\n\n";
        TextView article=(TextView)findViewById(R.id.article);
        article.setText(str);
        ImageButton star = (ImageButton)findViewById(R.id.star);
        if(c.getString(7).equals("1"))star.setImageResource(R.drawable.star2);
        else star.setImageResource(R.drawable.star1);
    }
    //加到收藏與取消收藏
    public void star_click(View view){
        ImageButton star = (ImageButton)findViewById(R.id.star);
        int f;
        TextView title=(TextView)findViewById(R.id.title);
        String id= title.getText().toString();
        Cursor tmp = db.rawQuery("SELECT favor FROM EAT WHERE _name ='"+id+"'", null);
        tmp.moveToFirst();
        if(tmp.getString(0).equals("1"))f=0;
        else f=1;
        ContentValues cv = new ContentValues();
        cv.put("favor",f);
        db.update("EAT",cv, "_name='"+id+"'",null);
        tmp = db.rawQuery("SELECT favor FROM EAT WHERE _name ='"+id+"'", null);
        tmp.moveToFirst();
        if(tmp.getString(0).equals("1"))star.setImageResource(R.drawable.star2);
        else star.setImageResource(R.drawable.star1);
    }

    public void goto_M3(View view){
        Intent intent = new Intent();
        intent.setClass(eat.this,Main3Activity.class);
        startActivity(intent);
        eat.this.finish();
    }
}

