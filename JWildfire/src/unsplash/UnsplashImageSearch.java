package unsplash;

import com.sangupta.jerry.http.service.impl.DefaultHttpServiceImpl;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class UnsplashImageSearch {

    public static void imageSearch(String searchString) throws IOException {
        UnsplashClient client = new UnsplashClient("xfO7UuG2iXVONso6WQPp69Giq0YtQ3sNoSU8RvLBSN8");
        client.setHttpService(new DefaultHttpServiceImpl());

        UnsplashQueryImage images = client.getPhotos(searchString);
        UnsplashImage[] array =images.getUnsplashImageArray();

        for(int imgCount=0; imgCount<5 ; imgCount++){
            UnsplashImage loopImage = array[imgCount];
            if(loopImage != null){
                URL url = new URL(loopImage.getImageUrl());
                InputStream in = new BufferedInputStream(url.openStream());
                OutputStream out = new BufferedOutputStream(new FileOutputStream("D:\\JWildFireFiles\\"+imgCount+".jpg"));

                for ( int i; (i = in.read()) != -1; ) {
                    out.write(i);
                }
                in.close();
                out.close();
            }
        }
        System.out.println("Done");
    }
}
