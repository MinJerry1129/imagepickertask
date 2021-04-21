package com.mobiledevteam.imagepickertask;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Environment;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SliderLayout sliderLayout;
    private List<String> imageUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sliderLayout = findViewById(R.id.imageSlider);
        imageUrls=new ArrayList<>();
//        setSliderViews_main();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPickImage();
            }
        });
    }
    public void onPickImage() {
        ImagePicker.Companion.with(this).cameraOnly().saveDir(Environment.getExternalStorageDirectory()).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK){
            Uri fileUri = data.getData();
//            mSelImageStatus = "yes";
//            _txtSelImage.setVisibility(View.GONE);
//            _imgClinic.setImageURI(fileUri);
//            File file = ImagePicker.Companion.getFile(data);
            String filePath = ImagePicker.Companion.getFilePath(data);
            imageUrls.add(filePath);
            setSliderViews();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void setSliderViews() {
        this.runOnUiThread(new Runnable() {
            public void run() {
                sliderLayout.removeAllSliders();
//        sliderLayout.removeAllViews();
                for (String theImageUrl: imageUrls) {
//            TextSliderView textSliderView = new TextSliderView(this);
//            textSliderView.image(theImageUrl).setScaleType(BaseSliderView.ScaleType.CenterCrop);
//            sliderLayout.addSlider(textSliderView);
                    File imgFile = new File(theImageUrl);
                    DefaultSliderView sliderView = new DefaultSliderView(getBaseContext());
                    sliderView.image(imgFile).setScaleType(BaseSliderView.ScaleType.CenterCrop);
                    sliderLayout.addSlider(sliderView);
                    Log.d("Filepath:", theImageUrl);
                }
            }
        });
    }
    public void setSliderViews_main() {
        for (int i=0;i<5;i++) {
            String theImageUrl="https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png";
//            imageUrls.add(theImageUrl);
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView.image(theImageUrl).setScaleType(BaseSliderView.ScaleType.CenterCrop);
            sliderLayout.addSlider(textSliderView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}