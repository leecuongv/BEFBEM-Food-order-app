package com.foa.orderfoodserver;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.foa.orderfoodserver.Common.Common;
import com.foa.orderfoodserver.Common.OrderAdapter;
import com.foa.orderfoodserver.Interface.ItemClickListener;
import com.foa.orderfoodserver.Model.Request;
import com.foa.orderfoodserver.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class OrderStatus extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase db;
    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;
    DatabaseReference requests;
    MaterialSpinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        db = FirebaseDatabase.getInstance();
        requests = db.getReference("Requests");


        recyclerView =  findViewById(R.id.listOrder);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrder();
    }
    private void loadOrder() {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {

                viewHolder.txtOrderId.setText("Id đơn hàng : "+adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText("Trạng thái : "+Common.convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderPhone.setText("SĐT : "+model.getPhone());
                viewHolder.txtGmail.setText("Địa chỉ : "+model.getAddress());
                viewHolder.txtTotal.setText("Giá tiền : "+model.getTotal());

                OrderAdapter orderAdapter = new OrderAdapter(model.getFoods() , getApplicationContext());
                viewHolder.lvOrders.setAdapter(orderAdapter);

                Log.d("OrderStatus", "populateViewHolder:  " + model.getFoods().size());


                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void OnClick(View view, int position, boolean isLongClick) {

                    }
                });

            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(Common.UPDATE))
            showUpdateDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        else if (item.getTitle().equals(Common.DELETE))
            deleteOrder(adapter.getRef(item.getOrder()).getKey());
        return super.onContextItemSelected(item);
    }

    private void deleteOrder(String key) {
        requests.child(key).removeValue();

    }

    private void showUpdateDialog(String key, final Request item) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderStatus.this);
        alertDialog.setTitle("Sửa đơn đặt");
        alertDialog.setMessage("Hãy chọn trạng thái");

        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.update_order_layout,null);

        spinner = (MaterialSpinner)view.findViewById(R.id.statusSpiner);
        spinner.setItems("Đã đặt hàng","Đang gửi thức ăn","Đã gửi thức ăn");
        alertDialog.setView(view);

        final String localKey = key;
        alertDialog.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                item.setStatus(String.valueOf(spinner.getSelectedIndex()));

                requests.child(localKey).setValue(item);
            }
        });
        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();

    }

}
