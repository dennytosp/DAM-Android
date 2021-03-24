package com.example.assignment_nangcao.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;

import com.example.assignment_nangcao.Dialog.Bottom_Sheet_EditLoai;
import com.example.assignment_nangcao.Dialog.Bottom_Sheet_HoaDonList;
import com.example.assignment_nangcao.R;
import com.example.assignment_nangcao.dao.HoaDonChiTietDAO;
import com.example.assignment_nangcao.dao.HoaDonDAO;
import com.example.assignment_nangcao.model.HoaDon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.example.assignment_nangcao.FragmentList.ListTypeFragment.dsTheLoai;

public class HoaDonAdapter extends BaseAdapter implements Filterable {
    List<HoaDon> arrHoaDon;
    List<HoaDon> arrSortHoaDon;
    private Filter hoaDonFilter;
    public Activity context;
    public LayoutInflater inflater;
    HoaDonDAO hoadonDAO;
    HoaDonChiTietDAO hoaDonChiTietDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public HoaDonAdapter(Activity context, List<HoaDon> arrayHoaDon) {
        super();
        this.context = context;
        this.arrHoaDon = arrayHoaDon;
        this.arrSortHoaDon = arrayHoaDon;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        hoadonDAO = new HoaDonDAO(context);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(context);
    }

    @Override
    public int getCount() {
        return arrHoaDon.size();
    }

    @Override
    public Object getItem(int position) {
        return arrHoaDon.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder {

        ImageView img;
        TextView txtMaHoaDon, txtNgayMua;
        ImageView iv_bill;
        ConstraintLayout itemClick;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_bill, null);
            holder.img = (ImageView) convertView.findViewById(R.id.item_bill_img);
            holder.txtMaHoaDon = (TextView) convertView.findViewById(R.id.tvMaHoaDon);
            holder.txtNgayMua = (TextView) convertView.findViewById(R.id.tvNgayMua);
            holder.iv_bill = (ImageView) convertView.findViewById(R.id.iv_bill);
            holder.itemClick = (ConstraintLayout) convertView.findViewById(R.id.itemClick);
            holder.itemClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HoaDon hoaDon = (HoaDon) getItem(position);
                    Bundle b = new Bundle();
                    b.putString("MAHOADON", hoaDon.getMaHoaDon());

                    Bottom_Sheet_HoaDonList bottomSheet = new Bottom_Sheet_HoaDonList();
                    bottomSheet.setArguments(b);
                    bottomSheet.show(((FragmentActivity) context).getSupportFragmentManager(), "TAG");

                }
            });
            holder.iv_bill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    PopupMenu popupMenu = new PopupMenu(context, v);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_popup_hoadon_adapter, popupMenu.getMenu());

                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.one:

                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setTitle("Delay pay");
                                    builder.setMessage("Do you want to delete this data ?");
                                    builder.setIcon(R.drawable.beane);
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (hoaDonChiTietDAO.checkHoaDon(arrHoaDon.get(position).getMaHoaDon())) {
                                                Toast.makeText(context, "Bạn phải xoá hoá đơn chi tiết trước tiên", Toast.LENGTH_LONG).show();
                                            } else {
                                                hoadonDAO.deleteHoaDonByID(arrHoaDon.get(position).getMaHoaDon());
                                                arrHoaDon.remove(position);
                                                notifyDataSetChanged();
                                                Toast.makeText(context, "Data deleted successfully", Toast.LENGTH_SHORT).show();
                                            }
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

        HoaDon _entry = (HoaDon) arrHoaDon.get(position);
        holder.txtMaHoaDon.setText(_entry.getMaHoaDon());
        holder.txtNgayMua.setText(sdf.format(_entry.getNgayMua()));

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<HoaDon> items) {
        this.arrHoaDon = items;
        notifyDataSetChanged();

    }

    public void resetData() {
        arrHoaDon = arrSortHoaDon;
    }


    public Filter getFilter() {
        if (hoaDonFilter == null)
            hoaDonFilter = new CustomFilter();

        return hoaDonFilter;
    }

    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = arrSortHoaDon;
                results.count = arrSortHoaDon.size();
            } else {
                List<HoaDon> lsHoaDon = new ArrayList<HoaDon>();
                for (HoaDon p : arrHoaDon) {
                    if (p.getMaHoaDon().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        lsHoaDon.add(p);
                }

                results.values = lsHoaDon;
                results.count = lsHoaDon.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                arrHoaDon = (List<HoaDon>) results.values;
                notifyDataSetChanged();
            }

        }

    }

}

