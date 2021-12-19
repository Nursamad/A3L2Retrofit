package com.geektech.a3l2retrofit.ui.interfaces;

import com.geektech.a3l2retrofit.models.Post;

public interface OnItemClickListener {
    void onClick(int position);
    void  onLongClick(Post post, int position);
}
