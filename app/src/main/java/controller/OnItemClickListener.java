package controller;

import android.view.View;

public interface OnItemClickListener<Model> {
    void onItemClick(View view, int position, Model model);
}
