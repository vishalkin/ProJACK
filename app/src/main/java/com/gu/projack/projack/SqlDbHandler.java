package com.gu.projack.projack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vishal on 10/12/2016.
 */
public class SqlDbHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION=1;
    private static final String DB_NAME="scrum_manager";
    //Projects Table attrib
    String TABLE_PROJECT="projects";
    String KEY_PROJECT_ID = "_id";
    String KEY_PROJECT_NAME = "project_name";
    String KEY_PROJECT_DESC = "project_desc";
    String KEY_PROJECT_TYPE = "project_type";
    String KEY_PROJECT_START_DATE = "start_date";
    String KEY_PROJECT_END_DATE = "end_date";


    //Tasks Table attrib
    String TABLE_TASKS="tasks";
    String KEY_TASK_ID = "_id";
    String KEY_TASK_NAME = "task_name";
    String KEY_TASK_DESC = "task_desc";
    String KEY_STORY_POINT = "story_point";
    String KEY_PRIORITY = "priority";
    String KEY_STATUS = "status";
    String KEY_TPROJECT_ID = "project_id";



    public SqlDbHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PROJECT_TABLE = "CREATE TABLE " + TABLE_PROJECT + "(" + KEY_PROJECT_ID + " INTEGER PRIMARY KEY, " + KEY_PROJECT_NAME + " TEXT, " + KEY_PROJECT_DESC + " TEXT, " + KEY_PROJECT_TYPE + " TEXT, " + KEY_PROJECT_START_DATE + " TEXT, " + KEY_PROJECT_END_DATE + " TEXT)";
        Log.i("Hi","Table Created");
        //int _id, String task_name, int story_point, int priority, String status, int project_id
        String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASKS + "(" + KEY_TASK_ID + " INTEGER PRIMARY KEY, " + KEY_TASK_NAME + " TEXT,  "  + KEY_TASK_DESC + " TEXT,  " + KEY_STORY_POINT + " INTEGER, " + KEY_PRIORITY + " INTEGER, " + KEY_STATUS + " INTEGER, " + KEY_TPROJECT_ID + " INTEGER, FOREIGN KEY("+ KEY_TPROJECT_ID +") REFERENCES "+ TABLE_PROJECT +" (" + KEY_PROJECT_ID + ") )";
        Log.i("Hi","Table2 Created");
        db.execSQL(CREATE_PROJECT_TABLE);
        db.execSQL(CREATE_TASK_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //drop table if exists
        db.execSQL("DROP TABLE "+ TABLE_PROJECT);
        db.execSQL("DROP TABLE "+ TABLE_TASKS);
        //create new
        onCreate(db);
    }

    public void addProject(SqlProjects blog){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PROJECT_NAME,blog.get_project_name());
        values.put(KEY_PROJECT_DESC,blog.get_project_desc());
        values.put(KEY_PROJECT_TYPE,blog.get_project_type());
        values.put(KEY_PROJECT_START_DATE,blog.get_project_start_date());
        values.put(KEY_PROJECT_END_DATE,blog.get_project_end_date());


        db.insert(TABLE_PROJECT, null, values);
        db.close();
    }

    public Cursor fetchAllProjects() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + KEY_PROJECT_ID +" AS _id, " + KEY_PROJECT_NAME + ", "  + KEY_PROJECT_DESC + ", " + KEY_PROJECT_TYPE + ", "   + KEY_PROJECT_START_DATE + ", "  + KEY_PROJECT_END_DATE + " FROM " + TABLE_PROJECT;

        Cursor cursor = db.rawQuery(selectQuery,null);
        List<SqlProjects> post_list = new ArrayList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()){
                do {
                    SqlProjects blog_entry = new SqlProjects();
                    blog_entry.set_project_id(cursor.getInt(0));
                    blog_entry.set_project_name(cursor.getString(1));
                    blog_entry.set_project_desc(cursor.getString(2));
                    blog_entry.set_project_type(cursor.getString(3));
                    blog_entry.set_project_start_date(cursor.getString(4));
                    blog_entry.set_project_end_date(cursor.getString(5));

                    // add blog to list
                    post_list.add(blog_entry);
                }while (cursor.moveToNext());
            }
        }
        return cursor;
    }


    public void addTasks(SqlTasks blog){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TASK_NAME,blog.getTask_name());
        values.put(KEY_TASK_DESC,blog.getTask_desc());
        values.put(KEY_STORY_POINT,blog.getStory_point());
        values.put(KEY_PRIORITY,blog.getPriority());
        values.put(KEY_STATUS,blog.getStatus());
        values.put(KEY_TPROJECT_ID,blog.getProject_id());

        db.insert(TABLE_TASKS, null, values);
        db.close();
    }


    public Cursor fetchAllTasks(int project_id, int task_status) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + KEY_TASK_ID +" AS _id, " + KEY_TASK_NAME + ", "  + KEY_TASK_DESC + ", " + KEY_STORY_POINT + ", "   + KEY_PRIORITY  + " FROM " + TABLE_TASKS + " WHERE " + KEY_TPROJECT_ID + "=" + project_id + " AND " + KEY_STATUS + "=" + task_status ;

        Cursor cursor = db.rawQuery(selectQuery,null);
        List<SqlTasks> post_list = new ArrayList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()){
                do {
                    SqlTasks blog_entry = new SqlTasks();
                    blog_entry.set_id(cursor.getInt(0));
                    blog_entry.setTask_name(cursor.getString(1));
                    blog_entry.setTask_desc(cursor.getString(2));
                    blog_entry.setStory_point(Integer.parseInt(cursor.getString(3)));
                    blog_entry.setPriority(Integer.parseInt(cursor.getString(4)));

                    //blog_entry.set_project_end_date(cursor.getString(5));

                    // add blog to list
                    post_list.add(blog_entry);
                }while (cursor.moveToNext());
            }
        }
        return cursor;
    }

}
