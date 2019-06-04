package com.bernal.jonatan.whip.Views;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class ShareFacebook extends AppCompatActivity {

    private static final int REQUEST_VIDEO_CODE = 1000;
    private static final int REQUEST_IMAGE_CODE = 1500;
    Button btnShareLink, btnSharePhoto, btnShareVideo, btnSharePost;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    /*

    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

            bitmap = ShowImage.retrieveImageBitmap(idImatge);

            SharePhoto sharePhoto = new SharePhoto.Builder().setBitmap(bitmap).build();
            if (ShareDialog.canShow(SharePhotoContent.class)) {
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
    */
    private String titlePost = "", descriPost = "";
    private String idImatge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_share_facebook);

        //init view
        btnShareLink = (Button) findViewById(R.id.btnShareLink);
        btnSharePhoto = (Button) findViewById(R.id.btnSharePhoto);
        btnShareVideo = (Button) findViewById(R.id.btnShareVideo);
        btnSharePost = (Button) findViewById(R.id.btnSharePost);

        //init FB

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);


        titlePost = getIntent().getStringExtra("titlePost");
        if (titlePost == null) titlePost = "";

        descriPost = getIntent().getStringExtra("descriptionPost");
        if (descriPost == null) descriPost = "";

        idImatge = getIntent().getStringExtra("urlImage");
        if (idImatge == null) idImatge = "";


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

                /*
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

                */


                @SuppressLint("IntentReset") Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                gallery.setType("image/");
                startActivityForResult(gallery.createChooser(gallery, "select PHOTO"), REQUEST_IMAGE_CODE);




            }
        });

        btnSharePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create callback
                System.out.println("entro a post");

                Bitmap image = retrieveImage(idImatge);


                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(image)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();





            }
        });

        btnShareVideo.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {


                //choose video dialog
                Intent intent = new Intent();
                intent.setType("image/*");
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
            else if (requestCode == REQUEST_IMAGE_CODE) {
                Uri selectedImage = data.getData();

                SharePhoto photo = new SharePhoto.Builder().setImageUrl(selectedImage).build();

                SharePhotoContent photoContent = new SharePhotoContent.Builder().addPhoto(photo).build();


                if (shareDialog.canShow(SharePhotoContent.class))
                    shareDialog.show(photoContent);
            }
        }
    }

    @SuppressLint("IntentReset")
    public void openGallery() {
        @SuppressLint("IntentReset") Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        gallery.setType("image/");
        startActivityForResult(gallery.createChooser(gallery, "Seleccione la Aplicación"), 10);
    }



    public Bitmap retrieveImage(String idImageFirebase) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        //TODO: necessito recuperar l objecte desde el json. a child posarhi l indetificador guardat
        StorageReference storageReference = storage.getReferenceFromUrl("gs://whip-1553341713756.appspot.com/").child(idImageFirebase);



        String urlPhoto =  idImageFirebase ;


        storage.getReferenceFromUrl("gs://whip-1553341713756.appspot.com/").child(idImageFirebase).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                System.out.println("url download imatge: " + uri);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

                System.out.println("url download imatge: ERROR");
                // Handle any errors
            }
        });

        return null;
    }
}
