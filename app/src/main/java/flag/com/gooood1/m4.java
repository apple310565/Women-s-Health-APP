package flag.com.gooood1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class m4 extends AppCompatActivity {
    private SQLiteDatabase db;
    private StdDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m4);
        //建立SQLOHleper物件
        dbHelper = new StdDBHelper(this);
        db =dbHelper.getWritableDatabase();//開啟資料庫
        SqlQuery("SELECT * FROM Data ORDER BY _date DESC");

    }
    public void SqlQuery(String sql) {
        String[] colNames;
        String[] flow={" 多 ","正常"," 少 ","或多或少","點滴性出血"};
        String[] color={"黝深","鮮紅","淡紅","紫紅","紫黑"};
        String[] q={"清稀","正常","黏稠","有血塊","夾雜黏液"};
        String str = "",str1="";
        Cursor c = db.rawQuery(sql, null);
        colNames = c.getColumnNames();
        str1 += "\t\t\t 日期 \t\t\t\t|  流量\t|  經色\t|  質地\t|  其它";
        TextView T_t= (TextView)findViewById(R.id.title) ;
        T_t.setText(str1);
        c.moveToFirst();
        int tmp;
        for (int i = 0; i < c.getCount(); i++) {
            str += c.getString(0) + "\t|  ";
            tmp=Integer.parseInt(c.getString(1));
            if(tmp!=-1 && tmp!=0)str += flow[tmp-1] + "\t|  ";
            else str+="null\t|  ";
            tmp=Integer.parseInt(c.getString(2));
            if(tmp!=-1 && tmp!=0 )str += color[tmp-1] + "\t|  ";
            else str+="null\t|  ";
            tmp=Integer.parseInt(c.getString(3));
            if(tmp!=-1 && tmp!=0)str += q[tmp-1] + "\t";
            else str+="null\t";

            if(c.getString(4).equals("1"))str += "|  "+"經期開始"+ "\t";
            if(c.getString(5).equals("1"))str += "|  "+"經期結束";
            str +=  "\n";
            c.moveToNext();
        }
        TextView tv =(TextView)findViewById(R.id.Tv);
        tv.setText(str);
    }
    public void  goto_home(View view){
        Intent intent = new Intent();
        intent.setClass(m4.this,person.class);
        startActivity(intent);
        m4.this.finish();
    }
    public void  goto_graph(View view){
        Intent intent = new Intent();
        intent.setClass(m4.this,graph.class);
        startActivity(intent);
    }
}
