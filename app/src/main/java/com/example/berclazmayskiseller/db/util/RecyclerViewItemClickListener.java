package com.example.berclazmayskiseller.db.util;

import android.view.View;

public interface RecyclerViewItemClickListener {
    void onItemClick(View v, int position);

    void onItemLongClick(View v, int position);
}
