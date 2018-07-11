package br.com.workshop.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import br.com.workshop.R;
import br.com.workshop.model.Talks;

public class DetailActivity extends AppCompatActivity {

    private Talks talk;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle bundle = getIntent().getExtras();

        talk = (Talks) bundle.getSerializable("talk");

        getSupportActionBar().setTitle(talk.name);

        ImageView image = findViewById(R.id.image);
        TextView description = findViewById(R.id.description);

        description.setText(talk.description);
        Glide.with(this).load(talk.image).into(image);

    }


}
