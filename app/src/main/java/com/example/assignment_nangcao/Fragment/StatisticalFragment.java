package com.example.assignment_nangcao.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.assignment_nangcao.R;
import com.example.assignment_nangcao.dao.HoaDonChiTietDAO;

import java.text.DecimalFormat;


public class StatisticalFragment extends Fragment {
    TextView tvNgay, tvThang, tvNam;
    HoaDonChiTietDAO hoaDonChiTietDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistical, container, false);
        tvNgay = (TextView) view.findViewById(R.id.tvThongKeNgay);
        tvThang = (TextView) view.findViewById(R.id.tvThongKeThang);
        tvNam = (TextView) view.findViewById(R.id.tvThongKeNam);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(getActivity());
        DecimalFormat formatter = new DecimalFormat("#,###");

        tvNgay.setText(formatter.format(hoaDonChiTietDAO.getDoanhThuTheoNgay())+" VNĐ");
        tvThang.setText(formatter.format(hoaDonChiTietDAO.getDoanhThuTheoThang())+" VNĐ");
        tvNam.setText(formatter.format(hoaDonChiTietDAO.getDoanhThuTheoNam())+" VNĐ");

        return view;

    }
}
