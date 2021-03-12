package com.team13.trojancheckin_out.Layouts;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.team13.trojancheckin_out.R;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

/**
 * Created by Belal on 10/18/2017.
 */


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;
    private ImageButton qrButton;

    //we are storing all the products in a list
    private List<Product> productList;

    //getting the context and product list with constructor
    public ProductAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_products, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        Product product = productList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(product.getTitle());
        holder.textViewCurrent.setText(String.valueOf(product.getCurrent()));
        holder.textViewCapacity.setText(String.valueOf(product.getCapacity()));
        holder.textViewPercent.setText(String.valueOf(product.getPercent()) + "%");
        holder.progressBar.setProgress((product.getPercent()));

        qrButton = holder.imageButton;

        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = LayoutInflater.from(mCtx);
                View popupView = inflater.inflate(R.layout.qr_popup, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window token
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                // dismiss the popup window when touched
                /*
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });*/
            }
        });
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewCurrent, textViewCapacity, textViewPercent;
        ImageButton imageButton;
        ProgressBar progressBar;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewCurrent = itemView.findViewById(R.id.textViewCurrent);
            textViewCapacity = itemView.findViewById(R.id.textViewCapacity);
            textViewPercent = itemView.findViewById(R.id.textViewPercent);
            progressBar = itemView.findViewById(R.id.progressBar);
            imageButton = itemView.findViewById(R.id.imageButton4);
        }
    }

}
