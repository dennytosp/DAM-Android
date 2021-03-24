package com.example.assignment_nangcao.Dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.assignment_nangcao.R;
import com.example.assignment_nangcao.adapter.CartAdapter;
import com.example.assignment_nangcao.dao.HoaDonChiTietDAO;
import com.example.assignment_nangcao.dao.SachDAO;
import com.example.assignment_nangcao.model.HoaDon;
import com.example.assignment_nangcao.model.HoaDonChiTiet;
import com.example.assignment_nangcao.model.Sach;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Bottom_Sheet_HoaDonChiTiet extends BottomSheetDialogFragment {
    EditText edMaSach, edMaHoaDon, edSoLuong;
    TextView tvThanhTien;
    HoaDonChiTietDAO hoaDonChiTietDAO;
    SachDAO sachDAO;
    public List<HoaDonChiTiet> dsHDCT = new ArrayList<>();
    ListView lvCart;
    CartAdapter adapter;
    Button btnADDHoaDon, thanhToanHoaDon, btnDismiss;
    double thanhTien = 0;

    public Bottom_Sheet_HoaDonChiTiet() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_hoadonchitiet, container, false);
        edMaSach = (EditText) view.findViewById(R.id.edMaSach);
        edMaHoaDon = (EditText) view.findViewById(R.id.edMaHoaDon);
        edSoLuong = (EditText) view.findViewById(R.id.edSoLuongMua);
        btnADDHoaDon = (Button) view.findViewById(R.id.btnInsert_invoice);
        thanhToanHoaDon = (Button) view.findViewById(R.id.btnInsert_thanhtoan);

        lvCart = (ListView) view.findViewById(R.id.lvCart);
        tvThanhTien = (TextView) view.findViewById(R.id.tvThanhTien);

        adapter = new CartAdapter(getActivity(), dsHDCT);
        lvCart.setAdapter(adapter);

        Bundle getdata = getArguments();
        String mhd = getdata.getString("MAHOADON");

        edMaHoaDon.setText(mhd);

        btnADDHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hoaDonChiTietDAO = new HoaDonChiTietDAO(getActivity());
                sachDAO = new SachDAO(getActivity());
                try {
                    if (validation() < 0) {
                        Toast.makeText(getActivity().getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else {
                        Sach sach = sachDAO.getSachByID(edMaSach.getText().toString());
                        if (sach != null) {
                            int pos = checkMaSach(dsHDCT, edMaSach.getText().toString());
                            HoaDon hoaDon = new HoaDon(edMaHoaDon.getText().toString(), new Date());
                            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet(1, hoaDon, sach, Integer.parseInt(edSoLuong.getText().toString()));
                            if (pos >= 0) {
                                int soluong = dsHDCT.get(pos).getSoLuongMua();
                                hoaDonChiTiet.setSoLuongMua(soluong + Integer.parseInt(edSoLuong.getText().toString()));
                                dsHDCT.set(pos, hoaDonChiTiet);
                            } else {
                                dsHDCT.add(hoaDonChiTiet);
                            }
                            adapter.changeDataset(dsHDCT);
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Mã sách không tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }

            }
        });
        thanhToanHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hoaDonChiTietDAO = new HoaDonChiTietDAO(getActivity());
                //tinh tien
                thanhTien = 0;
                try {
                    for (HoaDonChiTiet hd : dsHDCT) {
                        hoaDonChiTietDAO.inserHoaDonChiTiet(hd);
                        thanhTien = thanhTien + hd.getSoLuongMua() * hd.getSach().getGiaBia();
                    }
                    tvThanhTien.setText("Tổng tiền: " + thanhTien);
                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }

            }
        });
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    public int checkMaSach(List<HoaDonChiTiet> lsHD, String maSach) {
        int pos = -1;
        for (int i = 0; i < lsHD.size(); i++) {
            HoaDonChiTiet hd = lsHD.get(i);
            if (hd.getSach().getMaSach().equalsIgnoreCase(maSach)) {
                pos = i;
                break;
            }
        }
        return pos;
    }

    public int validation() {
        if (edMaSach.getText().toString().isEmpty() || edSoLuong.getText().toString().isEmpty() || edMaHoaDon.getText().toString().isEmpty()) {
            return -1;
        }
        return 1;
    }
}
