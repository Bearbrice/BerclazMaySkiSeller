package com.example.berclazmayskiseller.adapter;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.berclazmayskiseller.R;
import com.example.berclazmayskiseller.database.entity.OrderEntity;
import com.example.berclazmayskiseller.database.entity.ProductEntity;
import com.example.berclazmayskiseller.util.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;

public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<T> mData;
    private RecyclerViewItemClickListener mListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mTextView;

        ViewHolder(TextView textView) {
            super(textView);
            mTextView = textView;
        }
    }

    public RecyclerAdapter(RecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(view -> mListener.onItemClick(view, viewHolder.getAdapterPosition()));
        v.setOnLongClickListener(view -> {
            mListener.onItemLongClick(view, viewHolder.getAdapterPosition());
            return true;
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        T item = mData.get(position);
        if (item.getClass().equals(ProductEntity.class))
            holder.mTextView.setText(((ProductEntity) item).getProductName());
        if (item.getClass().equals(OrderEntity.class))
            holder.mTextView.setText("Ordered on : "+((OrderEntity) item).getOrderDate() /*" - Product ID : #" + ((OrderEntity) item).getProduct_id()*/);
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    public void setData(final List<T> data) {
        if (mData == null) {
            mData = data;
            notifyItemRangeInserted(0, data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mData.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof ProductEntity) {
                        return ((ProductEntity) mData.get(oldItemPosition)).getProductName().equals(((ProductEntity) data.get(newItemPosition)).getProductName());
                    }
                    /* NOT IMPLEMENTED FOR ORDERS */
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof ProductEntity) {
                        ProductEntity newProduct = (ProductEntity) data.get(newItemPosition);
                        ProductEntity oldProduct = (ProductEntity) mData.get(newItemPosition);
                        return Objects.equals(newProduct.getProductName(), oldProduct.getProductName())
                                && Objects.equals(newProduct.getPrice(), oldProduct.getPrice())
                                && Objects.equals(newProduct.getColor(), oldProduct.getColor());
                    }
                    if (mData instanceof OrderEntity) {
                        OrderEntity newOrder = (OrderEntity) data.get(newItemPosition);
                        OrderEntity oldOrder = (OrderEntity) mData.get(newItemPosition);
                        return Objects.equals(newOrder.getIdOrder(), oldOrder.getIdOrder())
                                && Objects.equals(newOrder.getOrderDate(), oldOrder.getOrderDate())
                                && Objects.equals(newOrder.getClientEmail(), oldOrder.getClientEmail())
                                && Objects.equals(newOrder.getProductId(), oldOrder.getProductId());
                    }
                    return false;
                }
            });
            mData = data;
            result.dispatchUpdatesTo(this);
        }
    }
}
