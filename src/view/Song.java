//Bola Henine
//Roshan Seth


package view;

public class Song {
	 private String name;
     private String artist;
     private String album;
     private String year;
	public Song(String name, String artist, String album, String year)
    {
         setName(name);
         setArtist(artist);
         setAlbum(album);
         setYear(year);     
    }
 
    public String getName()
    {
         return name;
    }
    public void setName(String name)
    {
         this.name = name;
    }
    public String getArtist()
    {
         return artist;
    }
    public void setArtist(String artist)
    {
         this.artist = artist;
    }
    public String getAlbum()
    {
         return album;
    }
    public void setAlbum(String album)
    {
         this.album = album;
    }
    public String getYear()
    {
         return year;
    }
    public void setYear(String year)
    {
         this.year = year;
    }
    public String toString()
    {
         String result = String.format("%15s \t %15s \t %30s \t %10s",name, artist, album, year);
         return result;
    }
}