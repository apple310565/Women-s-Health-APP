package flag.com.gooood1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {
    private SQLiteDatabase db;
    private StdDBHelper dbHelper;
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        //建立SQLOHleper物件
        dbHelper = new StdDBHelper(this);
        db =dbHelper.getWritableDatabase();//開啟資料庫
        LinearLayout mm = (LinearLayout)findViewById(R.id.linearLayout6);
        mm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goto_EAT();
            }
        });

        TextView EAT = (TextView)findViewById(R.id.eat);
        SqlQuery("SELECT _name FROM EAT");
        EAT.setText(str);
    }

    public void goto_EAT(){
        Intent intent = new Intent();
        intent.setClass(Main3Activity.this, eat.class);
        startActivity(intent);
        Main3Activity.this.finish();
    }

    public void SqlQuery(String sql) {
        try {
            Cursor c= db.rawQuery(sql, null);
            str = "";
            c.moveToFirst();
            while(!c.isLast()){
                str+=c.getString(0)+"、";
                c.moveToNext();
            }
            str+=c.getString(0);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
}
