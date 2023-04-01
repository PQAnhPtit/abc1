package com.example.addeditdeleteslide4.model;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addeditdeleteslide4.R;

import java.util.ArrayList;
import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatViewHolder>{
    private Context context;

    private List<Cat> listBackUp;
    private List<Cat> mList;
    private CatItemListener mCatItem;

    public CatAdapter(Context context) {
        this.context = context;
        mList = new ArrayList<>();
        listBackUp = new ArrayList<>();
    }

    public List<Cat> getBackup(){
        return listBackUp;
    }

    public void setmList(List<Cat> mList){
        this.mList = mList;
    }

    public void filterList(List<Cat> filterlist){
        mList = filterlist;
        notifyDataSetChanged();
    }

    public void setClickListener(CatItemListener mCatItem){
        this.mCatItem = mCatItem;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new CatViewHolder(view);
    }

    public Cat getItem(int position){
        return mList.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {
        Cat cat = mList.get(position);
        if(cat == null)
            return;
        holder.img.setImageResource(cat.getImg());
        holder.tvName.setText(cat.getName());
        holder.tvDescribe.setText(cat.getDescribe());
        holder.tvPrice.setText(cat.getPrice()+"");
        holder.btRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thong bao xoa");
                builder.setMessage("Ban chac chan muon xoa " + cat.getName() + " nay khong");
                builder.setIcon(R.drawable.remove);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listBackUp.remove(position);
                        mList.remove(position);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void add(Cat c){
        listBackUp.add(c);
        mList.add(c);
        notifyDataSetChanged();
    }

    public void update(int position, Cat cat){
        listBackUp.set(position, cat);
        mList.set(position, cat);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mList != null)
            return mList.size();
        return 0;
    }

    public class CatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView img;
        private TextView tvName, tvDescribe, tvPrice;
        private Button btRemove;

        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tvName = itemView.findViewById(R.id.txtName);
            tvDescribe = itemView.findViewById(R.id.txtDescribe);
            tvPrice = itemView.findViewById(R.id.txtPrice);
            btRemove= itemView.findViewById(R.id.btRemove);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mCatItem != null){
                mCatItem.OnItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface CatItemListener{
        void OnItemClick(View view, int position);
    }
}