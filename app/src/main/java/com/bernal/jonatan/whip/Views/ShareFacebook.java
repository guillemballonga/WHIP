package com.bernal.jonatan.whip.Views;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bernal.jonatan.whip.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.common.internal.service.Common;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Callback;

import java.net.URI;
import java.util.jar.JarFile;

public class ShareFacebook extends AppCompatActivity {

    private static final int REQUEST_VIDEO_CODE = 1000;
    Button btnShareLink, btnSharePhoto, btnShareVideo;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

            SharePhoto sharePhoto = new SharePhoto.Builder().setBitmap(bitmap).build();
            if(ShareDialog.canShow(SharePhotoContent.class)) {
                SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(sharePhoto).build();
                shareDialog.show(content);
            }

        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }



        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_share_facebook);

        //init view
        btnShareLink = (Button) findViewById(R.id.btnShareLink);
        btnSharePhoto = (Button) findViewById(R.id.btnSharePhoto);
        btnShareVideo = (Button) findViewById(R.id.btnShareVideo);

        //init FB

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);


        btnShareLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(ShareFacebook.this, "ShareFacebooklink correcta", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(ShareFacebook.this, "ShareFacebooklink CANCEL", Toast.LENGTH_SHORT);

                    }

                    @Override
                    public void onError(FacebookException error) {

                        Toast.makeText(ShareFacebook.this, "ShareFacebooklink ERROR: " + error.getMessage(), Toast.LENGTH_SHORT);
                    }
                });


                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle("hola soc el titulin")
                        .setQuote("this is useful link")
                        .setContentUrl(Uri.parse("https://youtube.com"))
                        .build();

                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    shareDialog.show(linkContent);
                }
            }
        });

        btnSharePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create callback

                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(ShareFacebook.this, "ShareFacebookfoto correcta", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(ShareFacebook.this, "ShareFacebookfoto CANCEL", Toast.LENGTH_SHORT);

                    }

                    @Override
                    public void onError(FacebookException error) {

                        Toast.makeText(ShareFacebook.this, "ShareFacebookfoto ERROR: " + error.getMessage(), Toast.LENGTH_SHORT);
                    }
                });

                //we will fetch photo from link and convert to bitmap

                //Picasso.with(getBaseContext()).load("path").into(target);




            }
        });

        btnShareVideo.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {



                //choose video dialog
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "select video"), REQUEST_VIDEO_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_VIDEO_CODE) {
                Uri selectedVideo = data.getData();

                ShareVideo video = new ShareVideo.Builder().setLocalUrl(selectedVideo).build();

                ShareVideoContent videoContent = new ShareVideoContent.Builder()
                        .setContentTitle("this is useful video")
                        .setContentDescription("holi manoli")
                        .setVideo(video)
                        .build();

                if (shareDialog.canShow(ShareVideoContent.class))
                    shareDialog.show(videoContent);
            }
        }
    }

    public void publicarPost(String idImatge ) {

        Bitmap bitmap = ShowImage.retrieveImageBitmap("");
    }
}
