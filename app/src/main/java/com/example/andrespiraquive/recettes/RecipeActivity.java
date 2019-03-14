package com.example.andrespiraquive.recettes;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class RecipeActivity extends AppCompatActivity {

    private TextView tvtitle, tvdescription, tvingredient, tvpreparation;
    private ImageView img;
    private RatingBar note;
    private RatingBar ratingBar;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_recipe);

        db = FirebaseFirestore.getInstance ();
        img = (ImageView) findViewById (R.id.recipe_details_thumbnail_id);
        tvtitle = (TextView) findViewById (R.id.recipe_details_title_id);
        note = (RatingBar) findViewById (R.id.recipe_details_raiting_id);
        tvdescription = (TextView) findViewById (R.id.recipe_details_description_id);
        tvingredient = (TextView) findViewById (R.id.recipe_details_ingredient_id);
        tvpreparation = (TextView) findViewById (R.id.recipe_details_preparation_id);

        Picasso.get ()
                .load ("https://firebasestorage.googleapis.com/v0/b/recettes-bb215.appspot.com/o/image_plat_base_free.jpg?alt=media&token=29c46ebf-a107-45f8-9957-25b103108dd1")
                .into (img);

        //Recieve data
        Intent intent = getIntent ();
        String Title = intent.getExtras ().getString ("Title");
        double Note = intent.getExtras ().getDouble ("Note");
        String Description = intent.getExtras ().getString ("Description");
        String Ingredient = intent.getExtras ().getString ("Ingredient");
        String Preparation = intent.getExtras ().getString ("Preparation");

        //settings values
        //img.setImageResource(Image);
        tvtitle.setText (Title);
        note.setRating ((float) Note);
        tvdescription.setText (Description);
        tvingredient.setText (Ingredient);
        tvpreparation.setText (Preparation);
        addListenerOnRatingBar ();
    }

    public void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById (R.id.recipe_details_raiting_id);

        ratingBar.setOnRatingBarChangeListener (new RatingBar.OnRatingBarChangeListener () {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                addRating (rating);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate (R.menu.main_menu, menu);
        return super.onCreateOptionsMenu (menu);

    }

    private Task<Void> addRating(
            final float rating) {

        DocumentReference newRecipeRef = db.collection ("Recipes").document ("5py6doS79LqjKkDWzpSc");
        newRecipeRef.update("note", rating)
                .addOnSuccessListener(new OnSuccessListener < Void > () {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RecipeActivity.this, "Updated Successfully",
                                Toast.LENGTH_SHORT).show();
                    }
                });

        return null;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId () == R.id.list_recipes) {
            Intent listRecipes = new Intent (getApplicationContext (), GridViewActivity.class);
            startActivity (listRecipes);
            finish ();
        }

        if (item.getItemId () == R.id.add_recipe) {
            Intent addRecipe = new Intent (getApplicationContext (), AddRecipe.class);
            startActivity (addRecipe);
            finish ();
        }
        if (item.getItemId () == R.id.search_recipe) {
            Intent searchActivity = new Intent (getApplicationContext (), SearchActivity.class);
            startActivity (searchActivity);
            finish ();
        }
        if (item.getItemId () == R.id.user_settings) {
            Intent searchActivity = new Intent (getApplicationContext (), MainActivity.class);
            startActivity (searchActivity);
            finish ();
        }
        if (item.getItemId () == R.id.list_recipes) {
            Intent recipeActivity = new Intent (getApplicationContext (), GridViewActivity.class);
            startActivity (recipeActivity);
            finish ();
        }
        return super.onOptionsItemSelected (item);
    }
}
