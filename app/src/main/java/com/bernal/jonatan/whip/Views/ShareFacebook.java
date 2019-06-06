package com.bernal.jonatan.whip.Views;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Collections;

public class ShareFacebook extends AppCompatActivity {

    private static final int REQUEST_VIDEO_CODE = 1000;
    private static final int REQUEST_IMAGE_CODE = 1500;
    Button btnShareLink, btnSharePhoto, btnShareVideo, btnSharePost;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    private String titlePost = "", descriPost = "";
    private String idImatge = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_share_facebook);

        //init view
        btnShareLink = findViewById(R.id.btnShareLink);
        btnSharePhoto = findViewById(R.id.btnSharePhoto);
        btnShareVideo = findViewById(R.id.btnShareVideo);
        btnSharePost = findViewById(R.id.btnSharePost);

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
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException error) {
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
                //choose video dialog
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "select image"), REQUEST_IMAGE_CODE);
            }
        });

        btnSharePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create callback

                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        System.out.println("share succesful");
                    }

                    @Override
                    public void onCancel() {

                        System.out.println("share cancel");
                    }

                    @Override
                    public void onError(FacebookException error) {

                        System.out.println("share error");
                    }
                });


                System.out.println("entro a post");


                signInFirebase();

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
            } else if (requestCode == REQUEST_IMAGE_CODE) {
                Uri selectedImage = data.getData();
                SharePhoto photo = new SharePhoto.Builder().setImageUrl(selectedImage).build();
                SharePhotoContent photoContent = new SharePhotoContent.Builder().setPhotos(Collections.singletonList(photo)).build();
                if (shareDialog.canShow(SharePhotoContent.class))
                    shareDialog.show(photoContent);
            }
        }
    }


    public void sharePostWithoutImage() {

        try {
            System.out.println("dins PICASSO: ");
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("POST WHIP: " + titlePost)
                    .setContentUrl(Uri.parse("www.whip.com"))
                    .setQuote(descriPost)
                    .build();
            if (ShareDialog.canShow(ShareLinkContent.class)) {
                shareDialog.show(linkContent);
            }
        } catch (Exception e) {
            System.out.println("ERROR PICASSO: " + e.getMessage());
        }

    }

    public void retrieveImage(String idImageFirebase) {


        FirebaseStorage storage = FirebaseStorage.getInstance();

        storage.getReferenceFromUrl("gs://whip-1553341713756.appspot.com/").child(idImageFirebase).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                System.out.println("url download imatge: " + uri);
                System.out.println("url download imatge: " + uri.toString());

                try {
                    System.out.println("dins PICASSO: ");
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("POST WHIP: " + titlePost)
                            .setQuote(descriPost)
                            .setContentUrl(uri)
                            .build();

                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        shareDialog.show(linkContent);
                    }
                } catch (Exception e) {
                    System.out.println("ERROR PICASSO: " + e.getMessage());
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                System.out.println("url download imatge: ERROR");
            }
        });


    }

    private void signInFirebase() {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            if (!idImatge.equals(""))
                retrieveImage(idImatge);
            else {
                sharePostWithoutImage();
            }
        } else {
            signInAnonymously(mAuth);
        }
    }

    private void signInAnonymously(FirebaseAuth mAuth) {

        mAuth.signInAnonymously().addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                System.out.println("signInAnonymously: onSuccess");
                if (!idImatge.equals("")) retrieveImage(idImatge);

            }
        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e("signin ani: ", "signInAnonymously:FAILURE", exception);
                    }
                });
    }
}

