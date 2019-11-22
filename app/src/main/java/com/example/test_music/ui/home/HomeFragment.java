package com.example.test_music.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test_music.MainActivity;
import com.example.test_music.R;
import com.example.test_music.utils.SongList;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;

public class HomeFragment extends Fragment {


    private HomeViewModel homeViewModel;
    RecyclerView recyclerView;
    SongList songList;

    SongListAdapt songListAdapt;
    MainActivity mainActivity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.recycle_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        mainActivity = (MainActivity) getActivity();
        songList = mainActivity.getSongList();
        songListAdapt = new SongListAdapt(songList.getSongList());
        recyclerView.setAdapter(songListAdapt);


        return root;

    }

//    //接收Evenbus传递来的信息
//    @Subscribe
//    public void onEvent(ArrayList<String> list) {
//        songList=list;
//
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public class SongListAdapt extends RecyclerView.Adapter<SongListAdapt.ViewHolder> {
        ArrayList<String> list;

        public SongListAdapt(ArrayList<String> list) {
            this.list = list;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView text_list;
            Button bt_up;
            Button bt_down;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                text_list = itemView.findViewById(R.id.list_text);
                bt_up = itemView.findViewById(R.id.button_up);
                bt_down = itemView.findViewById(R.id.button_down);
                text_list.setClickable(true);

            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
            ViewHolder holder = new ViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            System.out.println("songNAME========" + songList.getSongList().get(position));
            holder.text_list.setText(songList.getSongList().get(position));
            //点击播放歌曲
            holder.text_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.songList.changIndex(position);
                    String songName = mainActivity.songList.getIndexSongName();
                    mainActivity.play(songName);
                    mainActivity.mediaPlayer.start();
                    mainActivity.bt_play_stop.setBackgroundResource(R.drawable.player_pause);
                }
            });
            //歌曲向上移动
            holder.bt_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.songList.moveSong(songList.getIndex(), songList.getIndex() - 1);
                    recyclerView.setAdapter(songListAdapt);
                }
            });

            //歌曲向下移动
            holder.bt_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.songList.moveSong(songList.getIndex(), songList.getIndex() + 1);
                    recyclerView.setAdapter(songListAdapt);
                }
            });


        }

        @Override
        public int getItemCount() {
            return songList.getSongList().size();
        }


    }
}
