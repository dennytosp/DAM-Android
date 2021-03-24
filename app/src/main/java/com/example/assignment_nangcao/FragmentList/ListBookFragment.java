package com.example.assignment_nangcao.FragmentList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.assignment_nangcao.Fragment.BookFragment;
import com.example.assignment_nangcao.Fragment.TypeFragment;
import com.example.assignment_nangcao.R;
import com.example.assignment_nangcao.adapter.BookAdapter;
import com.example.assignment_nangcao.adapter.TheLoaiAdapter;
import com.example.assignment_nangcao.dao.SachDAO;
import com.example.assignment_nangcao.dao.TheLoaiDAO;
import com.example.assignment_nangcao.model.Sach;
import com.example.assignment_nangcao.model.TheLoai;

import java.util.ArrayList;
import java.util.List;

public class ListBookFragment extends Fragment {
    public static List<Sach> dsSach = new ArrayList<>();
    ListView lvBook;
    BookAdapter adapter = null;
    SachDAO sachDAO;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listfragment_type, container, false);
        lvBook = (ListView) view.findViewById(R.id.lvTheLoai);
        sachDAO = new SachDAO(getActivity());
        dsSach = sachDAO.getAllSach();

        adapter = new BookAdapter(getActivity(), dsSach);
        lvBook.setAdapter(adapter);

        lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Sach sach = (Sach) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), BookFragment.class);
                Bundle b = new Bundle();
                b.putString("MASACH", sach.getMaSach());
                b.putString("MATHELOAI", sach.getMaTheLoai());
                b.putString("TENSACH", sach.getTenSach());
                b.putString("TACGIA", sach.getTacGia());
                b.putString("NXB", sach.getNXB());
                b.putString("GIABIA", String.valueOf(sach.getGiaBia()));
                b.putString("SOLUONG", String.valueOf(sach.getSoLuong()));
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        return view;
    }

}
