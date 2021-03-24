package com.example.assignment_nangcao.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.assignment_nangcao.Dialog.Bottom_Sheet_Sach;
import com.example.assignment_nangcao.R;
import com.example.assignment_nangcao.adapter.BookAdapter;
import com.example.assignment_nangcao.dao.SachDAO;
import com.example.assignment_nangcao.model.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.assignment_nangcao.FragmentList.ListTypeFragment.dsTheLoai;

public class BookFragment extends Fragment {
    public static List<Sach> dsSach = new ArrayList<>();
    public static ListView lvBook;
    BookAdapter adapter;
    SachDAO sachDAO;
    FloatingActionButton floating_book;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        floating_book = view.findViewById(R.id.floating_book);
        lvBook = (ListView) view.findViewById(R.id.rv_book);

        sachDAO = new SachDAO(getActivity());
        dsSach = sachDAO.getAllSach();

        adapter = new BookAdapter(getActivity(), dsSach);
        lvBook.setAdapter(adapter);


        floating_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bottom_Sheet_Sach bottomSheet = new Bottom_Sheet_Sach();
                bottomSheet.show(getFragmentManager(), "TAG");
            }
        });

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        dsSach.clear();
        dsSach.addAll(sachDAO.getAllSach());
        adapter.changeDataset(dsSach);
    }

}
