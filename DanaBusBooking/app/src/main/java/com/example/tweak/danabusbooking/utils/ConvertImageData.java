package com.example.tweak.danabusbooking.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class ConvertImageData {

    // Convert the input image to an array of bytes to be able to be saved in DB
    public static byte[] imageViewToBytes(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);

        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }

    // Convert array of bytes to bitmap to assign the output image to the view
    public static Bitmap bytesToBitmap(byte[] avatarImage) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(avatarImage, 0, avatarImage.length);

        return bitmap;
    }

}
