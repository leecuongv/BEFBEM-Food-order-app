package com.foa.orderfoodserver.ViewHolder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.foa.orderfoodserver.Common.Common;
import com.foa.orderfoodserver.Interface.ItemClickListener;
import com.foa.orderfoodserver.R;



public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener {
    public TextView txtOrderId,txtOrderStatus,txtOrderPhone,txtGmail, txtTotal,txtFood;
    public ListView lvOrders;
    private ItemClickListener itemClickListener;
    Button btnUpdate;
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public OrderViewHolder(View itemView) {
        super(itemView);
        txtOrderId = itemView.findViewById(R.id.order_id);
        txtOrderStatus = itemView.findViewById(R.id.order_status);
        txtOrderPhone = itemView.findViewById(R.id.order_phone);
        txtGmail = itemView.findViewById(R.id.order_gmail);
        txtTotal = itemView.findViewById(R.id.order_total);
        lvOrders = itemView.findViewById(R.id.lv_orders);
        btnUpdate=itemView.findViewById(R.id.btnUpdate);


        itemView.setOnClickListener(this);
        btnUpdate.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.OnClick(view,getAdapterPosition(),false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Chọn hành động");
        contextMenu.add(0,0,getAdapterPosition(), Common.UPDATE);
        contextMenu.add(0,1,getAdapterPosition(),Common.DELETE);
    }
}
