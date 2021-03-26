package unsplash;

public class UnsplashQueryImage {

    public int total;

    public int total_pages;

    public UnsplashImage[] results;

    public UnsplashImage[] getUnsplashImageArray(){
        if(this.results == null){
            return null;
        }
        return this.results;
    }
}
