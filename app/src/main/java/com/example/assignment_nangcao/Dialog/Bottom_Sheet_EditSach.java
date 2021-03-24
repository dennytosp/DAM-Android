package com.example.assignment_nangcao.Dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.assignment_nangcao.R;
import com.example.assignment_nangcao.adapter.BookAdapter;
import com.example.assignment_nangcao.adapter.TheLoaiAdapter;
import com.example.assignment_nangcao.dao.SachDAO;
import com.example.assignment_nangcao.dao.TheLoaiDAO;
import com.example.assignment_nangcao.model.Sach;
import com.example.assignment_nangcao.model.TheLoai;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import static com.example.assignment_nangcao.Fragment.BookFragment.lvBook;
import static com.example.assignment_nangcao.FragmentList.ListBookFragment.dsSach;


public class Bottom_Sheet_EditSach extends BottomSheetDialogFragment {
    SachDAO sachDAO;
    TheLoaiDAO theLoaiDAO;
    Spinner spnTheLoai;
    EditText edMaSach, edTenSach, edNXB, edTacGia, edGiaBia, edSoLuong;
    Button btnEdit_book;
    String maTheLoai = "";
    List<TheLoai> listTheLoai = new ArrayList<>();
    BookAdapter adapter;

    public Bottom_Sheet_EditSach() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_edit_sach, container, false);

        spnTheLoai = (Spinner) view.findViewById(R.id.spnTheLoai);
        getTheLoai();
        edMaSach = (EditText) view.findViewById(R.id.edMaSach);
        edTenSach = (EditText) view.findViewById(R.id.edTenSach);
        edNXB = (EditText) view.findViewById(R.id.edNXB);
        edTacGia = (EditText) view.findViewById(R.id.edTacGia);
        edGiaBia = (EditText) view.findViewById(R.id.edGiaBia);
        edSoLuong = (EditText) view.findViewById(R.id.edSoLuong);
        btnEdit_book = (Button) view.findViewById(R.id.btnEdit_book);
        //
        spnTheLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maTheLoai = listTheLoai.get(spnTheLoai.getSelectedItemPosition()).getMaTheLoai();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //load data into form
        Bundle b = getArguments();
        if (b != null) {
            edMaSach.setText(b.getString("MASACH"));
            String maTheLoai = b.getString("MATHELOAI");
            edTenSach.setText(b.getString("TENSACH"));
            edNXB.setText(b.getString("NXB"));
            edTacGia.setText(b.getString("TACGIA"));
            edGiaBia.setText(b.getString("GIABIA"));
            edSoLuong.setText(b.getString("SOLUONG"));
            spnTheLoai.setSelection(checkPositionTheLoai(maTheLoai));
        }

        btnEdit_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sachDAO = new SachDAO(getActivity());
                Sach sach = new Sach(edMaSach.getText().toString(), maTheLoai, edTenSach.getText().toString(),
                        edTacGia.getText().toString(), edNXB.getText().toString(),
                        Double.parseDouble(edGiaBia.getText().toString()), Integer.parseInt(edSoLuong.getText().toString()));
                try {
                    if (sachDAO.inserSach(sach) > 0) {
                        Toast.makeText(getActivity().getApplicationContext(), "Chỉnh sửa dữ liệu thành công", Toast.LENGTH_SHORT).show();
                        dsSach = sachDAO.getAllSach();
                        adapter = new BookAdapter(getActivity(), dsSach);
                        lvBook.setAdapter(adapter);
                        dismiss();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Chỉnh sửa dữ liệu thất bại", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }
            }
        });
        return view;

    }

    public void showSpinner(View view) {
        sachDAO = new SachDAO(getActivity());
        sachDAO.getAllSach();
    }

    public void getTheLoai() {
        theLoaiDAO = new TheLoaiDAO(getActivity());

        listTheLoai = theLoaiDAO.getAllTheLoai();
        ArrayAdapter<TheLoai> dataAdapter = new ArrayAdapter<TheLoai>(getActivity(),
                android.R.layout.simple_spinner_item, listTheLoai);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTheLoai.setAdapter(dataAdapter);
    }

    public int checkPositionTheLoai(String strTheLoai) {
        for (int i = 0; i < listTheLoai.size(); i++) {
            if (strTheLoai.equals(listTheLoai.get(i).getMaTheLoai())) {
                return i;
            }
        }
        return 0;
    }

}
