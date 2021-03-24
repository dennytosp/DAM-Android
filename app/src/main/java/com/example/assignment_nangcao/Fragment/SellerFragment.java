package com.example.assignment_nangcao.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.assignment_nangcao.R;
import com.example.assignment_nangcao.adapter.BookAdapter;
import com.example.assignment_nangcao.adapter.BookStatisticalAdapter;
import com.example.assignment_nangcao.dao.SachDAO;
import com.example.assignment_nangcao.model.Sach;

import java.util.ArrayList;
import java.util.List;


public class SellerFragment extends Fragment {
    public static List<Sach> dsSach = new ArrayList<>();
    ListView lvBook;
    BookStatisticalAdapter adapter = null;
    Button btnViewSach;
    SachDAO sachDAO;
    EditText edThang;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller, container, false);
        lvBook = view.findViewById(R.id.rv_seller);
        edThang = (EditText) view.findViewById(R.id.edThang);
        btnViewSach = view.findViewById(R.id.btnViewSach);
        btnViewSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(edThang.getText().toString())>13 || Integer.parseInt(edThang.getText().toString())<0){
                    Toast.makeText(getActivity().getApplicationContext(),"Không đúng định dạng tháng (1-12)",Toast.LENGTH_SHORT).show();
                }else {
                    sachDAO = new SachDAO(getActivity());
                    dsSach = sachDAO.getSachTop10(edThang.getText().toString());

                    adapter = new BookStatisticalAdapter(getActivity(), dsSach);
                    lvBook.setAdapter(adapter);
                }

            }
        });
        return view;

    }

}
