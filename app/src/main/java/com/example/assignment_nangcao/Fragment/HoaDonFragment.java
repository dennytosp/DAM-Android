//package com.example.assignment_nangcao.Fragment;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class HoaDonFragment extends Fragment {
//    public List<HoaDon> dsHoaDon = new ArrayList<>();
//    ListView lvHoaDon;
//    TextView edthem;
//    HoaDonAdapter adapter = null;
//    HoaDonDAO hoaDonDAO;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.activity_list_hoa_don,container,false);
//        lvHoaDon = (ListView) view.findViewById(R.id.lvHoaDon);
//        edthem = (TextView) view.findViewById(R.id.edthem);
//        hoaDonDAO = new HoaDonDAO(getContext());
//        try {
//            dsHoaDon = hoaDonDAO.getAllHoaDon();
//        } catch (Exception e) {
//            Log.d("Error: ", e.toString());
//        }
//
//        adapter = new HoaDonAdapter(getActivity(), dsHoaDon);
//        lvHoaDon.setAdapter(adapter);
//        lvHoaDon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                HoaDon hoaDon = (HoaDon) parent.getItemAtPosition(position);
//                Intent intent = new Intent(getContext(), ListHoaDonChiTietByIDActivity.class);
//                Bundle b = new Bundle();
//                b.putString("MAHOADON", hoaDon.getMaHoaDon());
//                intent.putExtras(b);
//                startActivity(intent);
//            }
//        });
//
//        edthem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bottom_Sheet_HoaDon bottomSheet = new Bottom_Sheet_HoaDon();
//                bottomSheet.show(getFragmentManager(),"TAG");
//
//            }
//        });
//
//        // TextFilter
//        lvHoaDon.setTextFilterEnabled(true);
//        EditText edSeach = (EditText) view.findViewById(R.id.edSearch);
//        edSeach.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                System.out.println("Text [" + s + "] - Start [" + start + "] - Before [" + before + "] - Count [" + count + "]");
//                if (count < before) {
//                    adapter.resetData();
//                }
//                adapter.getFilter().filter(s.toString());
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
//
//
//        return view;
//    }
//
//}
