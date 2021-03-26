package unsplash;

import java.util.List;

public class UnsplashImage {

    public String id;

    public String createdAt;

    public String updatedAt;

    public int width;

    public int height;

    public String color;

    public int downloads;

    public int likes;

    public int views;

    public boolean likedByUser;

    public String description;

    public UnsplashUser user;

    public UnsplashUrls urls;

    public List<UnsplashCategory> categories;

    public UnsplashLinks links;

    public UnsplashExif exif;

    public UnsplashLocation location;

    public String getImageUrl() {
        if(this.urls == null) {
            return null;
        }

        return this.urls.raw;
    }

    public String getDescription(){
        if(this.description == null){
            return  null;
        }

        return this.description;
    }

    @Override
    public int hashCode() {
        if(this.id == null) {
            return 0;
        }

        return this.id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        if(this == obj) {
            return true;
        }

        if(this.id == null) {
            return false;
        }

        if(obj instanceof UnsplashImage) {
            UnsplashImage other = (UnsplashImage) obj;

            return this.id.equals(other.id);
        }

        if(obj instanceof String) {
            return this.id.equals(obj);
        }

        return false;
    }

    @Override
    public String toString() {
        return "UnsplashImage: " + this.getImageUrl();
    }

}

