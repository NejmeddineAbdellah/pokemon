package com.example.pokemon;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.pokemon.models.Pokemon;
import com.example.pokemon.models.PokemonRepository;
import com.example.pokemon.pokeapi.PokeapiService;
import java.util.ArrayList;
import java.util.Random;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "POKEDEX";
    private RecyclerView recyclerView;
    TextView txtname;
    ImageView img;
    private ListPokemonAdapter listaPokemonAdapter;
 public Retrofit retrofit;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activite_pokemon);

        recyclerView = findViewById(R.id.recyclerView);
        listaPokemonAdapter = new ListPokemonAdapter(this);
        recyclerView.setAdapter(listaPokemonAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        //=================================Click=================================
            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent2 = new Intent(MainActivity.this,MainActivity2.class);
                    intent2.putExtra("index",String.valueOf(position+1));
                    startActivity(intent2);
                }

                @Override
                public void onItemLongClick(View view, int position) {
                    // ...
                }
            }));

            //=================================Click=================================

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getData();
    }

    private void getData() {
        PokeapiService service = retrofit.create(PokeapiService.class);
        Call<PokemonRepository> pokemonRespuestaCall = service.ListPokemon();
        pokemonRespuestaCall.enqueue(new Callback<PokemonRepository>() {
            @Override
            public void onResponse(Call<PokemonRepository> call, Response<PokemonRepository> response) {

                if (response.isSuccessful()) {
                    PokemonRepository pokemonRespuesta = response.body();
                    ArrayList<Pokemon> listaPokemon = pokemonRespuesta.getResults();
                    listaPokemonAdapter.adicionarListaPokemon(listaPokemon);


                    for(int i=0;i<listaPokemon.size();i++){

                        Pokemon p=listaPokemon.get(i);
                        Log.i(TAG,"Pokemon:"+p.getName());
                    }
                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonRepository> call, Throwable t) {
                //aptoPar aCargar = true;
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }

    public void sendMessage(View view) {

        txtname = findViewById(R.id.nombreTextView);
        String name = (String) txtname.getText();
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("name",name);
        startActivity(intent);
    }




}