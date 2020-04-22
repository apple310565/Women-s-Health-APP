package flag.com.gooood1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StdDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Class";
    private static final int DATABASE_VERSION = 1;
    public StdDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE Data(_date Text primary key, "+
                "flow int , color int , quality int , isStart int , isEnd int)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int Version){
        db.execSQL("DROP TABLE IF EXISTS Data");
        onCreate(db);
    }
}

