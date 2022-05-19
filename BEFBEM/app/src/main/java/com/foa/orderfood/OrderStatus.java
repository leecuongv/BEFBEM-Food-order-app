package com.foa.orderfood;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foa.orderfood.Common.Common;
import com.foa.orderfood.Model.Request;
import com.foa.orderfood.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderStatus extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference requests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = (RecyclerView) findViewById(R.id.listOrder);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadOrders(Common.currentUser.getPhone());
    }
    private void loadOrders(String phone) {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests.orderByChild("phone").equalTo(phone)
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {
                viewHolder.txtOrderId.setText("ID đơn hàng:  "+adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText("Trạng thái:  "+Common.convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderPhone.setText("SĐT:  "+model.getPhone());
                viewHolder.txtGmail.setText("Địa chỉ:  "+model.getAddress());
                viewHolder.txtTotal.setText("Tổng:  "+model.getTotal());
            }
        };
        recyclerView.setAdapter(adapter);
    }
}
