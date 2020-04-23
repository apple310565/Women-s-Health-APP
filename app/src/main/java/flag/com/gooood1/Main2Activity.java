package flag.com.gooood1;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class Main2Activity extends AppCompatActivity {
    private EditText editDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        editDate = (EditText)findViewById(R.id.m1);
    }

    public void datePicker(View view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month =  calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dateTime = String.valueOf(year)+"/"+String.valueOf(month+1)+"/"+String.valueOf(dayOfMonth);
                editDate.setText(dateTime);
            }
        },year,month,day).show();
    }

    public void submit_onclick(View view){
        EditText Name =(EditText) findViewById(R.id.name);
        EditText Passwd =(EditText) findViewById(R.id.passwd);
        EditText M1 =(EditText) findViewById(R.id.m1);
        EditText M2 =(EditText) findViewById(R.id.m2);
        EditText Email =(EditText) findViewById(R.id.email);
        String Digit = "[0-9]+";
        String RE = "([0-9]|[a-z]|[A-Z])+";
        //很長我知道，網路上找的email的RE
        String RE2 = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        if(Name.getText().toString().equals("")||Passwd.getText().toString().equals("")||M1.getText().toString().equals("")||M2.getText().toString().equals("")||Email.getText().toString().equals("")) {
            new AlertDialog.Builder(Main2Activity.this)
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
            new AlertDialog.Builder(Main2Activity.this)
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
        else if(!Passwd.getText().toString().matches(RE)||!Name.getText().toString().matches(RE)){
            new AlertDialog.Builder(Main2Activity.this)
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
        else if(!Email.getText().toString().matches(RE2)){
            new AlertDialog.Builder(Main2Activity.this)
                    .setIcon(R.drawable.ic_launcher_background)
                    .setTitle("Message")
                    .setMessage("Email的格式錯誤喔(*´▽`*)")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
        else{
            String str = Name.getText().toString()+","+Passwd.getText().toString()+","+M2.getText().toString()+","+M1.getText().toString()+","+"0,"+Email.getText().toString()+",";
            try {
                FileOutputStream out = openFileOutput("passwd.txt",MODE_PRIVATE);
                out.write(str.getBytes());
                out.close();
                Toast.makeText(this,"個人資料登入成功 ヽ(✿ﾟ▽ﾟ)ノ",Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this,"文件創建失败"+e.toString(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            Intent intent = new Intent();
            intent.setClass(Main2Activity.this  , home.class);
            startActivity(intent);
            Main2Activity.this.finish();
        }

    }

}
