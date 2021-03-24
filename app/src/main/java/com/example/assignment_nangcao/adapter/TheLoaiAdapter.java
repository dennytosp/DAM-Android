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
import com.example.assignment_nangcao.R;
import com.example.assignment_nangcao.dao.TheLoaiDAO;
import com.example.assignment_nangcao.model.TheLoai;

import java.util.List;

import static com.example.assignment_nangcao.FragmentList.ListTypeFragment.dsTheLoai;

public class TheLoaiAdapter extends BaseAdapter {
    List<TheLoai> arrTheLoai;
    public Activity context;
    public LayoutInflater inflater;
    TheLoaiDAO theLoaiDAO;

    public TheLoaiAdapter(Activity context, List<TheLoai> arrayTheLoai) {
        super();
        this.context = context;
        this.arrTheLoai = arrayTheLoai;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        theLoaiDAO = new TheLoaiDAO(context);
    }

    @Override
    public int getCount() {
        return arrTheLoai.size();
    }

    @Override
    public Object getItem(int position) {
        return arrTheLoai.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder {

        ImageView img;
        TextView txtMaTheLoai, txtTenTheLoai, txtViTri, txtMoTa;
        ImageView imgType, ivType;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_type, null);
            holder.txtMaTheLoai = (TextView) convertView.findViewById(R.id.tvMaTheLoai);
            holder.txtTenTheLoai = (TextView) convertView.findViewById(R.id.tvTenTheLoai);
            holder.txtViTri = (TextView) convertView.findViewById(R.id.tvViTri);
            holder.txtMoTa = (TextView) convertView.findViewById(R.id.tvMota);
            holder.imgType = (ImageView) convertView.findViewById(R.id.img_Type);
            holder.ivType = (ImageView) convertView.findViewById(R.id.iv_type);
            holder.ivType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    PopupMenu popupMenu = new PopupMenu(context, v);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.one:
                                    Bundle b = new Bundle();
                                    b.putString("MATHELOAI", dsTheLoai.get(position).getMaTheLoai());
                                    b.putString("TENTHELOAI", dsTheLoai.get(position).getTenTheLoai());
                                    b.putString("MOTA", dsTheLoai.get(position).getMoTa());
                                    b.putString("VITRI", String.valueOf(dsTheLoai.get(position).getViTri()));
                                    Bottom_Sheet_EditLoai bottomSheet = new Bottom_Sheet_EditLoai();

                                    bottomSheet.setArguments(b);
                                    bottomSheet.show(((FragmentActivity) context).getSupportFragmentManager(), "TAG");

                                    break;

                                case R.id.two:

                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setTitle("Delay pay");
                                    builder.setMessage("Do you want to delete this data ?");
                                    builder.setIcon(R.drawable.beane);
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            theLoaiDAO.deleteTheLoaiByID(arrTheLoai.get(position).getMaTheLoai());
                                            arrTheLoai.remove(position);
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

        TheLoai _entry = (TheLoai) arrTheLoai.get(position);
//        holder.img.setImageResource(R.drawable.cateicon);
        holder.txtMaTheLoai.setText("Mã loại: " + _entry.getMaTheLoai());
        holder.txtTenTheLoai.setText("Tên loại: " + _entry.getTenTheLoai());
        holder.txtViTri.setText(("Vị trí: ") + String.valueOf(_entry.getViTri()));
        holder.txtMoTa.setText("Mô tả: " + _entry.getMoTa());

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<TheLoai> items) {
        this.arrTheLoai = items;
        notifyDataSetChanged();

    }

}

