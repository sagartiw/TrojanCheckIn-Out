package com.team13.trojancheckin_out.Layouts;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;
import com.team13.trojancheckin_out.Database.MyBuildingCallback;
import com.team13.trojancheckin_out.UPC.Building;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.team13.trojancheckin_out.Layouts.Startup.buildingManipulator;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    //this context we will use to inflate the layout
    private Context mCtx;
    private Button profileButton, historyBuilding;
    private RecyclerView recyclerView;

    //we are storing all the products in a list
    private List<User> studentList;
    private List<History> historyList;

    //getting the context and product list with constructor
    public StudentAdapter(Context mCtx, List<User> studentList) {
        this.mCtx = mCtx;
        this.studentList = studentList;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_students, null);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        //getting the product of the specified position
        User student = studentList.get(position);

        //binding the data with the viewholder views
        holder.fullName.setText(student.getName());
        holder.studentID.setText(String.valueOf(student.getId()));
        holder.currentBuilding.setText("Current Building: " + student.getCurrentBuilding().getAbbreviation());

        profileButton = holder.profileButton;
        historyBuilding = holder.historyButton;

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = LayoutInflater.from(mCtx);
                View popupView = inflater.inflate(R.layout.student_profile_popup, null);
                Button closeButton = (Button) popupView.findViewById(R.id.button6);
                TextView nameText = (TextView) popupView.findViewById(R.id.name6);
                TextView idText = (TextView) popupView.findViewById(R.id.id5);
                TextView majorText = (TextView) popupView.findViewById(R.id.id6);
                TextView buildingName = (TextView) popupView.findViewById(R.id.buildingName2);

                nameText.setText(student.getName());
                idText.setText(student.getId());
                majorText.setText(student.getMajor());
                buildingName.setText(student.getCurrentBuilding().getAbbreviation());

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

        historyBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = LayoutInflater.from(mCtx);
                View popupView = inflater.inflate(R.layout.student_history_popup, null);
                Button closeButton = (Button) popupView.findViewById(R.id.button6);
                TextView nameText = (TextView) popupView.findViewById(R.id.nameTitle4);

                nameText.setText(student.getName());

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

                //getting the recyclerview from xml
                recyclerView = (RecyclerView) popupView.findViewById(R.id.recyclerView2);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

                //get current buildings
                historyList = new ArrayList<>();

                historyList.add(
                    new History(
                            "SAL",
                            "Check In",
                            "11:30 AM"
                    ));
                historyList.add(
                        new History(
                                "SAL",
                                "Check Out",
                                "2:30 PM"
                        ));
                historyList.add(
                        new History(
                                "SAL",
                                "Check In",
                                "11:30 AM"
                        ));
                historyList.add(
                        new History(
                                "SAL",
                                "Check Out",
                                "2:30 PM"
                        ));
                historyList.add(
                        new History(
                                "SAL",
                                "Check In",
                                "11:30 AM"
                        ));
                historyList.add(
                        new History(
                                "SAL",
                                "Check Out",
                                "2:30 PM"
                        ));

                //creating recyclerview adapter
                HistoryAdapter adapter = new HistoryAdapter(view.getContext(), historyList);

                //setting adapter to recyclerview
                recyclerView.setAdapter(adapter);

                // dismiss the popup window when touched
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        if (studentList == null) return 0;
        if (studentList.isEmpty()) return 0;
        return studentList.size();
    }


    class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView fullName, studentID, currentBuilding;
        Button profileButton, historyButton;

        public StudentViewHolder(View itemView) {
            super(itemView);

            fullName = itemView.findViewById(R.id.fullName);
            studentID = itemView.findViewById(R.id.studentID);
            currentBuilding = itemView.findViewById(R.id.currentBuilding);
            profileButton = itemView.findViewById(R.id.profileButton);
            historyButton = itemView.findViewById(R.id.historyButton);
        }
    }

}
