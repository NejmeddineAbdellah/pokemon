package com.example.pokemon.pokeapi;

import com.example.pokemon.models.Pokemon;
import com.example.pokemon.models.PokemonRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokeapiService {

    @GET("pokemon")
    Call<PokemonRepository> ListPokemon();

    @GET("{indexpoke}/")
    Call<Pokemon> getPokemon(@Path("indexpoke") String indexpoke);


}
