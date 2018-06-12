package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    //Define the widget on the details layout
    private TextView alsoKnownAsTextView;
    private  TextView placeOfOriginTextView;
    private  TextView descriptionTextView;
    private TextView ingredientsTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        //Find views by ID
        alsoKnownAsTextView=findViewById(R.id.also_known_tv);
        placeOfOriginTextView=findViewById(R.id.origin_tv);
        descriptionTextView=findViewById(R.id.description_tv);
        ingredientsTextView=findViewById(R.id.ingredients_tv);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        //populate UI passing the recovered sandwich object as parameter
        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);



        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        //Implement textView justification for Android Oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            descriptionTextView.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }

        //Append string array for alsoKnownAs field
        for (String alsoKnownAs : sandwich.getAlsoKnownAs()){
            alsoKnownAsTextView.append(alsoKnownAs+ "\n");
        }

        //set text for placeOfOrigin and description
        placeOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        descriptionTextView.setText(sandwich.getDescription());


        //append string array for ingredients
        for (String ingredient: sandwich.getIngredients()){
            ingredientsTextView.append(ingredient+"\n");
        }
    }
}
