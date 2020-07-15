package com.hfad.tasty;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import java.util.List;
import static com.hfad.tasty.MainActivity.recyclerViewVertical;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder>{
    private List<String> quant ;

    private List<GeneralFood> popularFoods;
    private Context context;

    public static class HorizontalViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public HorizontalViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.popular_food_price);
        }
    }
    public HorizontalAdapter(List<String> regularFoods, int HorizontalViewHolder, Context context){
        this.context = context;
        this.quant = regularFoods;
    }
    @NonNull
    @Override
    public HorizontalAdapter.HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_horizontal, parent, false);
        return new HorizontalAdapter.HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HorizontalViewHolder holder, final int position) {
        final LottieAnimationView lottie  = holder.itemView.findViewById(R.id.we);
        lottie.setWillNotDraw(false);
        lottie.setAnimation("down.json");
        lottie.setSpeed(1f);
        lottie.setScaleType(ImageView.ScaleType.FIT_END);
        lottie.playAnimation();
        holder.textView.setText(quant.get(position).toString());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0:
                        recyclerViewVertical.smoothScrollToPosition(0);
                        break;
                    case 1:
                        recyclerViewVertical.smoothScrollToPosition(4);
                        break;
                    case 2:
                        recyclerViewVertical.smoothScrollToPosition(7);
                        break;
                    case 3:
                        recyclerViewVertical.smoothScrollToPosition(8);
                        break;
                    case 4:
                        recyclerViewVertical.smoothScrollToPosition(15);
                        break;

                }
            }
        });

    }



    @Override
    public void onViewRecycled(@NonNull HorizontalViewHolder holder) {
        super.onViewRecycled(holder);
        LottieAnimationView lottie = (LottieAnimationView) holder.itemView.findViewById(R.id.we);
        lottie.cancelAnimation();
        lottie.setWillNotDraw(true);
    }



    @Override
    public int getItemCount() {
        return quant.size();
    }
}
