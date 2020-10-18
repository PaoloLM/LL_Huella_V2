package com.desarrollo.ll_huella;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.airsaid.imagecomparator.AverageHashComparison;
import com.airsaid.imagecomparator.ImageComparator;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //IMAGEN
    private static final int GALLERY_INTENT = 1;
    Uri uriimagen;

    private int VerificarImagen = 0;

    ImageButton registroHuella, registroHuella2;
    Field[] fields;
    List<Integer> drawables;
    ProgressDialog progress;

    float max = 0;
    int indice = 0;
    String nombreUsuario = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registroHuella2 = findViewById(R.id.registroHuella2);
        registroHuella = findViewById(R.id.registroHuella);
        registroHuella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });

        findViewById(R.id.loginIngresar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (VerificarImagen == 1) {
                    new TareaCompararImagenes().execute();
                } else {
                    Toast.makeText(getApplicationContext(), "Falta datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {

            uriimagen = data.getData();

            Glide.with(MainActivity.this)
                    .load(uriimagen)
                    .fitCenter()
                    .centerCrop()
                    .into(registroHuella);
            VerificarImagen = 1;
        }
    }

    public void ExtraerIndicadores() {
        fields = R.drawable.class.getFields();
        drawables = new ArrayList<Integer>();
        for (Field field : fields) {
            if (field.getName().endsWith("_1")) {
                try {
                    drawables.add(field.getInt(null));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void CompararImagenes() {
        Context context = getApplicationContext();
        Drawable drawable_original = registroHuella.getDrawable();

        //Obteniendo Bitmap
        Bitmap bitmap_drawable_original = ((BitmapDrawable)drawable_original).getBitmap();

        //Parametros para la comparacion
        AverageHashComparison mAHashComparison = new AverageHashComparison();
        ImageComparator mImageComparator = new ImageComparator(mAHashComparison);

        max = 0;
        indice = 0;

        for (int i = 0; i < drawables.size(); i++) {
            //TODO: OBTENER DRAWABLE Y BITMAP DE IMAGENES GUARDADAS
            Drawable drawable_comparar = context.getResources().getDrawable(drawables.get(i));
            Bitmap bitmap_drawable_comparar = ((BitmapDrawable)drawable_comparar).getBitmap();

            //Comparando
            float result = mImageComparator.comparison(bitmap_drawable_original, bitmap_drawable_comparar);
            if (result >= max) {
                max = result;
                indice = drawables.get(i);
            } else {
                // no hace nada
            }
        }

        Log.e("Comparacion promedio", max + " imagen:" + indice );


    }

    class TareaCompararImagenes extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            Log.e("POSTEXECUTE", "onPreExecute: Inicio");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ExtraerIndicadores();
            CompararImagenes();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.e("POSTEXECUTE", "onPostExecute: Termino");
            Glide.with(MainActivity.this)
                    .load(indice)
                    .fitCenter()
                    .centerCrop()
                    .into(registroHuella2);
        }
    }
}