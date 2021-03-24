//package com.example.assignment_nangcao.Fragment;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.matx_book.Adapter.Sach_Adapter;
//import com.example.matx_book.DAO.SachDAO;
//import com.example.matx_book.Dialog.Bottom_Sheet_Sach;
//import com.example.matx_book.Model.Sach;
//import com.example.matx_book.R;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//
//import java.util.ArrayList;
//
//public class Book extends Fragment {
//    FloatingActionButton floating_khoan;
//    Sach_Adapter adapter;
//    SachDAO dao;
//    ArrayList<Sach> ds_sach;
//    //    ImageView ic_add_khoan, ic_edit_khoan;
//    public static RecyclerView rv_sach;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_book, container, false);
//        floating_khoan = view.findViewById(R.id.floating_book);
//        rv_sach = view.findViewById(R.id.rv_book);
//
////        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
////        rv_sach.setLayoutManager(linearLayoutManager);
//        rv_sach.setLayoutManager(new LinearLayoutManager(getContext()));
//        ds_sach = new ArrayList<>();
//        dao = new SachDAO(getContext());
//        ds_sach = dao.readAll();
//        adapter = new Sach_Adapter(ds_sach, getActivity());
//        rv_sach.setAdapter(adapter);
//
//        floating_khoan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bottom_Sheet_Sach bottomSheet = new Bottom_Sheet_Sach();
//                bottomSheet.show(getFragmentManager(), "TAG");
//            }
//        });
//
//        return view;
//    }
//
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        ds_sach.clear();
//        ds_sach.addAll(dao.readAll());
//        adapter.notifyDataSetChanged();
//    }
//}
