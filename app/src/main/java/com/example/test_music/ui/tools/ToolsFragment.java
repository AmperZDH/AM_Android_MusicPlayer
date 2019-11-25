package com.example.test_music.ui.tools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test_music.R;
import com.example.test_music.utils.SongList;

import java.util.ArrayList;

public class ToolsFragment extends Fragment implements View.OnClickListener {

    private ToolsViewModel toolsViewModel;
    EditText editText;
    Button bt_add;
    RecyclerView recyclerView;
    ListAdapt listAdapt;

    ArrayList<String> list = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        recyclerView = root.findViewById(R.id.recycle_listname);
        editText = root.findViewById(R.id.text_inputListName);
        bt_add = root.findViewById(R.id.button_createList);
        bt_add.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        listAdapt = new ListAdapt(list);
        recyclerView.setAdapter(listAdapt);
        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_createList:
                String newlistName = editText.getText().toString();
                if (newlistName == "")
                    break;
//                System.out.println("newlistName == " + newlistName);
                list.add(newlistName);
//                listAdapt = new ListAdapt(list);
                recyclerView.setAdapter(listAdapt);
                break;
        }
    }

    //创建适配器
    public class ListAdapt extends RecyclerView.Adapter<ListAdapt.ViewHolder> {
        public ListAdapt(ArrayList<String> list) {
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            TextView viewlistname;
            Button bt_del;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                viewlistname = itemView.findViewById(R.id.list_listname);
                bt_del = itemView.findViewById(R.id.button_dellist);
                viewlistname.setClickable(true);
            }
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.list_list, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
//            if (!list.isEmpty()) {
                holder.viewlistname.setText(list.get(position));

                //编辑歌单
                holder.viewlistname.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                //删除歌单
                holder.bt_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        list.remove(position);
                        listAdapt = new ListAdapt(list);
                        recyclerView.setAdapter(listAdapt);
                    }
                });

        }

        @Override
        public int getItemCount() {
            System.out.println("list.size()========="+list.size());
            return list.size();
        }


    }
}