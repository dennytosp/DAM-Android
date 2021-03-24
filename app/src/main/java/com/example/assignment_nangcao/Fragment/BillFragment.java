package com.example.assignment_nangcao.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.assignment_nangcao.Dialog.Bottom_Sheet_HoaDon;
import com.example.assignment_nangcao.Dialog.Bottom_Sheet_HoaDonChiTiet;
import com.example.assignment_nangcao.Dialog.Bottom_Sheet_Loai;
import com.example.assignment_nangcao.R;
import com.example.assignment_nangcao.adapter.HoaDonAdapter;
import com.example.assignment_nangcao.dao.HoaDonDAO;
import com.example.assignment_nangcao.model.HoaDon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class BillFragment extends Fragment {
    public List<HoaDon> dsHoaDon = new ArrayList<>();
    public static ListView lvHoaDon;
    HoaDonAdapter adapter = null;
    HoaDonDAO hoaDonDAO;
    FloatingActionButton floating_bill;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);

        floating_bill = view.findViewById(R.id.floating_bill);
        lvHoaDon = (ListView) view.findViewById(R.id.rv_bill);
        hoaDonDAO = new HoaDonDAO(getActivity());
        try {
            dsHoaDon = hoaDonDAO.getAllHoaDon();
        } catch (Exception e) {
            Log.d("Error: ", e.toString());
        }

        adapter = new HoaDonAdapter(getActivity(), dsHoaDon);
        lvHoaDon.setAdapter(adapter);

        floating_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bottom_Sheet_HoaDon bottomSheet = new Bottom_Sheet_HoaDon();
                bottomSheet.show(getFragmentManager(), "TAG");
            }
        });

        return view;

    }
}
