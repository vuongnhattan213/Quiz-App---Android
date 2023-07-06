package com.example.myproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Fragment2 extends Fragment {

    public Fragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        TextView textView = view.findViewById(R.id.text_view);
        textView.setText("Cơ sở dữ liệu là một môn học đối tượng của khoa học máy tính, liên quan đến việc thiết kế, lưu trữ, khai thác và quản lý các dữ liệu và thông tin. Các chuyên gia đối với môn học này tập trung vào việc xây dựng các hệ thống cơ sở dữ liệu hiệu quả, và các công cụ để truy xuất dữ liệu và phân tích thông tin.");
        return view;
    }
}