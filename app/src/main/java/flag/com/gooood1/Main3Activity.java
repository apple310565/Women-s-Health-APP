package flag.com.gooood1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        LinearLayout mm = (LinearLayout)findViewById(R.id.linearLayout6);
        mm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goto_EAT();
            }
        });
    }

    public void goto_EAT(){
        Intent intent = new Intent();
        intent.setClass(Main3Activity.this, eat.class);
        startActivity(intent);
    }
}
