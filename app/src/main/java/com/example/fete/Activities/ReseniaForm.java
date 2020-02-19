package com.example.fete.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.fete.R;

public class ReseniaForm extends AppCompatActivity implements View.OnClickListener {


    ImageView star1;
    ImageView star2;
    ImageView star3;
    ImageView star4;
    ImageView star5;
    Button btnGuardarResenia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resenia_form);

        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);
        btnGuardarResenia = findViewById(R.id.btnGuardarResenia);

        star1.setOnClickListener(this);
        star2.setOnClickListener(this);
        star3.setOnClickListener(this);
        star4.setOnClickListener(this);
        star5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.star1:
                star1.setImageResource(R.drawable.ic_star_black_24dp);
                star2.setImageResource(R.drawable.ic_star_border_black_24dp);
                star3.setImageResource(R.drawable.ic_star_border_black_24dp);
                star4.setImageResource(R.drawable.ic_star_border_black_24dp);
                star5.setImageResource(R.drawable.ic_star_border_black_24dp);
                break;
            case R.id.star2:
                star1.setImageResource(R.drawable.ic_star_black_24dp);
                star2.setImageResource(R.drawable.ic_star_black_24dp);
                star3.setImageResource(R.drawable.ic_star_border_black_24dp);
                star4.setImageResource(R.drawable.ic_star_border_black_24dp);
                star5.setImageResource(R.drawable.ic_star_border_black_24dp);
                break;
            case R.id.star3:
                star1.setImageResource(R.drawable.ic_star_black_24dp);
                star2.setImageResource(R.drawable.ic_star_black_24dp);
                star3.setImageResource(R.drawable.ic_star_black_24dp);
                star4.setImageResource(R.drawable.ic_star_border_black_24dp);
                star5.setImageResource(R.drawable.ic_star_border_black_24dp);
                break;
            case R.id.star4:
                star1.setImageResource(R.drawable.ic_star_black_24dp);
                star2.setImageResource(R.drawable.ic_star_black_24dp);
                star3.setImageResource(R.drawable.ic_star_black_24dp);
                star4.setImageResource(R.drawable.ic_star_black_24dp);
                star5.setImageResource(R.drawable.ic_star_border_black_24dp);
                break;
            case R.id.star5:
                star1.setImageResource(R.drawable.ic_star_black_24dp);
                star2.setImageResource(R.drawable.ic_star_black_24dp);
                star3.setImageResource(R.drawable.ic_star_black_24dp);
                star4.setImageResource(R.drawable.ic_star_black_24dp);
                star5.setImageResource(R.drawable.ic_star_black_24dp);
                break;
            case R.id.btnGuardarResenia:
                break;
        }
    }
}
