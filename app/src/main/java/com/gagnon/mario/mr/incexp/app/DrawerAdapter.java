package com.gagnon.mario.mr.incexp.app;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mario on 12/30/2015.
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder>{

    private static final int TYPE_HEADER = 0;  // Declaring Variable to Understand which View is being worked on
    // IF the view under inflation and population is header or Item
    private static final int TYPE_ITEM = 1;

    private String mNavTitles[]; // String Array to store the passed titles Value from MainActivity.java
    private int mIcons[];       // Int Array to store the passed icons resource value from MainActivity.java

    private String mName;        //String Resource for header View mName
    private Uri mProfile;        //int Resource for header view mProfile picture
    private int mProfileInt;
    private String mEmail;       //String Resource for header view mEmail

    public static class ViewHolder extends RecyclerView.ViewHolder {
        int mHolderid;

        TextView mTextView;
        ImageView mImageView;
        ImageView mProfile;
        TextView mName;
        TextView mEmail;


        public ViewHolder(View itemView,int viewType) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);


            // Here we set the appropriate view in accordance with the the view type as passed when the holder object is created

            if(viewType == TYPE_ITEM) {
                mTextView = (TextView) itemView.findViewById(R.id.rowText); // Creating TextView object with the id of mTextView from item_row.xml
                mImageView = (ImageView) itemView.findViewById(R.id.rowIcon);// Creating ImageView object with the id of ImageView from item_row.xml
                mHolderid = 1;                                               // setting holder id as 1 as the object being populated are of type item row
            }
            else{
                mName = (TextView) itemView.findViewById(R.id.name);         // Creating Text View object from header.xml for name
                mEmail = (TextView) itemView.findViewById(R.id.email);       // Creating Text View object from header.xml for mEmail
                mProfile = (ImageView) itemView.findViewById(R.id.circleView);// Creating Image view object from header.xml for mProfile pic
                mHolderid = 0;                                                // Setting holder id = 0 as the object being populated are of type header view
            }
        }

    }

    DrawerAdapter(String titles[], int icons[], String name, String email, Uri profile){ // DrawerAdapter Constructor with titles and icons parameter
        // titles, icons, name, mEmail, mProfile pic are passed from the main activity as we
        mNavTitles = titles;                //have seen earlier
        mIcons = icons;
        mName = name;
        mEmail = email;
        mProfile = profile;                     //here we assign those passed values to the values we declared here
        mProfileInt = 0;
        //in adapter
    }

    DrawerAdapter(String titles[], int icons[], String name, String email, int profile){ // DrawerAdapter Constructor with titles and icons parameter
        // titles, icons, name, mEmail, mProfile pic are passed from the main activity as we
        mNavTitles = titles;                //have seen earlier
        mIcons = icons;
        mName = name;
        mEmail = email;
        mProfile = null;
        mProfileInt = profile;                     //here we assign those passed values to the values we declared here
        //in adapter
    }


    @Override
    public DrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false); //Inflating the layout

            ViewHolder vhItem = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view

            return vhItem; // Returning the created object

            //inflate your layout and pass it to view holder

        } else if (viewType == TYPE_HEADER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header,parent,false); //Inflating the layout

            ViewHolder vhHeader = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view

            return vhHeader; //returning the object created


        }
        return null;

    }

    @Override
    public void onBindViewHolder(DrawerAdapter.ViewHolder holder, int position) {
        if(holder.mHolderid ==1) {                              // as the list view is going to be called after the header view so we decrement the
            // position by 1 and pass it to the holder while setting the text and image
            holder.mTextView.setText(mNavTitles[position - 1]); // Setting the Text with the array of our Titles
            holder.mImageView.setImageResource(mIcons[position - 1]);// Settimg the image with array of our icons
        }
        else{

            if(null != mProfile) {
                holder.mProfile.setImageURI(mProfile);           // Similarly we set the resources for header view
            }else {
                holder.mProfile.setImageResource(mProfileInt);
            }
            holder.mName.setText(mName);
            holder.mEmail.setText(mEmail);
        }
    }

    @Override
    public int getItemCount() {
        return mNavTitles.length+1; // the number of items in the list will be +1 the titles including the header view.
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

}
