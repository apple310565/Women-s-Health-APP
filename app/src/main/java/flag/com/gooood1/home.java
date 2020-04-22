package flag.com.gooood1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;

public class home extends AppCompatActivity {
    int [] Mon = {0,31,59,90,120,151,181,212,243,273,304,334};
    int Y,M,D,peroid=29,flag=0,day1=0,day2=0,i_M,mense;
    String account,passWD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //若是第一次登入沒有帳密，則切換成註冊頁面
        String line="";
        try{
            FileInputStream in = openFileInput("passwd.txt");
            byte[] data = new byte[128];
            in.read(data);
            in.close();
            line = new String(data);

            String[] name = line.split(",");
            String [] Date = name[3].split("/");
            Y=Integer.parseInt(Date[0]);
            M=Integer.parseInt(Date[1]);
            D=Integer.parseInt(Date[2]);
            peroid = Integer.parseInt(name[2]);
            flag=1;
            account=name[0];
            passWD=name[1];
            i_M=Integer.parseInt(name[4]);
        }
        catch (IOException e){
            new AlertDialog.Builder(home.this)
                    .setIcon(R.drawable.ic_launcher_background)
                    .setTitle("Message")
                    .setMessage("第一次使用請先設置帳密喔")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setClass(home.this, Main2Activity.class);
                            startActivity(intent);
                            home.this.finish();
                        }
                    })
                    .show();
        }
        if(flag==1){
            //初始化
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month =  calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            day1 = Mon[month]+day;
            day2 = Mon[M-1]+D;
            int days = (year-Y)*365+(day1-day2);
            mense =peroid - (days%peroid);
            if(mense>=peroid)mense=mense-peroid;
            if(is_mense()==0)out_memse();
            else in_memse();
        }
    }

    public int is_mense(){
        if(day1>=day2){
            if(i_M==-1)return 1;
            else if(i_M>=(day1-day2))return 1;
        }
        return 0;
    }

    public void in_memse(){
        Button bt1 = (Button)findViewById(R.id.button3);
        bt1.setText("經期中");
        TextView day = (TextView) findViewById(R.id.textView2);
        int days=day1-day2;
        day.setText(Integer.toString(days+1));
    }

    public void out_memse(){
        Button bt1 = (Button) findViewById(R.id.button3);
        bt1.setText("經期倒數");
        TextView day = (TextView) findViewById(R.id.textView2);
        day.setText(Integer.toString(mense));
        if(day1-day2>peroid&&day1-day2<peroid+15){
            bt1.setText("經期晚到");
            day.setText(Integer.toString(day1-day2-peroid));
        }
    }

    public void goto_M1(View view){
        Intent intent = new Intent();
        intent.setClass(home.this, MainActivity.class);
        startActivity(intent);
    }
    public void button_click(View view){
        Toast.makeText(this,"還未完工，敬請期待~ (,,・ω・,,)",Toast.LENGTH_SHORT).show();
    }
}
