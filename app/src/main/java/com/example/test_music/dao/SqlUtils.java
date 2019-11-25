package com.example.test_music.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SqlUtils {

    /**
     * 歌单的操作
     */
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    /**
     * 构造函数用于同android数据库操作的接口统一
     *
     * @param context Context
     * @param name    String
     * @param factory SQLiteDatabase.CursorFactory
     * @param version Integer
     */
    public SqlUtils(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        this.dbHelper = new MyDatabaseHelper(context, name, factory, version);
        this.db = this.dbHelper.getWritableDatabase();
    }


    /**
     * 插入一首歌曲到某个歌单
     *
     * @param listname
     * @param songname
     */
    public void insertValue(String listname, String songname) {
        ContentValues con = new ContentValues();
        try {
            System.out.println("插入成功1 !");
            con.put("listname", listname);
            con.put("songname", songname);
            db.insert("songlist", null, con);
//            System.out.println("Insert Successful !");
            System.out.println("插入成功2 !");


        } catch (Exception e) {
            System.out.println("ERROR : Sql Insert Failed ");
        }
    }

    /**
     * 删除某个歌单的某个歌曲
     *
     * @param listname
     * @param songname
     */
    public void deleteSong(String listname, String songname) {
        try {
            db.delete("songlist", "listname = ? and songname = ?", new String[]{listname, songname});
            System.out.println("Delete " + songname + " Successful !");
        } catch (Exception e) {
            System.out.println("ERROR : Sql delete " + listname + " " + songname + "Failed ");
        }
    }

    /**
     * 删除某个歌单
     *
     * @param listname
     */
    public void deleteList(String listname) {
        try {
            db.delete("songlist", "listname = ?", new String[]{listname});
            System.out.println("Delete " + listname + " Successful !");
        } catch (Exception e) {
            System.out.println("ERROR : Sql delete " + listname + " Failed ");
        }
    }

    /**
     * 查询所有歌单名称
     *
     * @return ArrayList<String>
     */
    public ArrayList<String> selectList() {
        ArrayList<String> list = new ArrayList<>();
        try {
            Cursor cursor = db.query("songlist", new String[]{"listname"}, null, null, "listname", null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String listname = cursor.getString(cursor.getColumnIndex("listname"));
                list.add(listname);
                cursor.moveToNext();

            }
            return list;
        } catch (Exception e) {
            System.out.println("ERROR : Sql Select ListName Failed ");
            return null;
        }

    }


    /**
     * 查询某歌单下所有歌曲名称
     *
     * @param listname
     * @return ArrarList<String>
     */
    public ArrayList<String> selectSongName(String listname) {
        ArrayList<String> list = new ArrayList<>();
        try {
            Cursor cursor = db.query("songlist", new String[]{"songname"}, "listname=?", new String[]{listname}, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String songname = cursor.getString(cursor.getColumnIndex("songname"));
                list.add(songname);
                cursor.moveToNext();
            }
            return list;
        } catch (Exception e) {
            System.out.println("ERROR : Sql Select SongName Failed ");
            return null;
        }
    }

}
