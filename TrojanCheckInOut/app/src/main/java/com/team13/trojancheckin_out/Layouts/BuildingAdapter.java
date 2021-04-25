package com.team13.trojancheckin_out.Layouts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.WriterException;
import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;
import com.team13.trojancheckin_out.Database.MyBuildingCallback;
import com.team13.trojancheckin_out.UPC.Building;

import java.util.List;
import java.util.Map;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import static com.team13.trojancheckin_out.Layouts.Startup.buildingManipulator;

public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.BuildingViewHolder> {

    //this context we will use to inflate the layout
    private Context mCtx;
    private ImageButton qrButton;
    private Button cap, studentList;
    private User user;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    //NEW STUFF
    private Building building;

    public static final FirebaseStorage storage = FirebaseStorage.getInstance();
    public static final StorageReference buildingQRCodes = storage.getReference("QR Codes");
//    public static final StorageReference storageReference = FirebaseStorage.getInstance().getReference();




    //we are storing all the products in a list
    private List<Building> buildingList;

    //getting the context and product list with constructor
    public BuildingAdapter(Context mCtx, List<Building> buildingList) {
        this.mCtx = mCtx;
        this.buildingList = buildingList;
    }

    @Override
    public BuildingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_products, null);
        return new BuildingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BuildingViewHolder holder, int position) {
        //getting the product of the specified position
        //Building building was the og. i added private member.
        building = buildingList.get(position);
        System.out.println("CAPACITY: " + building.getCapacity());
        System.out.println("PERCENT: " + building.getPercent());

        // Retrieve currentCount
        buildingManipulator.getCurrentBuildings(new MyBuildingCallback() {
            @Override
            public void onCallback(Map<String, Building> map) {
                int count = map.get(building.getAbbreviation()).getCurrentCount();
                System.out.println("COUNT: " + count);
                double cur = (double) count;
                System.out.println("CUR: " + cur);
                double cap = (double) building.getCapacity();
                System.out.println("CAP: " + cap);
                double perc = (cur / cap) * 100;
                System.out.println("PERC: " + perc);
                int percent = (int) perc;
                System.out.println("PERCENT: " + percent);
                holder.textViewCurrent.setText(String.valueOf(count));
                holder.textViewPercent.setText(String.valueOf(percent) + "%");
                holder.progressBar.setProgress(percent);
            }
        });

        //binding the data with the viewholder views
        holder.textViewTitle.setText(building.getAbbreviation());
        holder.textViewCapacity.setText(String.valueOf(building.getCapacity()));

        qrButton = holder.imageButton;
        cap = holder.capacity;
        studentList = holder.profile;

        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = LayoutInflater.from(mCtx);
                View popupView = inflater.inflate(R.layout.qr_popup, null);
                Button closeButton = (Button) popupView.findViewById(R.id.button6);

                TextView buildingName = (TextView) popupView.findViewById(R.id.textView19);
                buildingName.setText(buildingList.get(position).getName());

                ImageView qrImage = (ImageView) popupView.findViewById(R.id.imageView8);

                // QR Code Generation

                // getting width and
                // height of a point

                int w = 1200;
                int h = 1200;

                // generating dimension from width and height.
                int dimen = w < h ? w : h;
                dimen = dimen * 3 / 4;

                // setting this dimensions inside our qr code
                // encoder to generate our qr code.
                qrgEncoder = new QRGEncoder(buildingList.get(position).getAbbreviation(), null, QRGContents.Type.TEXT, dimen);
                try {
                    // getting our qrcode in the form of bitmap.
                    bitmap = qrgEncoder.encodeAsBitmap();
                    // the bitmap is set inside our image
                    // view using .setimagebitmap method.
                    qrImage.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    // this method is called for
                    // exception handling.
                    Log.e("Tag", e.toString());
                }

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.setElevation(20);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window token
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                // dismiss the popup window when touched
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

        cap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = LayoutInflater.from(mCtx);
                View popupView = inflater.inflate(R.layout.cap_popup, null);
                Button closeButton = (Button) popupView.findViewById(R.id.button6);

                Button submitButton = (Button) popupView.findViewById(R.id.button9);
                TextView name = (TextView) popupView.findViewById(R.id.textView18);
                name.setText(buildingList.get(position).getName());
                EditText num = (EditText) popupView.findViewById(R.id.editTextNumber2);
                //name.setText(building.getName());

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.setElevation(20);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window token
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                // dismiss the popup window when touched
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });


                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String w = num.getText().toString();
                        int x = Integer.parseInt(w);
                        // If cap is less than current count
                        String hold = holder.textViewCurrent.getText().toString();
                        int y = Integer.parseInt(hold);

                        if(x < y || x < 0) {
                            Toast.makeText(v.getContext(), "New capacity cannot be less than current count of students!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            buildingList.get(position).setCapacity(x, buildingList.get(position).getAbbreviation());
                        }
                        popupWindow.dismiss();
                    }
                });

            }
        });

        studentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                building = buildingList.get(position);
                Intent intent = new Intent(v.getContext(), StudentsList.class);
                intent.putExtra("PrevPageData", building);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (buildingList == null) return 0;
        if (buildingList.isEmpty()) return 0;
        return buildingList.size();
    }


    class BuildingViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewCurrent, textViewCapacity, textViewPercent;
        ImageButton imageButton;
        ProgressBar progressBar;
        Button capacity, profile;

        public BuildingViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.buildName);
            textViewCurrent = itemView.findViewById(R.id.textViewCurrent);
            textViewCapacity = itemView.findViewById(R.id.textViewCapacity);
            textViewPercent = itemView.findViewById(R.id.textViewPercent);
            progressBar = itemView.findViewById(R.id.progressBar);
            imageButton = itemView.findViewById(R.id.imageButton4);
            capacity = itemView.findViewById(R.id.capButton);
            profile = itemView.findViewById(R.id.profileButton);
        }
    }

}
