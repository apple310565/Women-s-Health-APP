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
        LinearLayout breast = (LinearLayout)findViewById(R.id.breast);
        breast.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goto_breast();
            }
        });
        LinearLayout geni = (LinearLayout)findViewById(R.id.geni);
        geni.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goto_geni();
            }
        });
        LinearLayout post = (LinearLayout)findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goto_post();
            }
        });
    }

    public void goto_men(){
        Intent intent = new Intent();
        intent.setClass(sym_search.this, men_sym.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", "月經病");
        intent.putExtras(bundle);
        startActivity(intent);
        sym_search.this.finish();
    }
    public void goto_breast(){
        Intent intent = new Intent();
        intent.setClass(sym_search.this, men_sym.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", "乳病");
        intent.putExtras(bundle);
        startActivity(intent);
        sym_search.this.finish();
    }
    public void goto_geni(){
        Intent intent = new Intent();
        intent.setClass(sym_search.this, men_sym.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", "前陰疾病");
        intent.putExtras(bundle);
        startActivity(intent);
        sym_search.this.finish();
    }
    public void goto_post(){
        Intent intent = new Intent();
        intent.setClass(sym_search.this, men_sym.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", "產後病");
        intent.putExtras(bundle);
        startActivity(intent);
        sym_search.this.finish();
    }
    public void goto_home(View view){
        Intent intent = new Intent();
        intent.setClass(sym_search.this, home.class);
        startActivity(intent);
        sym_search.this.finish();
    }

}
