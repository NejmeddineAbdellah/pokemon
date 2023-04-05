package com.example.pokemon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pokemon.models.Pokemon;
import com.example.pokemon.pokeapi.PokeapiService;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity2 extends AppCompatActivity {

    public Retrofit retrofit;
    private Pokemon p;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_chrz);
        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String id = extras.getString("index");

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://pokeapi.co/api/v2/pokemon/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            getData(id);
        }
    }

    private void getData(String pos) {
        PokeapiService service = retrofit.create(PokeapiService.class);
        Call<Pokemon> poke = service.getPokemon(pos);
        poke.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {

                TextView txt = findViewById(R.id.ch);
                TextView txtweight = findViewById(R.id.txtweight);
                TextView txtheight = findViewById(R.id.txtheight);
                ImageView img = findViewById(R.id.imgpok);
                Random rnd = new Random();
                int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

                GradientDrawable drb = new GradientDrawable();

                drb.setColor(color);
                drb.setShape(GradientDrawable.RECTANGLE);
                setCornerRadii(drb, 150f, 150f);
                img.setBackground(drb);
                Pokemon p = response.body();
                txt.setText(p.getName());
                txtheight.setText(p.getHeight()+" ft");
                txtweight.setText(p.getWeight()+" lbs");
                Glide.with(MainActivity2.this).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+pos+".png").into(img);

            }
            private void setCornerRadii(GradientDrawable drawable, float bottomLeftRadius, float bottomRightRadius) {
                float[] radii = {0, 0, 0,0, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius};
                drawable.setCornerRadii(radii);
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {

            }
        });

    }
}