package com.example.myproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {

    public Fragment1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        TextView textView = view.findViewById(R.id.text_view);
        textView.setText("Software Engineering là một bộ môn của khoa học máy tính liên quan đến việc phát triển phần mềm. Nó là quá trình thiết kế, phát triển, triển khai, và bảo trì các sản phẩm phần mềm có chất lượng cao. Sự phát triển của phần mềm bao gồm các giai đoạn khác nhau, bao gồm thu thập yêu cầu, phân tích yêu cầu, thiết kế, lập trình, kiểm thử, triển khai và bảo trì.");
        return view;
    }
}