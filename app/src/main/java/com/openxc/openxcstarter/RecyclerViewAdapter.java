package com.openxc.openxcstarter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Aws on 28/01/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Book> mData ;


    public RecyclerViewAdapter(Context mContext, List<Book> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardveiw_item_book,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tv_book_title.setText(mData.get(position).getTitle());
        holder.img_book_thumbnail.setImageResource(mData.get(position).getThumbnail());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mData.get(position).getTitle()=="Achievements") {
                    Toast.makeText(getApplicationContext(), "Achievement", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, AchievementActivity.class);

                    // passing data to the book activity
                    intent.putExtra("Title", mData.get(position).getTitle());
                    intent.putExtra("Description", mData.get(position).getDescription());
                    intent.putExtra("Thumbnail", mData.get(position).getThumbnail());
                    // start the activity
                    mContext.startActivity(intent);
                }
                else if(mData.get(position).getTitle()=="LeaderBoard") {
                    Toast.makeText(getApplicationContext(), "LeaderBoard", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, LeaderBoard.class);

                    // passing data to the book activity
                    intent.putExtra("Title", mData.get(position).getTitle());
                    intent.putExtra("Description", mData.get(position).getDescription());
                    intent.putExtra("Thumbnail", mData.get(position).getThumbnail());
                    // start the activity
                    mContext.startActivity(intent);
                }
                else if(mData.get(position).getTitle()=="Username") {
                    Toast.makeText(getApplicationContext(), "Username", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, UserName.class);

                    // passing data to the book activity
                    intent.putExtra("Title", mData.get(position).getTitle());
                    intent.putExtra("Description", mData.get(position).getDescription());
                    intent.putExtra("Thumbnail", mData.get(position).getThumbnail());
                    // start the activity
                    mContext.startActivity(intent);
                }
                else if(mData.get(position).getTitle()=="Ignition Status") {
                    Toast.makeText(getApplicationContext(), "Ignition Status", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, IgnitionStatus.class);

                    // passing data to the book activity
                    intent.putExtra("Title", mData.get(position).getTitle());
                    intent.putExtra("Description", mData.get(position).getDescription());
                    intent.putExtra("Thumbnail", mData.get(position).getThumbnail());
                    // start the activity
                    mContext.startActivity(intent);
                }
                else if(mData.get(position).getTitle()=="Score") {
                    Toast.makeText(getApplicationContext(), "Score", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, Score.class);

                    // passing data to the book activity
                    intent.putExtra("Title", mData.get(position).getTitle());
                    intent.putExtra("Description", mData.get(position).getDescription());
                    intent.putExtra("Thumbnail", mData.get(position).getThumbnail());
                    // start the activity
                    mContext.startActivity(intent);
                }
                else if(mData.get(position).getTitle()=="Distance") {
                    Toast.makeText(getApplicationContext(), "Distance", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, Distance.class);

                    // passing data to the book activity
                    intent.putExtra("Title", mData.get(position).getTitle());
                    intent.putExtra("Description", mData.get(position).getDescription());
                    intent.putExtra("Thumbnail", mData.get(position).getThumbnail());
                    // start the activity
                    mContext.startActivity(intent);
                }
                else if(mData.get(position).getTitle()=="Fuel Level") {
                    Toast.makeText(getApplicationContext(), "Fuel Level", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, FuelLevel.class);

                    // passing data to the book activity
                    intent.putExtra("Title", mData.get(position).getTitle());
                    intent.putExtra("Description", mData.get(position).getDescription());
                    intent.putExtra("Thumbnail", mData.get(position).getThumbnail());
                    // start the activity
                    mContext.startActivity(intent);
                }
                else if(mData.get(position).getTitle()=="Nearest Gas Station") {
                    Toast.makeText(getApplicationContext(), "Nearest Gas Station", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, NearestGasStation.class);

                    // passing data to the book activity
                    intent.putExtra("Title", mData.get(position).getTitle());
                    intent.putExtra("Description", mData.get(position).getDescription());
                    intent.putExtra("Thumbnail", mData.get(position).getThumbnail());
                    // start the activity
                    mContext.startActivity(intent);
                }
                else if(mData.get(position).getTitle()=="Emergency Button") {
                    Toast.makeText(getApplicationContext(), "Emergency Button", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, EmergencyButton.class);

                    // passing data to the book activity
                    intent.putExtra("Title", mData.get(position).getTitle());
                    intent.putExtra("Description", mData.get(position).getDescription());
                    intent.putExtra("Thumbnail", mData.get(position).getThumbnail());
                    // start the activity
                    mContext.startActivity(intent);
                }
                else if(mData.get(position).getTitle()=="Speed") {
                    Toast.makeText(getApplicationContext(), "Speed", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, Speed.class);

                    // passing data to the book activity
                    intent.putExtra("Title", mData.get(position).getTitle());
                    intent.putExtra("Description", mData.get(position).getDescription());
                    intent.putExtra("Thumbnail", mData.get(position).getThumbnail());
                    // start the activity
                    mContext.startActivity(intent);
                }
                else if(mData.get(position).getTitle()=="Gear Position") {
                    Toast.makeText(getApplicationContext(), "Gear Position", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, GearPosition.class);

                    // passing data to the book activity
                    intent.putExtra("Title", mData.get(position).getTitle());
                    intent.putExtra("Description", mData.get(position).getDescription());
                    intent.putExtra("Thumbnail", mData.get(position).getThumbnail());
                    // start the activity
                    mContext.startActivity(intent);
                }
                else if(mData.get(position).getTitle()=="Accelerator Pedal Position") {
                    Toast.makeText(getApplicationContext(), "Accelerator Pedal Position", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, AcceleratorPedalPosition.class);

                    // passing data to the book activity
                    intent.putExtra("Title", mData.get(position).getTitle());
                    intent.putExtra("Description", mData.get(position).getDescription());
                    intent.putExtra("Thumbnail", mData.get(position).getThumbnail());
                    // start the activity
                    mContext.startActivity(intent);
                }
                else if(mData.get(position).getTitle()=="Brake Pedal Position") {
                    Toast.makeText(getApplicationContext(), "Brake Pedal Position", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, BrakePedalPosition.class);

                    // passing data to the book activity
                    intent.putExtra("Title", mData.get(position).getTitle());
                    intent.putExtra("Description", mData.get(position).getDescription());
                    intent.putExtra("Thumbnail", mData.get(position).getThumbnail());
                    // start the activity
                    mContext.startActivity(intent);
                }

                else if(mData.get(position).getTitle()=="Location") {
                    Toast.makeText(getApplicationContext(), "Location", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, Location.class);

                    // passing data to the book activity
                    intent.putExtra("Title", mData.get(position).getTitle());
                    intent.putExtra("Description", mData.get(position).getDescription());
                    intent.putExtra("Thumbnail", mData.get(position).getThumbnail());
                    // start the activity
                    mContext.startActivity(intent);
                }
                else if(mData.get(position).getTitle()=="Settings") {
                    Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, Settings.class);

                    // passing data to the book activity
                    intent.putExtra("Title", mData.get(position).getTitle());
                    intent.putExtra("Description", mData.get(position).getDescription());
                    intent.putExtra("Thumbnail", mData.get(position).getThumbnail());
                    // start the activity
                    mContext.startActivity(intent);
                }

                else {

                    Toast.makeText(getApplicationContext(), "Not Achievement", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, Book_Activity.class);

                    // passing data to the book activity
                    intent.putExtra("Title", mData.get(position).getTitle());
                    intent.putExtra("Description", mData.get(position).getDescription());
                    intent.putExtra("Thumbnail", mData.get(position).getThumbnail());
                    // start the activity
                    mContext.startActivity(intent);
                }

            }
        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public Context getApplicationContext() {
        return mContext;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_book_title;
        ImageView img_book_thumbnail;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_book_title = (TextView) itemView.findViewById(R.id.book_title_id) ;
            img_book_thumbnail = (ImageView) itemView.findViewById(R.id.book_img_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);


        }
    }


}