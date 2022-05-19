package com.foa.orderfoodserver.ViewHolder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.foa.orderfoodserver.Common.Common;
import com.foa.orderfoodserver.Interface.ItemClickListener;
import com.foa.orderfoodserver.R;



public class MenuViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener {
        public TextView menu_name;
        public ImageView menu_image;
    public Button btn;
        private ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public MenuViewHolder(View itemView) {
            super(itemView);
            menu_name= itemView.findViewById(R.id.menu_name);
            menu_image = itemView.findViewById(R.id.menu_image);
            btn = itemView.findViewById(R.id.btnUpdate1);

            btn.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.OnClick(view,getAdapterPosition(),false);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0,0,getAdapterPosition(), Common.UPDATE);
            menu.add(0,1,getAdapterPosition(), Common.DELETE);
        }
}
