package controller;

import android.view.View;

public interface OnRecyclerItemClickListener<Model> extends OnItemClickListener {
    void onItemLongClick(View view, int position, Model model);
}
