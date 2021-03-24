package com.example.assignment_nangcao.Dialog;

import android.app.DatePickerDialog;
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
import com.example.assignment_nangcao.adapter.HoaDonAdapter;
import com.example.assignment_nangcao.adapter.TheLoaiAdapter;
import com.example.assignment_nangcao.dao.HoaDonDAO;
import com.example.assignment_nangcao.model.HoaDon;
import com.example.assignment_nangcao.model.TheLoai;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.assignment_nangcao.Fragment.BillFragment.lvHoaDon;
import static com.example.assignment_nangcao.FragmentList.ListTypeFragment.dsTheLoai;


public class Bottom_Sheet_HoaDon extends BottomSheetDialogFragment {
    EditText edNgayMua, edMaHoaDon;
    Button btnInsert_bill;
    HoaDonDAO hoaDonDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    List<HoaDon> ds_hoadon = new ArrayList<>();
    HoaDonAdapter adapter;
    public Bottom_Sheet_HoaDon() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_hoadon, container, false);
        edNgayMua = (EditText) view.findViewById(R.id.edNgayMua);
        edMaHoaDon = (EditText) view.findViewById(R.id.edMaHoaDon);
        btnInsert_bill = (Button) view.findViewById(R.id.btnInsert_bill);


        final Calendar calendar_date = Calendar.getInstance();
        final int year = calendar_date.get(calendar_date.YEAR);
        final int month = calendar_date.get(calendar_date.MONTH);
        final int day = calendar_date.get(calendar_date.DAY_OF_MONTH);

        edNgayMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int i, int i1, int i2) {
//                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        calendar_date.set(i, i1, i2);
                        String date = sdf.format(calendar_date.getTime());
                        edNgayMua.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        btnInsert_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hoaDonDAO = new HoaDonDAO(getContext());

                try {
                    if (validation() < 0) {
                        Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else {
                        HoaDon hoaDon = new HoaDon(edMaHoaDon.getText().toString(), sdf.parse(edNgayMua.getText().toString()));
                        if (hoaDonDAO.inserHoaDon(hoaDon) > 0) {
                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();


                            Bundle bundle = new Bundle();
                            bundle.putString("MAHOADON", edMaHoaDon.getText().toString());
                            Bottom_Sheet_HoaDonChiTiet bottomSheet = new Bottom_Sheet_HoaDonChiTiet();

                            bottomSheet.setArguments(bundle);
                            bottomSheet.show(getActivity().getSupportFragmentManager(),bottomSheet.getTag());

                            ds_hoadon = hoaDonDAO.getAllHoaDon();
                            adapter = new HoaDonAdapter(getActivity(), ds_hoadon);
                            lvHoaDon.setAdapter(adapter);


                        } else {
                            Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }

            }
        });
        return view;
    }

    public int validation() {
        if (edMaHoaDon.getText().toString().isEmpty() || edNgayMua.getText().toString().isEmpty()) {
            return -1;
        }
        return 1;
    }

}
