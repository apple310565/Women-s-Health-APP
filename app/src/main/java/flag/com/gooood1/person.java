package flag.com.gooood1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;

public class person extends AppCompatActivity {
    //int [] Mon = {0,31,59,90,120,151,181,212,243,273,304,334};
    int[] Mon={31,28,31,30,31,30,31,31,30,31,30,31};
    int Y,M,D,peroid=29,flag=0,a,flag2=0,day1=0,day2=0,i_M;
    String account,passWD,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        try{
            FileInputStream in = openFileInput("passwd.txt");
            byte[] data = new byte[128];
            in.read(data);
            in.close();
            String line;
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
            email=name[5];
            int y2=Y;
            int m2=M;
            int d2=D+peroid;
            if(d2>Mon[m2-1]){
                d2=d2-Mon[m2-1];
                m2++;
            }
            if(m2>12){
                m2=1;
                y2++;
            }
            String Date2=Integer.toString(y2)+"/"+Integer.toString(m2)+"/"+Integer.toString(d2);
            TextView dd=(TextView)findViewById(R.id.day2);
            dd.setText(Date2);

            TextView P =(TextView)findViewById(R.id.p);
            P.setText(name[2]+"天");
            TextView Email =(TextView)findViewById(R.id.email);
            Email.setText(email);
            TextView AC =(TextView)findViewById(R.id.account);
            AC.setText(account);
            TextView Day =(TextView)findViewById(R.id.day);
            Day.setText(name[3]);

            String bb="";
            try{
                FileInputStream in2 = openFileInput("PPP.txt");
                byte[] data2 = new byte[128];
                in2.read(data2);
                in2.close();
                bb = new String(data2);
                String[] tmp = bb.split(",");
                String pefer=tmp[1];
                TextView Pefer =(TextView)findViewById(R.id.pefer);
                Pefer.setText(pefer);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        catch (IOException e){
            Toast.makeText(this,"讀檔失敗: "+e.toString(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void  goto_home(View view){
        Intent intent = new Intent();
        intent.setClass(person.this,home.class);
        startActivity(intent);
        person.this.finish();
    }
    public void  goto_m4(View view){
        Intent intent = new Intent();
        intent.setClass(person.this,m4.class);
        startActivity(intent);
        person.this.finish();
    }
    public void  goto_change(View view){
        Intent intent = new Intent();
        intent.setClass(person.this,change_person.class);
        startActivity(intent);
        person.this.finish();
    }
}
