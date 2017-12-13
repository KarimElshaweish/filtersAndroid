package com.example.karim.hanor3;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;

/**
 * Created by karim on 12/13/17.
 */

public class filters {
        public  static Bitmap doHighlight(Bitmap src){
            Bitmap bmOut=Bitmap.createBitmap(src.getWidth()+96,src.getHeight()+96,Bitmap.Config.ARGB_8888);
            Canvas can=new Canvas(bmOut);
            can.drawColor(0, PorterDuff.Mode.CLEAR);
            Paint ptBlur=new Paint();
            ptBlur.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.NORMAL));
            int []offsetX=new int[2];
            Bitmap bmAlpha=src.extractAlpha(ptBlur,offsetX);
            Paint ptAlphaColor=new Paint();
            ptAlphaColor.setColor(0xffffff);
            can.drawBitmap(bmAlpha,offsetX[0],offsetX[1],ptAlphaColor);
            bmAlpha.recycle();
            can.drawBitmap(src,0,0,null);
            return bmOut;
        }
        public  static  Bitmap doInvert(Bitmap src){
            Bitmap bmOut=Bitmap.createBitmap(src.getWidth()+96,src.getHeight(),Bitmap.Config.ARGB_8888);
            int A,R,G,B;
            int pixelColor;
            int heigh=src.getHeight();
            int width=src.getWidth();
            for (int y=0;y<heigh;y++){
                for (int x=0;x<width;x++){
                    pixelColor=src.getPixel(x,y);
                    A= Color.alpha(pixelColor);
                    R=255-Color.red(pixelColor);
                    G=255-Color.green(pixelColor);
                    B=255-Color.blue(pixelColor);
                    bmOut.setPixel(x,y,Color.argb(A,R,G,B));
                }
            }
            return  bmOut;
        }
        public  static  Bitmap doGrayScal(Bitmap src){
            final double GS_RED = 0.299;
            final double GS_GREEN = 0.587;
            final double GS_BLUE = 0.114;
            Bitmap bmOut=Bitmap.createBitmap(src.getWidth()+96,src.getHeight(),Bitmap.Config.ARGB_8888);
            int A,R,G,B;
            int pixel;
            int width=src.getWidth();
            int height=src.getHeight();
            for(int x=0;x<width;++x){
                for(int y=0;y<height;++y){
                    pixel=src.getPixel(x,y);
                    A=Color.alpha(pixel);
                    R=Color.red(pixel);
                    G=Color.green(pixel);
                    B=Color.blue(pixel);
                    R=G=B=(int)(GS_RED*R+GS_GREEN*G+GS_BLUE*B);
                    bmOut.setPixel(x,y,Color.argb(A,R,G,B));
                }

            }
            return  bmOut;
        }
        public  static  Bitmap doGomma(Bitmap src,double red,double green,double blue){
            //Red = 0.6, Green = 0.6, Blue = 0.6
            //or
            //Red = 1.8, Green = 1.8, Blue = 1.8

            Bitmap bmOut=Bitmap.createBitmap(src.getWidth(),src.getHeight(), src.getConfig());
            int width=src.getWidth();
            int hieght=src.getHeight();
            int A,R,G,B;
            int pixel;
            // constant value curve
            final int    MAX_SIZE = 256;
            final double MAX_VALUE_DBL = 255.0;
            final int    MAX_VALUE_INT = 255;
            final double REVERSE = 1.0;
            // gamma arrays
            int[] gammaR = new int[MAX_SIZE];
            int[] gammaG = new int[MAX_SIZE];
            int[] gammaB = new int[MAX_SIZE];
            for(int i=0;i<MAX_SIZE;++i){
                gammaR[i]=(int)Math.min(MAX_VALUE_INT,
                        (int)MAX_VALUE_DBL*Math.pow(i/MAX_VALUE_DBL,REVERSE/red));
                gammaG[i]=(int)Math.min(MAX_VALUE_INT,
                        (int)MAX_VALUE_DBL*Math.pow(i/MAX_VALUE_DBL,REVERSE/green));
                gammaB[i]=(int)Math.min(MAX_VALUE_INT,
                        (int)MAX_VALUE_DBL*Math.pow(i/MAX_VALUE_DBL,REVERSE/blue));
            }
            for(int x=0;x<width;++x){
                for(int y=0;y<hieght;++y){
                    pixel=src.getPixel(x,y);
                    A=Color.alpha(pixel);
                    R=Color.red(pixel);
                    G=Color.green(pixel);
                    B=Color.blue(pixel);
                    bmOut.setPixel(x,y,Color.argb(A,R,G,B));
                }
            }
            return bmOut;
        }
        public  static  Bitmap doColorFilter(Bitmap src,double red,double green,double blue){
            //0,1,1
            //0,0,1
            //0,1,0
            //1,0,0
            //.5,.5,.5
            //1.5,1.5,1.5
            int width=src.getWidth();
            int height=src.getHeight();
            Bitmap bmOut=Bitmap.createBitmap(width,height,src.getConfig());
            int A,R,G,B;
            int pixel;
            for(int x=0;x<width;++x){
                for(int y=0;y<height;++y){
                    pixel=src.getPixel(x,y);
                    A=Color.alpha(pixel);
                    R=(int)(Color.red(pixel)*red);
                    B=(int)(Color.blue(pixel)*blue);
                    G=(int)(Color.green(pixel)*green);
                    bmOut.setPixel(x,y,Color.argb(A,R,G,B));
                }
            }
            return  bmOut;
        }
        public  static  Bitmap doContrast(Bitmap src){
            double Value=50;
            int width=src.getWidth();
            int height=src.getHeight();
            Bitmap bmOut=Bitmap.createBitmap(width,height,src.getConfig());
            int A,R,G,B;
            int pixel;
            double contrast=Math.pow((100+Value)/100,2);
            for(int x=0;x<width;++x){
                for(int y=0;y<height;++y){
                    pixel=src.getPixel(x,y);
                    A=Color.alpha(pixel);
                    R=Color.red(pixel);
                    R=(int)(((((R/255.0)-.5)*contrast)+.5)*255.0);
                    if(R<0)R=0;
                    else if(R>255)R=255;
                    G=Color.green(pixel);
                    G=(int)(((((G/255.0)-.5)*contrast)+.5)*255.0);
                    if(G<0)G=0;
                    else if(G>255)G=255;
                    B=Color.blue(pixel);
                    B=(int)(((((B/255.0)-.5)*contrast)+.5)*255.0);
                    if(B<0)B=0;
                    else if(B>255)B=255;
                    bmOut.setPixel(x,y,Color.argb(A,R,G,B));

                }
            }
            return  bmOut;
        }
        public  static  Bitmap tintImage(Bitmap src){
            int degree=35;
            double PI = 3.14159d;
            double FULL_CIRCLE_DEGREE = 360d;
            double HALF_CIRCLE_DEGREE = 180d;
            double RANGE = 256d;
            int width= src.getWidth();
            int height= src.getHeight();
            int []pix=new int[width*height];
            src.getPixels(pix,0,width,0,0,width,height);
            int RY, GY, BY, RYY, GYY, BYY, R, G, B, Y;
            double angel=(PI*(double)degree)/HALF_CIRCLE_DEGREE;
            int s=(int)(RANGE*Math.sin(angel));
            int c=(int)(RANGE*Math.cos(angel));
            for(int y=0;y<height;++y){
                for(int x=0;x<width;++x){
                    int index=y*width+x;
                    int r=(pix[index]>>16)&0xff;
                    int g = ( pix[index] >> 8 ) & 0xff;
                    int b = pix[index] & 0xff;
                    RY = ( 70 * r - 59 * g - 11 * b ) / 100;
                    GY = (-30 * r + 41 * g - 11 * b ) / 100;
                    BY = (-30 * r - 59 * g + 89 * b ) / 100;
                    Y  = ( 30 * r + 59 * g + 11 * b ) / 100;
                    RYY = ( s * BY + c * RY ) / 256;
                    BYY = ( c * BY - s * RY ) / 256;
                    GYY = (-51 * RYY - 19 * BYY ) / 100;
                    R = Y + RYY;
                    R = ( R < 0 ) ? 0 : (( R > 255 ) ? 255 : R );
                    G = Y + GYY;
                    G = ( G < 0 ) ? 0 : (( G > 255 ) ? 255 : G );
                    B = Y + BYY;
                    B = ( B < 0 ) ? 0 : (( B > 255 ) ? 255 : B );
                    pix[index] = 0xff000000 | (R << 16) | (G << 8 ) | B;
                }
            }
            Bitmap bmOut=Bitmap.createBitmap(width,height,src.getConfig());
            bmOut.setPixels(pix,0,width,0,0,width,height);
            pix=null;
            return bmOut;
        }
        public  static  Bitmap boost(Bitmap src,int type,float precent){
            //type=1 150%
            //type=2 50%
            //type=3 67%
            int width=src.getWidth();
            int height=src.getHeight();
            Bitmap bmOut=Bitmap.createBitmap(width,height,src.getConfig());
            int A,R,G,B;
            int pixel;
            for(int x=0;x<width;++x)
                for(int y=0;y<height;++y){
                    pixel=src.getPixel(x,y);
                    A=Color.alpha(pixel);
                    R=Color.red(pixel);
                    G=Color.green(pixel);
                    B=Color.blue(pixel);
                    if(type==1){
                        R=(int)(R*(1+precent));
                        if(R>255)R=255;
                    }
                    else if(type==2){
                        G=(int)(G*(1+precent));
                        if(G>255)G=255;
                    }
                    else if(type==3){
                        B=(int)(B*(1+precent));
                        if(B>255)B=225;
                    }
                    bmOut.setPixel(x,y,Color.argb(A,R,G,B));
                }
            return bmOut;
        }

}
