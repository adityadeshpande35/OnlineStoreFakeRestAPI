package pojo;

public class Geolocation {
    private  String lat;
    private  String lng;

    public Geolocation(String lat, String lng)
    {
        this.lat=lat;
        this.lng=lng;
    }

    public String getLatitude()
    {
        return lat;
    }

    public void setLatitude(String firstname)
    {
        this.lat=lat;
    }
    public String getLongitude()
    {
        return lng;
    }

    public void setLongitude(String firstname)
    {
        this.lng=lng;
    }




}
