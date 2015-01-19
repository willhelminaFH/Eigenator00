package com.bfh.willhelmina.eigenator00;

import android.content.Intent;

import java.util.ArrayList;

import processing.core.PImage;

/**
 * Created by Willhelmina on 1/12/2015.
 */
public class ProcSketch extends processing.core.PApplet{
    Intent intent;
    //something wrong with this... just not sure what
    ArrayList<String> imgUrls;
    PImage[] images;
    PImage displayImage;
    int frame = 0;
    public void setup(){
        size(displayWidth, displayHeight, P2D);
        frameRate(60);
        System.out.println("available memory: "+Runtime.getRuntime().freeMemory());
        intent = getIntent();
        imgUrls = intent.getStringArrayListExtra("com.bfh.willhelmina.eigenator00.imgurls");
        images = new PImage[8];//[imgUrls.size()];
        PImage img;
        PImage bg = new PImage(displayWidth, displayHeight);
        color(0);
        //Change to float
        float rW;
        float rH;
        float iW;
        float iH;
        //int i = 0;
        for (int i = 0; i<8;i++){
        //for(String s: imgUrls){
            String s = imgUrls.get(i);
            img = loadImage(s);
            //bg = new PImage(displayWidth, displayHeight);
            iW = img.width;
            iH = img.height;
            rW = displayWidth/iW;
            rH = displayHeight/iH;
            System.out.println("available memory: "+Runtime.getRuntime().freeMemory());
            /*
            System.out.println("iW: "+iW+" || iH: "+iH+ " || rW: "+ rW+ " || rH: "+rH+
                               " || dW: "+displayWidth+ " || dH: "+ displayHeight);

            if(rH>rW){
                //img.resize(rH*iW, rH*iH);
                //System.out.println("rH*iW:"+ rH*iW+" rH*iH:"+ rH*iH);
                //System.out.println("rW*iW:"+ rW*iW+" rW*iH:"+ rW*iH);
                bg.copy(img, 0,0,(int)iW, (int)iH, 0,0, (int)(rW*iW), (int)(rW*iH));
            }else{
                //img.resize(rW*iW, rW*iH);
                bg.copy(img, 0,0,(int)iW, (int)iH, 0,0, (int)(rH*iW), (int)(rH*iH));
            }*/

            //iW = img.width;
            //iH = img.height;
            //bg.blend(img, 0,0,iW, iH, 0,0, iW, iH, ADD);
            //bg.copy(img, 0,0,iW, iH, 0,0, iW, iH);
            images[i] = img;//bg;
            System.out.println("loading image: " +i);

            //i++;
        }
    }
    public void draw(){

        background(0);
        displayImage = images[frameCount%7];
        float rW;
        float rH;
        float iW;
        float iH;
        iW = displayImage.width;
        iH = displayImage.height;
        rW = displayWidth/iW;
        rH = displayHeight/iH;

        if(rH>rW){
            displayImage.resize((int)(rW*iW), (int)(rW*iH));
        }else{
            displayImage.resize((int)(rH*iW), (int)(rH*iH));
        }

        //displayImage.loadPixels();
        image(displayImage,0,0);
        color(255);
        textSize(18);
        String txt = "frame#:"+frame+ "rate:"+frameRate;
        text(txt, 24, displayHeight - 32);

    }
}
