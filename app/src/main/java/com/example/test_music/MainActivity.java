package com.example.test_music;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.test_music.ui.home.HomeFragment;
import com.example.test_music.utils.LocalUtils;
import com.example.test_music.utils.SongList;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Logger;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppBarConfiguration mAppBarConfiguration;

    public Button bt_play_stop;
    Button bt_backforword;
    Button bt_forward;
    Button bt_module;
    TextView songnameView;
    SeekBar bar_progress;

    Boolean playing = false; //控制播放
    Boolean module = false; //控制随机播放和循环播放


    int pausePosition = 0; //歌曲暂停的位置
    int duration; //歌曲的总时长

    public SongList songList;
    public MediaPlayer mediaPlayer = new MediaPlayer();
    private ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_tools, R.id.nav_share)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        songnameView = findViewById(R.id.songnameView);

        bt_play_stop = findViewById(R.id.player_play_stop);
        bt_backforword = findViewById(R.id.player_backforward);
        bt_forward = findViewById(R.id.player_forward);
        bt_module = findViewById(R.id.player_module);
        bar_progress = findViewById(R.id.progress_bar);

        bt_play_stop.setOnClickListener(this);
        bt_backforword.setOnClickListener(this);
        bt_forward.setOnClickListener(this);
        bt_module.setOnClickListener(this);


        ArrayList<String> slist = new ArrayList<>();

        slist = new LocalUtils().getMusicPath(this);
        songList = new SongList(slist);

//        EventBus.getDefault().post(songList.getSongList());

        //进度条
        bar_progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // fromUser判断是用户改变的滑块的值
                if (fromUser == true) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.player_play_stop:
                if (playing == false) {

                    bt_play_stop.setBackgroundResource(R.drawable.player_pause);
                    play(songList.getIndexSongName());
                    mediaPlayer.seekTo(pausePosition);
                    mediaPlayer.start();
                    handler.post(updateThread);
                    playing = true;

                } else {
                    bt_play_stop.setBackgroundResource(R.drawable.player_play2);
                    pausePosition = mediaPlayer.getCurrentPosition();
                    mediaPlayer.pause();

                    playing = false;

                }
                break;

            case R.id.player_forward:
                pausePosition = 0;
                play(songList.forwardPlay());
                mediaPlayer.start();
                break;

            case R.id.player_backforward:
                pausePosition = 0;
                play(songList.backardPlay());
                mediaPlayer.start();
                break;

            case R.id.player_module:
                if (module == false) {
                    songList.setRandom(true);
                    bt_module.setBackgroundResource(R.drawable.player_random);
                    module = true;
                } else {
                    songList.setRandom(false);
                    bt_module.setBackgroundResource(R.drawable.player_circle);
                    module = false;
                }
                break;
        }


    }

    /**
     * 获取歌曲列表
     *
     * @return
     */
    public SongList getSongList() {
        return songList;
    }


    /**
     * 获取歌单列表
     *
     * @return
     */
    public ArrayList<String> getList() {
        return list;
    }

    /**
     * 设置歌单列表
     *
     * @param list
     */
    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    /**
     * 控制播放
     *
     * @param songpath
     */
    public void play(String songpath) {
        mediaPlayer.reset();
        try {

            //设置歌曲名称
            String songName = songpath.replace("/storage/emulated/0/netease/cloudmusic/Music/", "");
            songName = songName.replace(".flac", "");
            songnameView.setText(songName.replace(".mp3", ""));

            mediaPlayer.setDataSource(songpath);
            mediaPlayer.prepare();
            duration = mediaPlayer.getDuration();//获取整首song时间
            bar_progress.setMax(duration);//设置整首song的时间
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Handler handler = new Handler();
    Runnable updateThread = new Runnable() {
        public void run() {
            //获得歌曲现在播放位置并设置成播放进度条的值
            bar_progress.setProgress(mediaPlayer.getCurrentPosition());
            if (mediaPlayer.getCurrentPosition() >= (duration - 250)) {
                pausePosition = 0;
                play(songList.forwardPlay());
                mediaPlayer.start();
            }
            //每次延迟100毫秒再启动线程
            handler.postDelayed(updateThread, 100);
        }
    };


}



