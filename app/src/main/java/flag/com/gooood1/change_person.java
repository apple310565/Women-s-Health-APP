package flag.com.gooood1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class change_person extends AppCompatActivity {
    String line,account,passWD,email,P,sub,select;
    int Y,M,D,peroid,i_M,flag,F;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_person);
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
            email=name[5];
        }
        catch (IOException e){

        }

        String bb="";
        try{
            FileInputStream in = openFileInput("PPP.txt");
            byte[] data = new byte[128];
            in.read(data);
            in.close();
            bb = new String(data);
            String[] tmp = bb.split(",");
            P=tmp[0];
            sub = tmp[1];
            if(sub.equals("日常保健"))F=0;
            else F=1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        EditText Account = (EditText) findViewById(R.id.account);
        Account.setText(account);

        EditText P = (EditText) findViewById(R.id.P);
        P.setText(Integer.toString(peroid));

        Spinner spinner2 = (Spinner)findViewById(R.id.spinner2);
        final String[] lunch2 = {"日常保健", "減肥塑身"};
        ArrayAdapter<String> lunchList2 = new ArrayAdapter<>(change_person.this,
                android.R.layout.simple_spinner_dropdown_item,
                lunch2);spinner2.setAdapter(lunchList2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select=lunch2[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        spinner2.setSelection(F);
    }

    public void submit_click(View view){
        EditText Name = (EditText)findViewById(R.id.account);
        EditText M2= (EditText) findViewById(R.id.P);

        String Digit = "[0-9]+";
        String RE = "([0-9]|[a-z]|[A-Z])+";
        //很長我知道，網路上找的email的RE
        String RE2 = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        if(Name.getText().toString().equals("")||M2.getText().toString().equals("")) {
            new AlertDialog.Builder(change_person.this)
                    .setIcon(R.drawable.ic_launcher_background)
                    .setTitle("Message")
                    .setMessage("不可以有資料為空喔 (*ﾟ∀ﾟ*)")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
        else if(!M2.getText().toString().matches(Digit)){
            new AlertDialog.Builder(change_person.this)
                    .setIcon(R.drawable.ic_launcher_background)
                    .setTitle("Message")
                    .setMessage("經期週期內必須填數字，若是不確定可以直接填29呦(*´▽`*)")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
        else if(!Name.getText().toString().matches(RE)){
            new AlertDialog.Builder(change_person.this)
                    .setIcon(R.drawable.ic_launcher_background)
                    .setTitle("Message")
                    .setMessage("帳號和密碼只能由中英文組成喔(*´▽`*)")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
        else {
            //把資料寫回"PPP.txt"
            FileOutputStream pout = null;
            String str="";
            str=P+","+select+",";
            try {
                pout = openFileOutput("PPP.txt",MODE_PRIVATE);
                pout.write(str.getBytes());
                pout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //把資料寫回"passWD.txt"
            account=Name.getText().toString();
            peroid=Integer.parseInt(M2.getText().toString());
            String str2=account+","+passWD+","+peroid+","+Y+"/"+M+"/"+D+","+i_M+","+email+",";
            try {
                FileOutputStream out = openFileOutput("passwd.txt",MODE_PRIVATE);
                out.write(str2.getBytes());
                out.close();
                //Toast.makeText(this,"成功寫入檔案",Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this,"文件创建失败"+e.toString(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            Toast.makeText(this,"資料修改成功 ヽ(✿ﾟ▽ﾟ)ノ",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent();
            intent.setClass(change_person.this,person.class);
            startActivity(intent);
            change_person.this.finish();
        }
    }
    public void  goto_home(View view){
        Intent intent = new Intent();
        intent.setClass(change_person.this,person.class);
        startActivity(intent);
        change_person.this.finish();
    }
}
