package com.example.assignment_nangcao.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.assignment_nangcao.Dialog.Bottom_Sheet_EditLoai;
import com.example.assignment_nangcao.Dialog.Bottom_Sheet_HoaDonList;
import com.example.assignment_nangcao.R;
import com.example.assignment_nangcao.dao.HoaDonChiTietDAO;
import com.example.assignment_nangcao.model.HoaDonChiTiet;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import static com.example.assignment_nangcao.FragmentList.ListTypeFragment.dsTheLoai;

public class CartAdapter extends BaseAdapter {
    List<HoaDonChiTiet> arrHoaDonChiTiet;
    public Activity context;
    public LayoutInflater inflater;
    HoaDonChiTietDAO hoaDonChiTietDAO;

    public CartAdapter(Activity context, List<HoaDonChiTiet> arrayHoaDonChiTiet) {
        super();
        this.context = context;
        this.arrHoaDonChiTiet = arrayHoaDonChiTiet;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(context);
    }

    @Override
    public int getCount() {
        return arrHoaDonChiTiet.size();
    }

    @Override
    public Object getItem(int position) {
        return arrHoaDonChiTiet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder {

        TextView txtMaSach, txtSoLuong, txtGiaBia, txtThanhTien;
        ImageView iv_cart;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_cart, null);
            holder.txtMaSach = (TextView) convertView.findViewById(R.id.tvMaSach);
            holder.txtSoLuong = (TextView) convertView.findViewById(R.id.tvSoLuong);
            holder.txtGiaBia = (TextView) convertView.findViewById(R.id.tvGiaBia);
            holder.txtThanhTien = (TextView) convertView.findViewById(R.id.tvThanhTien);
            holder.iv_cart = (ImageView) convertView.findViewById(R.id.iv_cart);

            holder.iv_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    PopupMenu popupMenu = new PopupMenu(context, v);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_popup_hoadon, popupMenu.getMenu());
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.one:
//                                    Bundle b = new Bundle();
//                                    Bottom_Sheet_EditLoai bottomSheet = new Bottom_Sheet_EditLoai();
//
//                                    bottomSheet.setArguments(b);
//                                    bottomSheet.show(((FragmentActivity) context).getSupportFragmentManager(), "TAG");
                                    break;

                                case R.id.two:

                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setTitle("Delay pay");
                                    builder.setMessage("Do you want to delete this data ?");
                                    builder.setIcon(R.drawable.beane);
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            hoaDonChiTietDAO.deleteHoaDonChiTietByID(String.valueOf(arrHoaDonChiTiet.get(position).getMaHDCT()));
                                            arrHoaDonChiTiet.remove(position);
                                            notifyDataSetChanged();
                                            Toast.makeText(context, "Data deleted successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    builder.show();

                                    break;
                            }
                            return true;
                        }
                    });

                }
            });

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        HoaDonChiTiet _entry = (HoaDonChiTiet) arrHoaDonChiTiet.get(position);
        holder.txtMaSach.setText("Mã sách: " + _entry.getSach().getMaSach());
        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decimalFormat.applyPattern("#,###,###,###");
        holder.txtSoLuong.setText("Số lượng: " + _entry.getSoLuongMua());

        decimalFormat.applyPattern("#,###,###,###");
        holder.txtGiaBia.setText("Giá: " + decimalFormat.format(_entry.getSach().getGiaBia()) + " VNĐ");
//        holder.txtThanhTien.setText("Thành tiền: " + _entry.getSoLuongMua() * _entry.getSach().getGiaBia() + " VNĐ");
        holder.txtThanhTien.setText("Thành tiền: " + decimalFormat.format(_entry.getSoLuongMua() * _entry.getSach().getGiaBia()) + " VNĐ");


        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<HoaDonChiTiet> items) {
        this.arrHoaDonChiTiet = items;
        notifyDataSetChanged();

    }

}

