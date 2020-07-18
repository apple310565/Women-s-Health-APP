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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class Day_in extends AppCompatActivity {
    private SQLiteDatabase db;
    private StdDBHelper dbHelper;
    int DayOfweek;
    String [] week ={"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
    String ID;
    String Date;
    String name;
    String article;
    int Max;
    int p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_in);

        //建立SQLOHleper物件
        dbHelper = new StdDBHelper(this);
        db =dbHelper.getWritableDatabase();//開啟資料庫

        //取得本日是星期幾
        Calendar cd = Calendar.getInstance();
        DayOfweek=cd.get(Calendar.DAY_OF_WEEK);
        if(DayOfweek==1)DayOfweek=7;
        else DayOfweek--;


        // 取得前一個Activity傳過來的資料
        Bundle bundle = this.getIntent().getExtras();
        // 將取得的Bundle資料設定
        ID = bundle.getString("_ID");
        Date = bundle.getString("_date");
        Cursor Day=db.rawQuery("SELECT * FROM Day WHERE _ID = '" + ID + "'", null);
        Cursor DayIn = db.rawQuery("SELECT * FROM DayIn WHERE _ID = '" + ID + "' AND _date = '" + Date + "'", null);
        Day.moveToFirst();
        DayIn.moveToFirst();

        name = Day.getString(1);
        article=Day.getString(2);
        String max=Day.getString(6);

        String progress =DayIn.getString(2);
        TextView Name= (TextView)findViewById(R.id.Name);
        TextView Art=(TextView)findViewById(R.id.article);
        TextView complete=(TextView)findViewById(R.id.Complete);
        Name.setText(name);
        Art.setText(article);
        complete.setText(progress+"/"+max);

        ImageView im =(ImageView)findViewById(R.id.IM);
        Max=Integer.parseInt(max);
        p=Integer.parseInt(progress);
        float i = ((float) Max/4);
        if(p==Max)im.setImageDrawable(getResources().getDrawable( R.drawable.r5 ));
        else if(p>=Max-(int)(i))im.setImageDrawable(getResources().getDrawable( R.drawable.r4 ));
        else if(p>=Max-(int)(2*i))im.setImageDrawable(getResources().getDrawable( R.drawable.r3 ));
        else if(p>=Max-(int)(3*i))im.setImageDrawable(getResources().getDrawable( R.drawable.r2 ));
        else im.setImageDrawable(getResources().getDrawable( R.drawable.r1 ));

        TextView Note=(TextView)findViewById(R.id.note);
        String note="";
        int j;
        for( j=4;j<11;j++){
            if(!DayIn.getString(j).equals("NULL")){
                note+=week[j-4]+" | "+DayIn.getString(j)+"\n";
            }
        }
        if(note.equals("")){
            Note.setText("目前還沒有任何紀錄，趕快來寫下給自己的鼓勵吧 > <");
        }
        else Note.setText(note);

        //圖表內容設定
        Cursor DayIn2 = db.rawQuery("SELECT * FROM DayIn WHERE _ID = '" + ID + "' ORDER BY _date", null);
        DayIn2.moveToFirst();
        com.github.mikephil.charting.charts.LineChart lineChart = findViewById(R.id.lineChart);
        try{
        //x軸
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//X軸標籤顯示位置(預設顯示在上方，分為上方內/外側、下方內/外側及上下同時顯示)
        xAxis.setTextColor(Color.GRAY);//X軸標籤顏色
        xAxis.setTextSize(12);//X軸標籤大小

            DayIn2.moveToFirst();
        xAxis.setLabelCount(DayIn2.getCount());//X軸標籤個數
        xAxis.setSpaceMin(0.2f);//折線起點距離左側Y軸距離
        xAxis.setSpaceMax(0.2f);//折線終點距離右側Y軸距離

        xAxis.setDrawGridLines(false);//不顯示每個座標點對應X軸的線 (預設顯示)

        //設定所需特定標籤資料
        List<String> xList = new ArrayList<>();
        //xList.add("NULL");
        for (j=0;j<DayIn2.getCount();j++) {
            xList.add(DayIn2.getString(1));
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xList));}catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
        lineChart.setBackgroundColor(Color.parseColor("#FFFFFF"));
        ArrayList<Entry> values = new ArrayList<>();
        DayIn2.moveToFirst();
        for(j =0;j<DayIn2.getCount();j++){
            values.add(new Entry((float) j, Float.parseFloat(DayIn2.getString(3))));
            DayIn2.moveToNext();
        }




        final LineDataSet set;
        set = new LineDataSet(values, "");
        set.setDrawValues(false);//不顯示座標點對應Y軸的數字(預設顯示)
        set.setCircleRadius(5);
        set.setCircleColor(Color.parseColor("#FF4D00"));
        set.setMode(LineDataSet.Mode.LINEAR);//類型為折線
        set.setColor(Color.parseColor("#FF4D00"));//線的顏色
        set.setLineWidth(1.5f);//線寬
        LineData data = new LineData(set);
        lineChart.setTag("完成度");
        lineChart.setDrawBorders(true);
        lineChart.setData(data);//一定要放在最後
        lineChart.invalidate();//繪製圖





    }


    public void goto_Day(View view){
        Intent intent = new Intent();
        intent.setClass(Day_in.this,DayDay.class);
        startActivity(intent);
        Day_in.this.finish();
    }

    public void sumbit(View view){
        EditText ET = (EditText)findViewById(R.id.ET);
        String tmp=ET.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put("d"+Integer.toString(DayOfweek),tmp);
        db.update("DayIn",cv, "_ID = '"+ID+"' AND _date = '"+Date+"'" ,null);
        ET.setText("");

        Cursor DayIn = db.rawQuery("SELECT * FROM DayIn WHERE _ID = '" + ID + "' AND _date = '" + Date + "'", null);
        DayIn.moveToFirst();
        try{
        TextView Note=(TextView)findViewById(R.id.note);
        String note="";
        int j;
        for( j=4;j<11;j++){
            if(!DayIn.getString(j).equals("NULL")&&!DayIn.getString(j).equals("")){
                note=note+week[j-4]+" | "+DayIn.getString(j)+"\n";
            }
        }
        if(note.equals("")){
            Note.setText("目前還沒有任何紀錄，趕快來寫下給自己的鼓勵吧 > <");
        }
        else Note.setText(note);}catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getApplicationContext(), "記錄新增成功(*´▽`*)y", Toast.LENGTH_SHORT).show();
    }
    public void hide(View view){
        ContentValues cv = new ContentValues();
        cv.put("selected",0);
        db.update("Day",cv, "_ID = '"+ID+"'" ,null);
        Toast.makeText(getApplicationContext(), "習慣隱藏成功，可以再從新增習慣處加回來喔(*´▽`*)y", Toast.LENGTH_SHORT).show();
    }
    public void  change(View view){
        LayoutInflater inflater = LayoutInflater.from(Day_in.this);
        final View v = inflater.inflate(R.layout.alertdialog_use, null);
        EditText Name=(EditText) (v.findViewById(R.id.A_name));
        Name.setText(name);
        EditText ART=(EditText) (v.findViewById(R.id.A_art));
        ART.setText(article);
        EditText MAX=(EditText) (v.findViewById(R.id.A_max));
        MAX.setText(Integer.toString(Max));

        new AlertDialog.Builder(Day_in.this)
                .setIcon(R.drawable.ic_launcher_background)
                .setIcon(R.drawable.r3)
                .setTitle("修改")
                .setView(v)
                .setNegativeButton("取消更改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("確定更改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText Name=(EditText) (v.findViewById(R.id.A_name));
                        String name=Name.getText().toString();
                        EditText ART=(EditText) (v.findViewById(R.id.A_art));
                        String article=ART.getText().toString();
                        EditText MAX=(EditText) (v.findViewById(R.id.A_max));
                        String Digit = "[0-9]+";
                        if(!MAX.getText().toString().matches(Digit)){
                            new AlertDialog.Builder(Day_in.this)
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
                            int max = Integer.parseInt(MAX.getText().toString());

                            ContentValues cv = new ContentValues();
                            cv.put("name", name);
                            cv.put("max", max);
                            cv.put("article", article);
                            db.update("Day", cv, "_ID = '" + ID + "'", null);

                            if(max<p){
                                p=max;
                                ContentValues cv2 = new ContentValues();
                                cv2.put("progress",p);
                                cv2.put("complete",(float)p/(float)max);
                                db.update("DayIn",cv2, "_ID = '"+ID+"' AND _date = '"+Date+"'" ,null);
                            }
                            else{
                                ContentValues cv2 = new ContentValues();
                                cv2.put("complete",(float)p/(float)max);
                                db.update("DayIn",cv2, "_ID = '"+ID+"' AND _date = '"+Date+"'" ,null);
                            }

                            Toast.makeText(getApplicationContext(), "內容修改成功(*´▽`*)y", Toast.LENGTH_SHORT).show();

                            //goto_DayIn
                            Intent intent = new Intent();
                            intent.setClass(Day_in.this,Day_in.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("_ID", ID);
                            bundle.putString("_date", Date);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            Day_in.this.finish();
                        }

                    }
                })

                .show();
    }
}
