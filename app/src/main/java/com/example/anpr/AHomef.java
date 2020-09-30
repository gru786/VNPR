package com.example.anpr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AHomef extends Fragment
{
    ViewFlipper viewFlipper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_homef,null,false);
        int images[] = {R.drawable.s1,R.drawable.s2,R.drawable.s3};
        viewFlipper = view.findViewById(R.id.v_flipper);

        for(int i=0;i<images.length;i++)
        {
            flipperImages(images[i]);
        }
        return view;
    }
    public void flipperImages(int image)
    {
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);

        //animation
        viewFlipper.setInAnimation(getContext(),android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(getContext(),android.R.anim.slide_out_right);


    }
}
