package com.hfad.tasty;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;
import java.util.List;

import static com.hfad.tasty.CartActivity.cartLottie;
import static com.hfad.tasty.CartActivity.grandTotal;
import static com.hfad.tasty.CartActivity.priceAdjust;
import static com.hfad.tasty.MainActivity.cartUpdate;
import static com.hfad.tasty.MainActivity.lottieCart;
import static com.hfad.tasty.MainActivity.mAdapter;
import static com.hfad.tasty.MainActivity.uniquecart;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private CartAdapter mCartAdapter;


    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int position) {
            holder.cartTitle.setText(uniquecart.get(position).getTitle());
            holder.cartQuantity.setText(Integer.toString(uniquecart.get(position).getQuantity()));
            holder.cartPrice.setText((Double.toString((uniquecart.get(position).getPrice()))) + " Rs.");
            Picasso.get().load(uniquecart.get(position).getImage()).fit().into(holder.cartPicture);
            holder.lottie.setAnimation("down.json");
            holder.lottie.setSpeed(1f);
            holder.lottie.setScaleType(ImageView.ScaleType.FIT_END);
            holder.lottie.playAnimation();
            holder.ratingBar.setRating(uniquecart.get(position).getRating());

            holder.cartDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.cartDelete.setSpeed(1.5f);
                    holder.cartDelete.playAnimation();
                    new CountDownTimer(1600, 100) {

                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {
                            holder.cartDelete.setFrame(0);
                        }
                    }.start();
                    if(uniquecart.get(position).getQuantity()>0) {
                        uniquecart.get(position).setQuantity(uniquecart.get(position).getQuantity() - 1);
                        holder.cartQuantity.setText(Integer.toString(uniquecart.get(position).getQuantity()));

                    }
                    if(uniquecart.get(position).getQuantity()<1)
                    {
                        uniquecart.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(0, uniquecart.size());

                    }
                    grandTotal(uniquecart);
                    priceAdjust();
                    mAdapter.notifyDataSetChanged();
                    cartUpdate();
                    cartLottie.setMinAndMaxFrame(20,73);
                    cartLottie.setSpeed(-1f);
                    cartLottie.playAnimation();


                }
            });



    }


    @Override
    public int getItemCount() {
        return uniquecart.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        KenBurnsView cartPicture;
        TextView cartTitle,cartQuantity,cartPrice;
        CardView cartParentLayout;
        LottieAnimationView lottie;
        RatingBar ratingBar;
        LottieAnimationView cartDelete;

        public CartViewHolder(View itemView) {
            super(itemView);

            cartPicture = itemView.findViewById(R.id.cart_food_pic);
            cartTitle = itemView.findViewById(R.id.cart_food_title);
            cartPrice = itemView.findViewById(R.id.cart_food_price);
            cartParentLayout = itemView.findViewById(R.id.cart_parent_layout);
            cartDelete = itemView.findViewById(R.id.cart_food_delete);
            cartQuantity = itemView.findViewById(R.id.cart_food_quantity);
            lottie = itemView.findViewById(R.id.wwe);
            ratingBar = itemView.findViewById(R.id.ratingBar);

        }
    }

    public CartAdapter(List<GeneralFood> cartFoods, int recyclerview_cart, Context context){
        this.context = context;
        uniquecart = cartFoods;

    }


}