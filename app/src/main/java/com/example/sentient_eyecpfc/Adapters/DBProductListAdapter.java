package com.example.sentient_eyecpfc.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sentient_eyecpfc.Data.DBProduct;
import com.example.sentient_eyecpfc.R;

import java.util.List;

public class DBProductListAdapter  extends RecyclerView.Adapter<DBProductListAdapter.ViewHolder>  {
    private LayoutInflater mInflater;
    private List<DBProduct> mProducts;

    public DBProductListAdapter(Context context, List<DBProduct> products) {
        this.mProducts = products;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public DBProductListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.list_product_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(DBProductListAdapter.ViewHolder holder, int position) {
        DBProduct product = mProducts.get(position);
        holder.dateTv.setText("Date of consuming: " + product.getCode());
        holder.doseTv.setText("Amount of product (gr): " + product.getmDose().toString());
        holder.nameTv.setText("\nThe name of the product "  + product.getName());
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameTv, dateTv,doseTv;
        ViewHolder(View view){
            super(view);
            nameTv = view.findViewById(R.id.nameListTv);
            dateTv = view.findViewById(R.id.dateListTv);
            doseTv = view.findViewById(R.id.doseListTv);
        }
    }
}
