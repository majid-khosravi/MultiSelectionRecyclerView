package controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ir.khosravi.multiselection.R;
import model.CityModel;

public class MultiSelectAdapter extends RecyclerView.Adapter<MultiSelectAdapter.MyViewHolder> {

    public List<CityModel> modelList;
    public List<CityModel> multiSelectList;
    public boolean isMultiSelect;
    private OnRecyclerItemClickListener<CityModel> listener;
    Context context;

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView posting, name;
        private LinearLayout ll_listitem;
        private CheckBox chk_select;

        MyViewHolder(View view) {
            super(view);
            posting = view.findViewById(R.id.tv_posting);
            name = view.findViewById(R.id.tv_user_name);
            ll_listitem = view.findViewById(R.id.ll_listitem);
            chk_select = view.findViewById(R.id.chk_select);

        }
    }

    public MultiSelectAdapter(Context context, List<CityModel> modelList, List<CityModel> multiSelectList) {
        this.context = context;
        this.modelList = modelList;
        this.multiSelectList = multiSelectList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final CityModel model = modelList.get(position);
        holder.name.setText(model.getName());
        holder.posting.setText(model.getCountry());

        if (isMultiSelect)
            holder.chk_select.setVisibility(View.VISIBLE);
        else
            holder.chk_select.setVisibility(View.GONE);

        if (multiSelectList.contains(modelList.get(position))) {
            holder.chk_select.setChecked(true);
        } else {
            holder.chk_select.setChecked(false);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onItemClick(view, position, model);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (listener != null)
                    listener.onItemLongClick(view, position, model);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public void setOnItemClickListener(OnRecyclerItemClickListener<CityModel> listener) {
        this.listener = listener;
    }
}

