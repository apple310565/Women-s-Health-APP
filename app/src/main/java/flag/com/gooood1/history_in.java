package flag.com.gooood1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class history_in extends AppCompatActivity {
    private SQLiteDatabase db;
    private StdDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_in);
        dbHelper = new StdDBHelper(this);
        db =dbHelper.getWritableDatabase();//開啟資料庫


        // 取得前一個Activity傳過來的資料
        Bundle bundle = this.getIntent().getExtras();
        // 將取得的Bundle資料設定
        int ID = bundle.getInt("id");

        Cursor c=db.rawQuery("Select * from history where id ='"+ID+"'",null);
        c.moveToFirst();
        String ans=c.getString(4);
        String sym=c.getString(2);
        String date=c.getString(3);
        String main=c.getString(1);

        TextView SYM=(TextView)findViewById(R.id.sym);
        SYM.setText(sym);
        TextView ANS=(TextView)findViewById(R.id.ans);
        ANS.setText(ans);
        TextView DATE_MAIN=(TextView)findViewById(R.id.date_and_main);
        DATE_MAIN.setText("★"+date+"  "+main);
    }
}
