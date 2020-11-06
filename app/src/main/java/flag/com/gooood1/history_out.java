package flag.com.gooood1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class history_out extends AppCompatActivity {
    private SQLiteDatabase db;
    private StdDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_out);
        dbHelper = new StdDBHelper(this);
        db =dbHelper.getWritableDatabase();//開啟資料庫
        produce();
    }

    @SuppressLint("SetTextI18n")
    public void produce(){
        LinearLayout LL=(LinearLayout)findViewById(R.id.LL);
        Cursor history=db.rawQuery("Select * from history order by id desc",null);
        history.moveToFirst();
        int n=history.getCount();
        try {
            for (int i = 0; i < n; i++) {
                //load
                int id = history.getInt(0);
                String date = history.getString(3);
                String main = history.getString(1);
                String NOTE = history.getString(5);

                //produce
                LinearLayout Lout = new LinearLayout(this);
                Lout.setPadding(20, 20, 20, 20);
                LL.addView(Lout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout Lin = new LinearLayout(this);
                Lout.addView(Lin, LinearLayout.LayoutParams.MATCH_PARENT, 350);
                Lin.setOrientation(LinearLayout.VERTICAL);
                Lin.setBackgroundColor(Color.parseColor("#A3FFFFFF"));
                LinearLayout L1 = new LinearLayout(this);
                L1.setPadding(18, 18, 18, 18);
                L1.setBackgroundColor(Color.parseColor("#FFAF96"));
                TextView No = new TextView(this);
                No.setTextColor(Color.parseColor("#000000"));
                No.setTextSize(25);
                No.setText("NO." + id);
                L1.addView(No, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                Lin.addView(L1, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout L2 = new LinearLayout(this);
                L2.setPadding(18, 18, 18, 18);
                L2.setOrientation(LinearLayout.HORIZONTAL);
                TextView DATE = new TextView(this);
                DATE.setTextColor(Color.parseColor("#000000"));
                DATE.setTextSize(18);
                DATE.setText(date+"\t\t\t\t");
                L2.addView(DATE);
                TextView MAIN = new TextView(this);
                MAIN.setTextColor(Color.parseColor("#000000"));
                MAIN.setTextSize(25);
                MAIN.setText(main);
                L2.addView(MAIN);
                Lin.addView(L2, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout L3 = new LinearLayout(this);
                L3.setPadding(18, 18, 18, 18);
                TextView note = new TextView(this);
                note.setText(NOTE);
                L3.addView(note, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                Lin.addView(L3, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                history.moveToNext();
            }
        }catch (Exception e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }
    }
}
