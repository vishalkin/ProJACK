package com.gu.projack.projack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vishal on 10/12/2016.
 */
public class SqlDbHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION=1;
    private static final String DB_NAME="scrum_manager";
    String TABLE_PROJECT="projects";

    String KEY_PROJECT_ID = "_id";
    String KEY_PROJECT_NAME = "project_name";
    String KEY_PROJECT_DESC = "project_desc";
    String KEY_PROJECT_TYPE = "project_type";
    String KEY_PROJECT_START_DATE = "start_date";
    String KEY_PROJECT_END_DATE = "end_date";


    public SqlDbHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    public SqlDbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PROJECT_TABLE = "CREATE TABLE " + TABLE_PROJECT + "(" + KEY_PROJECT_ID + " INTEGER PRIMARY KEY, " + KEY_PROJECT_NAME + " TEXT, " + KEY_PROJECT_DESC + " TEXT, " + KEY_PROJECT_TYPE + " TEXT, " + KEY_PROJECT_START_DATE + " TEXT, " + KEY_PROJECT_END_DATE + " TEXT)";
        db.execSQL(CREATE_PROJECT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //drop table if exists
        db.execSQL("DROP TABLE "+ TABLE_PROJECT);

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
/*
    public List<SqlProjects> getAllProjects(){
        List<SqlProjects> post_list = new ArrayList<SqlProjects>();

        String selectQuery = "SELECT * FROM " + TABLE_PROJECT;
        SQLiteDatabase db = this.getWritableDatabase();

        //cursor exposes results from db query.
        Cursor cursor = db.rawQuery(selectQuery,null);

        //Looping thru all rows and adding to list
        //move cursor to first row

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
        //return blog entry list.
        return post_list;
    }
*/
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



}
