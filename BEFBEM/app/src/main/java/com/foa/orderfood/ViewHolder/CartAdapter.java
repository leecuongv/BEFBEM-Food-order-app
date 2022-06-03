package com.foa.orderfood.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.foa.orderfood.Common.Common;
import com.foa.orderfood.Model.Order;
import com.foa.orderfood.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


class CartViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnLongClickListener {
    public TextView txt_cart_name,txt_price;

    public void setTxt_cart_name(TextView txt_cart_name) {
        this.txt_cart_name = txt_cart_name;
    }

    public ImageView imd_cart_count;
    private CartAdapter.OnItemClickListener itemClickListener;
    private Button btnXoa;

    public CartViewHolder(View itemView, CartAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);
        txt_cart_name = itemView.findViewById(R.id.cart_item_name);
        txt_price = itemView.findViewById(R.id.cart_item_price);
        imd_cart_count = itemView.findViewById(R.id.cart_item_count);
        this.itemClickListener = onItemClickListener;
        itemView.setOnCreateContextMenuListener(this);

    }
    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Chọn hành động");
        contextMenu.add(0,0,getAdapterPosition(), Common.DELETE);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.deleteItem(getAdapterPosition());
        return true;
    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Order> listData = new ArrayList<>();
    private Context context;
    private OnItemClickListener onItemClickListener;

    public CartAdapter(List<Order> listData, Context context, OnItemClickListener onItemClickListener) {
        this.listData = listData;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout, parent, false);
        return new CartViewHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        TextDrawable drawable = TextDrawable.builder().buildRound("" + listData.get(position).getQuantity(), Color.RED);
        holder.imd_cart_count.setImageDrawable(drawable);

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        int price = (Integer.parseInt(listData.get(position).getPrice()))*(Integer.parseInt(listData.get(position).getQuantity()));
        holder.txt_price.setText(formatter.format(price)+" VND");
        holder.txt_cart_name.setText(listData.get(position).getProductName());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public interface OnItemClickListener {
        void deleteItem(int index);
    }

}

