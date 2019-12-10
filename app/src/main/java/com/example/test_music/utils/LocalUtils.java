package com.example.test_music.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;

/**
 * 扫描所有的本地歌曲
 */
public class LocalUtils {
    ArrayList<String> songList = new ArrayList<>();

    public ArrayList<String> getMusicPath(Context context) {
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, MediaStore.Audio.AudioColumns.IS_MUSIC);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String str = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                if(str.contains("storage/emulated/0/Sounds/")||str.contains("/product/media/Pre-loaded/Music/"))
                    continue;
                System.out.println(str);
                songList.add(str);
            }
            cursor.close();
        }
        return songList;

    }
}
