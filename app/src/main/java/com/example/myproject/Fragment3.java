package com.example.myproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Fragment3 extends Fragment {

    public Fragment3() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        TextView textView = view.findViewById(R.id.text_view);
        textView.setText("Phát triển web là một lĩnh vực quan trọng của công nghệ thông tin, với mục đích tạo ra các trang web và ứng dụng web tương tác cho người dùng. Các nhà phát triển web sử dụng các ngôn ngữ lập trình như HTML, CSS, JavaScript và các khung (framework) phổ biến như React, Vue.js, Angular và Node.js để tạo ra các trang web tương tác, các ứng dụng web, các dịch vụ web hoàn chỉnh và những trang web điện tử cho các doanh nghiệp.");
        return view;
    }
}