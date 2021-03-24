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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.assignment_nangcao.Dialog.Bottom_Sheet_EditSach;
import com.example.assignment_nangcao.R;
import com.example.assignment_nangcao.dao.SachDAO;
import com.example.assignment_nangcao.dao.TheLoaiDAO;
import com.example.assignment_nangcao.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class BookStatisticalAdapter extends BaseAdapter implements Filterable {
    List<Sach> arrSach;
    List<Sach> arrSortSach;
    private Filter sachFilter;
    public Activity context;
    public LayoutInflater inflater;
    SachDAO sachDAO;
    TheLoaiDAO theLoaiDAO;

    public BookStatisticalAdapter(Activity context, List<Sach> arraySach) {
        super();
        this.context = context;
        this.arrSach = arraySach;
        this.arrSortSach = arraySach;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sachDAO = new SachDAO(context);
    }
    @Override
    public int getCount() {
        return arrSach.size();
    }

    @Override
    public Object getItem(int position) {
        return arrSach.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public static class ViewHolder {

        ImageView img;
        TextView txtBookName, txtBookPrice, txtSoLuong, txtMaTheLoai;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView==null)
        {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_book_statstical, null);
            holder.img = (ImageView) convertView.findViewById(R.id.item_book_img);
            holder.txtBookName = (TextView) convertView.findViewById(R.id.tvBookName);
            holder.txtBookPrice = (TextView) convertView.findViewById(R.id.tvBookPrice);
            holder.txtSoLuong= (TextView) convertView.findViewById(R.id.tvSoLuong);
            holder.txtMaTheLoai= (TextView) convertView.findViewById(R.id.tvMaTheLoai);

            convertView.setTag(holder);
        }
        else
            holder=(ViewHolder)convertView.getTag();

        Sach _entry = (Sach) arrSach.get(position);
        holder.txtBookName.setText("Mã sách: "+_entry.getMaSach());
        holder.txtSoLuong.setText("Số lượng: "+_entry.getSoLuong());
        holder.txtBookPrice.setText("Giá: "+ _entry.getGiaBia()+"");
        holder.txtMaTheLoai.setText("Mã loại: "+ _entry.getMaTheLoai());

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<Sach> items){
        this.arrSach = items;
        notifyDataSetChanged();

    }
    public void resetData() {
        arrSach = arrSortSach;
    }


    public Filter getFilter() {
        if (sachFilter == null)
            sachFilter = new CustomFilter();

        return sachFilter;
    }

    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                results.values = arrSortSach;
                results.count = arrSortSach.size();
            }
            else {
                List<Sach> lsSach = new ArrayList<Sach>();

                for (Sach p : arrSach) {
                    if (p.getMaSach().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        lsSach.add(p);
                }

                results.values = lsSach;
                results.count = lsSach.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                arrSach = (List<Sach>) results.values;
                notifyDataSetChanged();
            }

        }

    }

}

