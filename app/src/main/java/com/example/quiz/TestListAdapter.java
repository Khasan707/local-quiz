package com.example.quiz;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Field;
import java.util.List;

public class TestListAdapter extends RecyclerView.Adapter<TestListAdapter.ViewHolder> {

    private static final String DEF_TYPE = "raw";
    private static final String DEF_PACKAGE = "com.example.quiz";

    private LayoutInflater inflater;
    private Context context;
    private List<Field> fieldList;

    public TestListAdapter(Context context, List<Field> fieldList) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.fieldList = fieldList;
    }

    @NonNull
    @Override
    public TestListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_test_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Field field = fieldList.get(position);
        holder.bindItem(field);
    }

    @Override
    public int getItemCount() {
        return fieldList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Field mField;

        TextView tvTestName;

        ViewHolder(View view) {
            super(view);
            tvTestName = view.findViewById(R.id.tv_test_name);
            view.setOnClickListener(this);
        }

        void bindItem(Field field) {
            this.mField = field;
            tvTestName.setText(mField.getName());
        }

        @Override
        public void onClick(View view) {
            int id = context.getResources().getIdentifier(mField.getName(), DEF_TYPE, DEF_PACKAGE);
            Intent intent = new Intent(context, TestActivity.class);
            intent.putExtra("TEST_ID", id);
            context.startActivity(intent);
        }

    }

}
