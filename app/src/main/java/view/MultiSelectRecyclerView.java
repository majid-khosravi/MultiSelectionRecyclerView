package view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import controller.MultiSelectAdapter;
import controller.OnItemClickListener;
import controller.OnRecyclerItemClickListener;
import ir.khosravi.multiselection.R;
import model.CityModel;

public class MultiSelectRecyclerView extends LinearLayout {
    private String appName = getResources().getString(R.string.app_name);
    private Context context;
    private ImageView btnCancel;
    private TextView txtItemCount;
    private CheckBox chkSelectAll;
    private RecyclerView recyclerView;
    private OnItemClickListener listener;
    private MultiSelectAdapter adapter;
    private boolean isMultiSelect = false;
    private boolean isAllSelected = false;
    List<CityModel> modelList = new ArrayList<>();
    List<CityModel> multiSelectList = new ArrayList<>();

    public MultiSelectRecyclerView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public MultiSelectRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public MultiSelectRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        View root = inflate(context, R.layout.multi_select_recycler, this);
        recyclerView = root.findViewById(R.id.multiSelectRecycler_list);
    }

    public void setRecyclerList(List<CityModel> modelList,
                                ImageView btnCancel,
                                TextView txtItemCount,
                                CheckBox chkSelectAll) {
        this.btnCancel = btnCancel;
        this.txtItemCount = txtItemCount;
        this.chkSelectAll = chkSelectAll;
        this.btnCancel.setVisibility(View.GONE);
        this.chkSelectAll.setVisibility(View.GONE);

        this.modelList = modelList;

        initEvent();
    }

    private void initEvent() {
        chkSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    multiSelectList = new ArrayList<>();
                    multiSelectList.addAll(modelList);
                    isAllSelected = true;
                    txtItemCount.setText(multiSelectList.size()+" Selected");
                } else {
                    if (isAllSelected)
                        multiSelectList = new ArrayList<>();
                    txtItemCount.setText(appName);
                }

                refreshAdapter();
            }
        });

        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setCancel();
            }
        });

        adapter = new MultiSelectAdapter(context, modelList, multiSelectList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new OnRecyclerItemClickListener<CityModel>() {
            @Override
            public void onItemClick(View view, int position, Object o) {
                if (isMultiSelect) {
                    setMultiSelect(position);
                } else {
                    if (listener != null) {
                        listener.onItemClick(view, position, modelList.get(position));
                    }
                }
            }

            @Override
            public void onItemLongClick(View view, int position, CityModel cityModel) {
                if (!isMultiSelect) {
                    multiSelectList = new ArrayList<>();
                    isMultiSelect = true;
                }
                setMultiSelect(position);
            }
        });
    }

    private void setMultiSelect(int position) {
        if (multiSelectList.contains(modelList.get(position))) {
            multiSelectList.remove(modelList.get(position));
            if (isAllSelected) {
                isAllSelected = false;
            }
        } else {
            multiSelectList.add(modelList.get(position));
        }

        if (multiSelectList.size() == modelList.size()) {
            chkSelectAll.setChecked(true);
        }

        if (multiSelectList.size() < modelList.size()) {
            chkSelectAll.setChecked(false);
        }

        if (multiSelectList.size() > 0) {
            chkSelectAll.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);
            txtItemCount.setText( multiSelectList.size()+" Selected");
        } else {
            chkSelectAll.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
            txtItemCount.setText(appName);
            isMultiSelect = false;
        }
        refreshAdapter();
    }

    private void refreshAdapter() {
        adapter.multiSelectList = multiSelectList;
        adapter.modelList = modelList;
        adapter.isMultiSelect = isMultiSelect;
        adapter.notifyDataSetChanged();
    }

    public void setOnRecyclerItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setCancel() {
        chkSelectAll.setVisibility(View.GONE);
        multiSelectList = new ArrayList<>();
        isMultiSelect = false;
        txtItemCount.setText(appName);
        refreshAdapter();
        btnCancel.setVisibility(View.GONE);
    }

    public List<CityModel> getSelectedList() {
        return multiSelectList;
    }

    public boolean isMultiSelect() {
        return isMultiSelect && (chkSelectAll.getVisibility() == View.VISIBLE);
    }

}
