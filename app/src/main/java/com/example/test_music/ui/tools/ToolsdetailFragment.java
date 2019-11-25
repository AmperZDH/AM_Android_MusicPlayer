package com.example.test_music.ui.tools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test_music.MainActivity;
import com.example.test_music.R;
import com.example.test_music.dao.SqlUtils;
import com.example.test_music.ui.home.HomeFragment;

import java.util.ArrayList;

public class ToolsdetailFragment extends Fragment {
    private String listname = "";

    RecyclerView recyclerView;
    SqlUtils sql;
    SongNameAdapter songListAdapt;
    MainActivity mainActivity;
    ArrayList<String> songNameList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tools_detail, container, false);
        sql = new SqlUtils(getActivity(), "Songlist.db", null, 1);
        mainActivity = (MainActivity) getActivity();

        recyclerView = root.findViewById(R.id.recycle_collection);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        songNameList = sql.selectSongName(listname);
        songListAdapt = new SongNameAdapter();
        recyclerView.setAdapter(songListAdapt);


        return root;
    }

    public void setListname(String listname) {
        this.listname = listname;
    }

    public class SongNameAdapter extends RecyclerView.Adapter<SongNameAdapter.ViewHolder> {


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView viewSongName;
            Button bt_rm;

            public ViewHolder(View itemView) {
                super(itemView);
                viewSongName = itemView.findViewById(R.id.text_collection);
                bt_rm = itemView.findViewById(R.id.button_delCollection);
                viewSongName.setClickable(true);
            }
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.list_collection, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            final String path = songNameList.get(position);
            String name = path.replace("/storage/emulated/0/netease/cloudmusic/Music/", "");
            name = name.replace(".mp3", "");
            name = name.replace(".flac", "");
            holder.viewSongName.setText(name);

            //点击歌单歌曲播放
            holder.viewSongName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.songList.setSongList(songNameList);
                    mainActivity.play(songNameList.get(position));
                }
            });

            //删除歌单内某歌曲
            holder.bt_rm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sql.deleteSong(listname, path);
                    recyclerView.setAdapter(songListAdapt);
                }
            });

        }

        @Override
        public int getItemCount() {
            System.out.println("songNameList===========" + songNameList.size());
            return songNameList.size();
        }
    }
}
