package com.desarrollo.ll_huella;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class ImageActivity extends AppCompatActivity {

    CarouselView carouselView;
    /*int[] sampleImages = {R.drawable.abraham_caba_vargas_1
            , R.drawable.belisa_ramos_alvarez_1
            , R.drawable.carola_marchani_justo_1
            , R.drawable.danet_delgado_castillo_1
            , R.drawable.eddie_carpio_obando_1};*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);
        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;
        getWindow().setLayout((int)(ancho * 0.60), (int)(alto * 0.40));


        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(MainActivity.posibilidades.size());
        carouselView.setImageListener(imageListener);
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(MainActivity.posibilidades.get(position).getIndice());
        }
    };
}