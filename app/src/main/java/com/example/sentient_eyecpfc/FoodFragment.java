package com.example.sentient_eyecpfc;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sentient_eyecpfc.Adapters.DBProductListAdapter;
import com.example.sentient_eyecpfc.Data.DBProduct;
import com.example.sentient_eyecpfc.Database.DatabaseUsage;

import java.util.ArrayList;
import java.util.List;

//TODO maybe make lv clickable with full info;
public class FoodFragment extends Fragment {
    ArrayList<DBProduct> mProducts  = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        DatabaseUsage dBUs = new DatabaseUsage(getContext());
        mProducts = dBUs.setList(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.productRec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        DividerItemDecoration decoration = new DividerItemDecoration(this.getContext(), ConstraintWidget.VERTICAL);
        recyclerView.addItemDecoration(decoration);
        DBProductListAdapter adapter = new DBProductListAdapter(this.getContext(), mProducts);
        recyclerView.setAdapter(adapter);
        return view;
    }





}
