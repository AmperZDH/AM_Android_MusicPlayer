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

import com.example.test_music.R;
import com.example.test_music.dao.SqlUtils;
import com.example.test_music.ui.home.HomeFragment;

import java.util.ArrayList;

public class ToolsdetailFragment extends Fragment {
    private String listname = "";

    RecyclerView recyclerView;
    SqlUtils sql;
    SongNameAdapter songListAdapt;
    ArrayList<String> songNameList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tools_detail, container, false);
        sql = new SqlUtils(getActivity(), "Songlist.db", null, 1);
        System.out.println("The List name ======== " + listname);




        recyclerView = root.findViewById(R.id.recycle_collection);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        songListAdapt = new SongNameAdapter();
        recyclerView.setAdapter(songListAdapt);

        songNameList=sql.selectSongName(listname);
        return root;
    }

    public void setListname(String listname) {
        this.listname = listname;
    }

    public class SongNameAdapter extends RecyclerView.Adapter<SongNameAdapter.ViewHolder> {


        class ViewHolder extends RecyclerView.ViewHolder {
            TextView viewSongName;
            Button bt_rm;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                viewSongName = itemView.findViewById(R.id.text_collection);
                bt_rm = itemView.findViewById(R.id.button_collection);
            }
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.list_collection, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.viewSongName.setText(songNameList.get(position));
        }

        @Override
        public int getItemCount() {
            System.out.println("songNameList===========" + songNameList.size());
            return songNameList.size();
        }
    }
}
