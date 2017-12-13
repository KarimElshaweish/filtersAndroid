package com.example.karim.hanor3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    Button  button;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner=(Spinner)findViewById(R.id.spinner);
        button =(Button)findViewById(R.id.button);
        imageView =(ImageView)findViewById(R.id.imageView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap imgToFilter=BitmapFactory.decodeResource(getResources(), R.drawable.slide1);
                filters filters=new filters();
                switch (spinner.getSelectedItemPosition()){
                    case 1:
                        imageView.setImageResource(R.drawable.slide1);
                        break;
                    case 2:
                        imageView.setImageBitmap(filters.doHighlight(imgToFilter));
                        break;
                    case 3:
                        imageView.setImageBitmap(filters.doInvert(imgToFilter));
                        break;
                    case 4:
                        imageView.setImageBitmap(filters.doGrayScal(imgToFilter));
                        break;
                    case 5:
                        imageView.setImageBitmap(filters.doGomma(imgToFilter,50,10,30));
                        break;
                    case 6:
                        imageView.setImageBitmap(filters.doColorFilter(imgToFilter,10,20,50));
                        break;
                    case 7:
                        imageView.setImageBitmap(filters.doContrast(imgToFilter));
                        break;
                    case 8:
                        imageView.setImageBitmap(filters.tintImage(imgToFilter));
                        break;
                    case 9:
                        imageView.setImageBitmap(filters.boost(imgToFilter,1,50));
                        break;
                }
            }
        });

    }
}
