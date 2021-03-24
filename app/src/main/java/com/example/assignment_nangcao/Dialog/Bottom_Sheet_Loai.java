package com.example.assignment_nangcao.Dialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.assignment_nangcao.R;
import com.example.assignment_nangcao.adapter.TheLoaiAdapter;
import com.example.assignment_nangcao.dao.TheLoaiDAO;
import com.example.assignment_nangcao.model.TheLoai;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import static com.example.assignment_nangcao.FragmentList.ListTypeFragment.dsTheLoai;
import static com.example.assignment_nangcao.Fragment.TypeFragment.lv;


public class Bottom_Sheet_Loai extends BottomSheetDialogFragment {
    EditText edMaTheLoai, edTenTheLoai, edMoTa, edViTri;
    Button btnAdd, btnCancel, btnShow;
    TheLoaiDAO theLoaiDAO;
    ArrayList<TheLoai> ds_theloai;
    TheLoaiAdapter adapter;

    public Bottom_Sheet_Loai() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_loai, container, false);

        edMaTheLoai = (EditText) view.findViewById(R.id.edMaTheLoai);
        edTenTheLoai = (EditText) view.findViewById(R.id.edTenTheLoai);
        edMoTa = (EditText) view.findViewById(R.id.edMoTa);
        edViTri = (EditText) view.findViewById(R.id.edViTri);
        btnAdd = view.findViewById(R.id.btnInsert_type);


        Bundle b = getArguments();
        if (b != null) {
            edMaTheLoai.setText(b.getString("MATHELOAI"));
            edTenTheLoai.setText(b.getString("TENTHELOAI"));
            edMoTa.setText(b.getString("MOTA"));
            edViTri.setText(b.getString("VITRI"));
        }
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theLoaiDAO = new TheLoaiDAO(getContext());

                try {
                    if (validation() < 0) {
                        Toast.makeText(getActivity().getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else {
                        TheLoai theLoai = new TheLoai(edMaTheLoai.getText().toString(), edTenTheLoai.getText().toString(),
                                edMoTa.getText().toString(), Integer.parseInt(edViTri.getText().toString()));
                        if (theLoaiDAO.inserTheLoai(theLoai) > 0) {
                            Toast.makeText(getActivity().getApplicationContext(), "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
                            dsTheLoai = theLoaiDAO.getAllTheLoai();
                            adapter = new TheLoaiAdapter(getActivity(), dsTheLoai);
                            lv.setAdapter(adapter);
                            dismiss();

                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Thêm dữ liệu thất bại", Toast.LENGTH_SHORT).show();
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
        int check = 1;
        if (edMaTheLoai.getText().length() == 0 || edTenTheLoai.getText().length() == 0
                || edViTri.getText().length() == 0 || edMoTa.getText().length() == 0) {
            check = -1;
        }
        return check;
    }

}
