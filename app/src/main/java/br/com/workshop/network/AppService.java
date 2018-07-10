package br.com.workshop.network;

import java.util.List;

import br.com.workshop.model.Talks;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AppService {

    @GET("talks")
    Call<List<Talks>> getTalks();
}
