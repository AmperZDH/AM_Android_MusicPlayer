package com.example.test_music.utils;


import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;


public class SongList {


    /**
     * 控制歌单的各类操作
     */
    private ArrayList<String> qlist = new ArrayList<>();
    private int index = 0; //当前播放的歌曲
    private boolean isRandom = false; //判断当前是否是随机播放


    public SongList() {

    }

    public SongList(ArrayList<String> list) {
        this.qlist = list;
    }

    /**
     * 获取当前播放的索引
     *
     * @return
     */
    public String getIndexSongName() {
        return this.qlist.get(this.index);
    }

    /**
     * 获取当前歌曲的索引
     *
     * @return
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * 改变当前歌曲的播放位置
     *
     * @param index
     */
    public void changIndex(int index) {
        this.index = index;
    }

    /**
     * 获取整个歌单
     *
     * @return
     */
    public ArrayList<String> getSongList() {
        return qlist;
    }


    /**
     * 添加一首歌曲
     *
     * @param songName String
     */
    public void addSong(String songName) {
        qlist.add(songName);
    }


    /**
     * 删除一个歌曲
     *
     * @param songName String
     */
    public void removeSong(String songName) {
        qlist.remove(songName);
    }


    /**
     * 移动一首歌曲的位置
     *
     * @param oldIndex int
     * @param newIndex int
     */
    public void moveSong(int oldIndex, int newIndex) {
        if (newIndex < 0) {
            String old = qlist.get(oldIndex);
            qlist.remove(oldIndex);
            qlist.add(old);
        } else if (newIndex > qlist.size()) {
            String old = qlist.get(oldIndex);
            qlist.remove(oldIndex);
            qlist.add(0, old);
        } else {
            String old = qlist.get(oldIndex);
            qlist.remove(oldIndex);
            qlist.add(newIndex, old);
        }
    }


    /**
     * 播放下一首歌曲
     *
     * @return
     */
    public String forwardPlay() {
        if (isRandom)
            return randomPlay();
        if (index + 1 < qlist.size())
            index = index + 1;
        else
            index = 0;

        String songName = qlist.get(index);
        return songName;
    }

    /**
     * 播放上一首歌曲
     *
     * @return
     */
    public String backardPlay() {
        if (isRandom)
            return randomPlay();
        if (index - 1 >= 0)
            index = index - 1;

        else
            index = qlist.size() + 1;
        String songName = qlist.get(index);
        return songName;
    }

    /**
     * 设置是否随机播放
     *
     * @param random
     */
    public void setRandom(boolean random) {
        isRandom = random;
    }

    /**
     * 随机播放一首歌
     *
     * @return
     */
    public String randomPlay() {
        int num = (int) (Math.random() * qlist.size());
        System.out.println(num);
        return qlist.get(num);
    }

    /**
     * 重新设置播放的歌单
     *
     * @param list
     */
    public void setSongList(ArrayList<String> list) {
        this.qlist = list;
        this.index = 0;
    }
}
