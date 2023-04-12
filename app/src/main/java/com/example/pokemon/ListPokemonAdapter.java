package com.example.pokemon;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pokemon.models.Pokemon;

import java.util.ArrayList;
import java.util.Random;

public class ListPokemonAdapter extends RecyclerView.Adapter<ListPokemonAdapter.ViewHolder> {


    private ArrayList<Pokemon> dataset;
    private Context context;


    public ListPokemonAdapter(Context context) {

        this.context =context;

        dataset = new ArrayList<>();

    }


    @NonNull
    @Override
    public ListPokemonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_item_pokeman, parent, false);
        return new ViewHolder(view);

    }
    @Override
    public void onBindViewHolder(@NonNull ListPokemonAdapter.ViewHolder holder, int position) {

        Pokemon p = dataset.get(position);
        holder.nombreTextView.setText(p.getName());
        //picaso
        Glide.with(context).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + p.getNumber() + ".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.fotoImageView);

        GradientDrawable drb = new GradientDrawable();
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        drb.setColor(color);
        drb.setShape(GradientDrawable.RECTANGLE);
        setCornerRadii(drb, 110f, 110f, 110f, 110f);
        holder.fotoImageView.setBackground(drb);
    }
    private void setCornerRadii(GradientDrawable drawable,  float topLeftRadius, float topRightRadius,float bottomLeftRadius, float bottomRightRadius) {
        float[] radii = {topRightRadius, topRightRadius, topLeftRadius, topLeftRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius};
        drawable.setCornerRadii(radii);
    }

    @Override
    public int getItemCount() {
        return dataset.size();

    }

    public void adicionarListaPokemon(ArrayList<Pokemon> listaPokemon) {
        dataset.addAll(listaPokemon);
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView fotoImageView;
        private TextView nombreTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            fotoImageView = itemView.findViewById(R.id.fotoImageView);
            nombreTextView = itemView.findViewById(R.id.nombreTextView);

            ImageView img = fotoImageView.findViewById(R.id.fotoImageView);
        }
    }
}
