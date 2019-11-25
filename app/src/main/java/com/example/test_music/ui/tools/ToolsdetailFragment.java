package com.example.test_music.ui.tools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.test_music.R;

public class ToolsdetailFragment extends Fragment {
    private String listname="";

    TextView textView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tools_detail, container, false);
        textView=root.findViewById(R.id.test_text);
        textView.setText(listname);
        return root;
    }

    public void setListname(String listname) {
        this.listname = listname;
    }
}
