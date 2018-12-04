package com.example.tweak.danabusbooking;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.tweak.danabusbooking.database_management.SQLiteHelper;
import com.example.tweak.danabusbooking.database_management.controllers.ghe_ngoi.GheNgoiController;
import com.example.tweak.danabusbooking.database_management.controllers.khach_hang.KhachHangController;
import com.example.tweak.danabusbooking.database_management.controllers.khach_hang_ve.KhachHangVeController;
import com.example.tweak.danabusbooking.database_management.controllers.tram_dung.TramDungController;
import com.example.tweak.danabusbooking.database_management.controllers.tuyen_duong.TuyenDuongController;
import com.example.tweak.danabusbooking.database_management.controllers.uu_dai.UuDaiController;
import com.example.tweak.danabusbooking.database_management.controllers.ve.VeController;
import com.example.tweak.danabusbooking.database_management.controllers.xe_buyt.XeBuytController;
import com.example.tweak.danabusbooking.database_management.controllers.xe_buyt_tram_dung.XeBuytTramDungController;

public class SplashActivity extends AppCompatActivity {

    // Define how many seconds we have to wait until the homa activity pops up
    private final int SPLASH_SCREEN_DELAY = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
              super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Database initialization
        SQLiteHelper.getContextReference(this);

        // Initializing all the data in Db for the first time
        initializingAllData();

        // Setting up animation
        setUpAnimationSplashScreen();

        // This is running by another thread
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Executed after timer is finished
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);

                startActivity(intent);

                // Kills this Activity
                finish();
            }
        }, SPLASH_SCREEN_DELAY);
    }

    private void initializingAllData() {
        UuDaiController.getInstance().getDataForTheFirstTime();
        KhachHangController.getInstance().getDataForTheFirstTime();
        TuyenDuongController.getInstance().getDataForTheFirstTime();
        XeBuytController.getInstance().getDataForTheFirstTime();
        TramDungController.getInstance().getDataForTheFirstTime();
        XeBuytTramDungController.getInstance().getDataForTheFirstTime();
        VeController.getInstance().getDataForTheFirstTime();
        GheNgoiController.getInstance().getDataForTheFirstTime();
        KhachHangVeController.getInstance().getDataForTheFirstTime();
    }

    private void setUpAnimationSplashScreen() {
        assignAnimationToView((ImageView) findViewById(R.id.imageSplashScreenAnimImageView), 1500,-600);
    }

    private void assignAnimationToView(ImageView imageView, long duration,float fromYPosition) {
        AnimationSet animationSet = new AnimationSet(true);

        Animation alphaAnimation = new AlphaAnimation(0, 1);
        Animation translateAnimation = new TranslateAnimation(0, 0, fromYPosition, 0);

        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(translateAnimation);

        animationSet.setDuration(duration);

        imageView.startAnimation(animationSet);
    }

}