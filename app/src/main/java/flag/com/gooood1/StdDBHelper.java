package flag.com.gooood1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.FileInputStream;
import android.content.Context;

public class StdDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Class";
    private static final int DATABASE_VERSION = 1;
    public StdDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        /*經期紀錄相關資料*/
        db.execSQL("CREATE TABLE Data(_date Text primary key, "+
                "flow int , color int , quality int , isStart int , isEnd int, carry int)");
        db.execSQL("CREATE TABLE Data2(_date Text primary key, "+
                "ch1 boolean , ch2 boolean , ch3 boolean , ch4 boolean, ch5 boolean,ch6 boolean,ch7 boolean,ch8 boolean,ch9 boolean,ch10 boolean)");
        db.execSQL("CREATE TABLE Ch2(_date Text primary key, "+
                " a1 boolean , a2 boolean , a3 boolean , a4 boolean, a5 boolean,a6 boolean,a7 boolean)");
        db.execSQL("CREATE TABLE Ch3(_date Text primary key, "+
                " a1 boolean , a2 boolean , a3 boolean)");
        db.execSQL("CREATE TABLE Ch4(_date Text primary key, "+
                " a1 boolean , a2 boolean , a3 boolean)");
        db.execSQL("CREATE TABLE Ch5(_date Text primary key, "+
                " a1 boolean , a2 boolean , a3 boolean,a4 boolean)");
        db.execSQL("CREATE TABLE Ch6(_date Text primary key, "+
                " a1 boolean , a2 boolean , a3 boolean)");
        db.execSQL("CREATE TABLE Period(_date Text primary key, "+
                " p int)");

        /*健康資訊*/
        db.execSQL("CREATE TABLE EAT(_name Text primary key, "+
                "subset Text ,effect Text , method Text , who Text , main Text , main2 Text ,favor int,P int,D int,Source Text)");
        db.execSQL("CREATE TABLE ACUP(_name Text primary key, "+
                "subset Text ,effect Text , who Text , main Text , main2 Text, method Text ,theory Text,Source Text, favor int,P int,D int,note Text)");
        db.execSQL("CREATE TABLE SPORT(_name Text primary key, "+
                "subset1 Text , subset2 Text, effect Text , who Text , main Text , main2 Text, method Text ,theory Text,Source Text, favor int,P int,D int)");
        db.execSQL("CREATE TABLE HABIT(_name Text primary key, "+
                "subset Text , priority int ,P int,D int)");

        /*自我管理*/
        db.execSQL("CREATE TABLE Day(_ID Text primary key,name Text, article Text ,P int,D int,priority int,max int, selected int)");
        db.execSQL("CREATE TABLE DayIn(_ID Text , _date Text, progress int, complete real, d1 Text, d2 Text, d3 Text, d4 Text, d5 Text, d6 Text, d7 Text"+
                ",primary key(_ID,_date))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int Version){
        db.execSQL("DROP TABLE IF EXISTS Data");
        onCreate(db);
    }
}

