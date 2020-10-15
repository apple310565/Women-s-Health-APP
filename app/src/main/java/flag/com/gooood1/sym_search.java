package flag.com.gooood1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class sym_search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sym_search);

        LinearLayout men = (LinearLayout)findViewById(R.id.men);
        men.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goto_men();
            }
        });
    }

    public void goto_men(){
        Intent intent = new Intent();
        intent.setClass(sym_search.this, men_sym.class);
        startActivity(intent);
        sym_search.this.finish();
    }
}
