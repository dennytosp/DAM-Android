package com.example.assignment_nangcao.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.assignment_nangcao.Dialog.Bottom_Sheet_Loai;
import com.example.assignment_nangcao.R;
import com.example.assignment_nangcao.adapter.TheLoaiAdapter;
import com.example.assignment_nangcao.dao.TheLoaiDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.assignment_nangcao.FragmentList.ListTypeFragment.dsTheLoai;

public class TypeFragment extends Fragment {
    public static ListView lv;
    TheLoaiDAO theLoaiDAO;
    FloatingActionButton floating_type;
    TheLoaiAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type, container, false);
        floating_type = view.findViewById(R.id.floating_type);
        lv = (ListView) view.findViewById(R.id.rv_type);
        theLoaiDAO = new TheLoaiDAO(getContext());
        dsTheLoai = theLoaiDAO.getAllTheLoai();

        adapter = new TheLoaiAdapter(getActivity(), dsTheLoai);
        lv.setAdapter(adapter);

        floating_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bottom_Sheet_Loai bottomSheet = new Bottom_Sheet_Loai();
                bottomSheet.show(getFragmentManager(), "TAG");
            }
        });
        return view;

    }
    @Override
    public void onResume() {
        super.onResume();
        dsTheLoai.clear();
        dsTheLoai.addAll(theLoaiDAO.getAllTheLoai());
        adapter.changeDataset(dsTheLoai);
    }

}