package com.example.sentient_eyecpfc;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sentient_eyecpfc.Adapters.DBProductListAdapter;
import com.example.sentient_eyecpfc.Data.DBProduct;

import java.util.ArrayList;
import java.util.List;

//TODO maybe make lv clickable with full info;
public class FoodFragment extends Fragment {
    List<DBProduct> mProducts  = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        DBProduct pr = new DBProduct("12.01.01", 100.0,"123456789012", "Семушка тестовый", 100.0,
                100.0, 100.0, 100.0);
        DBProduct pr1 = new DBProduct("12.01.01", 100.0,"123456789012", "Семушка тестовый", 100.0,
                100.0, 100.0, 100.0);
        DBProduct pr2 = new DBProduct("12.01.01", 100.0,"123456789012", "Семушка тестовый", 100.0,
                100.0, 100.0, 100.0);
        mProducts.add(pr);
        mProducts.add(pr1);
        mProducts.add(pr2);
        RecyclerView recyclerView = view.findViewById(R.id.productRec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        DBProductListAdapter adapter = new DBProductListAdapter(this.getContext(), mProducts);
        recyclerView.setAdapter(adapter);
        return view;
    }





}
