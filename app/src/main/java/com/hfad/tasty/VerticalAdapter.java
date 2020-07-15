package com.hfad.tasty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.RenderMode;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.hfad.tasty.CartActivity.uniqify;
import static com.hfad.tasty.Details.mFood;
import static com.hfad.tasty.MainActivity.cartUpdate;
import static com.hfad.tasty.MainActivity.lottieCart;
import static com.hfad.tasty.MainActivity.nt;
import static com.hfad.tasty.MainActivity.recyclerViewHorizontal;
import static com.hfad.tasty.MainActivity.uniquecart;
import static com.hfad.tasty.MainActivity.view;


public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.VerticalViewHolder> {

    private List<GeneralFood> regularFoods;
    private Context context;


    public static class VerticalViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout p;
        RatingBar ratingBar;

        CardView verticalLayout;
        TextView regularTitle;
        TextView regularPrice,textView;
        KenBurnsView regularImage;
        LottieAnimationView regularPlus;

        public VerticalViewHolder(View itemView) {
            super(itemView);

            verticalLayout = itemView.findViewById(R.id.vertical_parent_layout);
            regularTitle = itemView.findViewById(R.id.regular_food_title);
            regularImage = itemView.findViewById(R.id.regular_food_pc);
            regularPrice = itemView.findViewById(R.id.regular_food_price);
            regularPlus = itemView.findViewById(R.id.regular_food_plus);
            textView = itemView.findViewById(R.id.textView4);//quantity
            ratingBar = itemView.findViewById(R.id.ratingBar);
            p = itemView.findViewById(R.id.nightt);



        }
    }

    public VerticalAdapter(List<GeneralFood> regularFoods, int vertical_recyclerview, Context context){
        this.context = context;
        this.regularFoods = regularFoods;
    }

    @NonNull
    @Override
    public VerticalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_vertical, parent, false);
        return new VerticalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VerticalViewHolder holder, final int position) {
        holder.regularImage.setClipToOutline(true);
        Picasso.get().load(regularFoods.get(position).getImage()).fit().into(holder.regularImage);
        holder.regularTitle.setText(regularFoods.get(position).getTitle());
        holder.regularPrice.setText((Double.toString((regularFoods.get(position).getPrice()))) + " Rs");
        LottieAnimationView lottie = holder.itemView.findViewById(R.id.wwe);
        lottie.setAnimation("down.json");
        lottie.playAnimation();
        holder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(fromUser){
                    regularFoods.get(position).setRating(rating);
                    holder.ratingBar.setRating(regularFoods.get(position).getRating());
                }
            }

        });
        holder.ratingBar.setRating(regularFoods.get(position).getRating());
        holder.regularPlus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                holder.regularPlus.setMinAndMaxFrame(35,119);
                lottieCart.setMinAndMaxFrame(29,98);
                view.setMinAndMaxFrame(29,89);
                view.playAnimation();
                lottieCart.playAnimation();
                holder.regularPlus.playAnimation();
                uniquecart.add(regularFoods.get(position));
                uniqify();
                cartUpdate();
                regularFoods.get(position).setQuantity(regularFoods.get(position).getQuantity()+1);
                holder.textView.setText(Integer.toString(regularFoods.get(position).getQuantity()));
            }});
        holder.textView.setText(Integer.toString(regularFoods.get(position).getQuantity()));

        holder.verticalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Details.class);
                intent.putExtra("position",position);
                mFood.add(regularFoods.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                context.startActivity(intent);
            }
        });
        if (nt){
            holder.p.setBackgroundColor(Color.rgb(40,51,74));
        }else {
            holder.p.setBackgroundColor(Color.rgb(212,36,5));
        }
        switch (position){
            case 0:
                recyclerViewHorizontal.smoothScrollToPosition(0);
                break;
            case 4:
                recyclerViewHorizontal.smoothScrollToPosition(1);
                break;
            case 7:
                recyclerViewHorizontal.smoothScrollToPosition(2);
                break;
            case 8:
                recyclerViewHorizontal.smoothScrollToPosition(3);
                break;
            case 14:
                recyclerViewHorizontal.smoothScrollToPosition(4);
                break;

        }
    }

    @Override
    public void onViewRecycled(@NonNull VerticalAdapter.VerticalViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return regularFoods.size();
    }
}
