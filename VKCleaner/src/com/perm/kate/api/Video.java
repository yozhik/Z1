package com.perm.kate.api;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class Video implements Serializable{
    private static final long serialVersionUID = 1L;
    public long vid;
    public long owner_id;
    public String title;
    public String description;
    public long duration;
    public String link;
    public String image;//130*120
    public String image_big;//320*240
    public long date;
    public String player;
    //files
    public String external;
    public String mp4_240;
    public String mp4_360;
    public String mp4_480;
    public String mp4_720;
    public String flv_320;
    
    
    public static Video parse(JSONObject o) throws NumberFormatException, JSONException{
        Video v = new Video();
        if(o.has("vid"))
            v.vid = o.getLong("vid");
        if(o.has("id"))//video.getUserVideos
            v.vid = Long.parseLong(o.getString("id"));
        v.owner_id = o.getLong("owner_id");
        v.title = Api.unescape(o.getString("title"));
        v.duration = o.getLong("duration");
        v.description = Api.unescape(o.optString("description"));
        if(o.has("image"))
            v.image = o.optString("image");
        v.image_big = o.optString("image_medium");
        if(o.has("thumb"))//video.getUserVideos
            v.image = o.optString("thumb");
        v.link = o.optString("link");
        v.date = o.optLong("date");
        v.player = o.optString("player");
        
        JSONObject files=o.optJSONObject("files");
        if(files!=null){
            v.external = files.optString("external");
            v.mp4_240 = files.optString("mp4_240");
            v.mp4_360 = files.optString("mp4_360");
            v.mp4_480 = files.optString("mp4_480");
            v.mp4_720 = files.optString("mp4_720");
            v.flv_320 = files.optString("flv_320");
        }
        return v;
    }
    
    public static Video parseForAttachments(JSONObject o) throws NumberFormatException, JSONException{
        Video v = new Video();
        if(o.has("vid"))
            v.vid = o.getLong("vid");
        if(o.has("id"))//video.getUserVideos
            v.vid = Long.parseLong(o.getString("id"));
        v.owner_id = o.getLong("owner_id");
        v.title = Api.unescape(o.getString("title"));
        v.duration = o.getLong("duration");
        v.description = Api.unescape(o.optString("description"));
        if(o.has("image"))
            v.image = o.optString("image");
        v.image_big = o.optString("image_big");
        if(o.has("thumb"))//video.getUserVideos
            v.image = o.optString("thumb");
        v.link = o.optString("link");
        v.date = o.optLong("date");
        v.player = o.optString("player");
        return v;
    }
    
    public String getVideoUrl() {
        return getVideoUrl(owner_id, link);
    }
    
    public static String getVideoUrl(long owner_id, String link) {
        String res = null;
        String base_url = "http://vk.com/";
        res = base_url + "video" + String.valueOf(owner_id) + link.replace("video", "_");
        //sample http://vkontakte.ru/video4491835_158963813
        //http://79.gt2.vkadre.ru/assets/videos/f6b1af1e4258-24411750.vk.flv
        return res;
    }
}