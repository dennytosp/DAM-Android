package com.example.assignment_nangcao.Dialog;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.assignment_nangcao.R;
import com.example.assignment_nangcao.adapter.CartAdapter;
import com.example.assignment_nangcao.dao.HoaDonChiTietDAO;
import com.example.assignment_nangcao.dao.HoaDonDAO;
import com.example.assignment_nangcao.model.HoaDon;
import com.example.assignment_nangcao.model.HoaDonChiTiet;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Bottom_Sheet_HoaDonList extends BottomSheetDialogFragment {
    public List<HoaDonChiTiet> dsHDCT = new ArrayList<>();
    ListView lvCart;
    CartAdapter adapter = null;
    HoaDonChiTietDAO hoaDonChiTietDAO;

    public Bottom_Sheet_HoaDonList() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_hoadonlist, container, false);
        lvCart = (ListView) view.findViewById(R.id.rv_cart);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(getActivity());
        Bundle b = getArguments();
        if (b != null) {
            dsHDCT = hoaDonChiTietDAO.getAllHoaDonChiTietByID(b.getString("MAHOADON"));
        }

        adapter = new CartAdapter(getActivity(), dsHDCT);
        lvCart.setAdapter(adapter);


        return view;
    }

}
