package com.perm.kate.api;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//Fields are optional. Should be null if not populated
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    public long uid;
    public String first_name;
    public String last_name;
    public String nickname;
    public Integer sex=null;
    public Boolean online=null;
    public Boolean online_mobile=null;
    public String birthdate; //bdate
    public String photo;//the same as photo_rec
    public String photo_big;
    public String photo_medium;
    public String photo_medium_rec;
    public Integer city=null;
    public Integer country=null;
    public Integer timezone=null;
    public String lists;
    public String domain;
    public Integer rate=null;
    public Integer university=null; //if education 
    public String university_name; //if education
    public Integer faculty=null; //if education
    public String faculty_name; //if education
    public Integer graduation=null; //if education
    public Boolean has_mobile=null;
    public String home_phone;
    public String mobile_phone;
    public String status;
    public Integer relation;
    public String friends_list_ids = null;
    public long last_seen;
    public int albums_count;
    public int videos_count;
    public int audios_count;
    public int notes_count;
    public int friends_count;
    public int user_photos_count;
    public int user_videos_count;
    //public int followers_count;
    //public int subscriptions_count;
    //public int online_friends_count;
    public String phone;//for getByPhones
    public int groups_count;
    //relation_partner
    public Long relation_partner_id;
    public String relation_partner_first_name;
    public String relation_partner_last_name;
    
    public static User parse(JSONObject o) throws JSONException {
        User u = new User();
        u.uid = Long.parseLong(o.getString("uid"));
        if(!o.isNull("first_name"))
            u.first_name = Api.unescape(o.getString("first_name"));
        if(!o.isNull("last_name"))
            u.last_name = Api.unescape(o.getString("last_name"));
        if(!o.isNull("nickname"))
            u.nickname = Api.unescape(o.optString("nickname"));
        if(!o.isNull("domain"))
            u.domain = o.optString("domain");
        if(!o.isNull("online"))
            u.online = o.optInt("online")==1;
        if(!o.isNull("online_mobile"))
            u.online_mobile = o.optInt("online_mobile")==1;
        else
            //if it's not there it means false
            u.online_mobile=false;
        if(!o.isNull("sex"))
            u.sex = Integer.parseInt(o.optString("sex"));
        if(!o.isNull("bdate"))
            u.birthdate = o.optString("bdate");
        try{
            u.city = Integer.parseInt(o.optString("city"));
        }catch(NumberFormatException ex){}
        try{            
            u.country = Integer.parseInt(o.optString("country"));
        }catch(NumberFormatException ex){}
        if(!o.isNull("timezone"))
            u.timezone = o.optInt("timezone");
        if(!o.isNull("photo"))
            u.photo = o.optString("photo");
        if(!o.isNull("photo_medium"))
            u.photo_medium = o.optString("photo_medium");
        if(!o.isNull("photo_medium_rec"))
            u.photo_medium_rec = o.optString("photo_medium_rec");
        if(!o.isNull("photo_big"))
            u.photo_big = o.optString("photo_big");
        if(!o.isNull("has_mobile"))
            u.has_mobile = o.optInt("has_mobile")==1;
        if(!o.isNull("home_phone"))
            u.home_phone = o.optString("home_phone");
        if(!o.isNull("mobile_phone"))
            u.mobile_phone = o.optString("mobile_phone");
        if(!o.isNull("rate"))
            u.rate = Integer.parseInt(o.optString("rate"));
        try{
            u.faculty = Integer.parseInt(o.optString("faculty"));
        }catch(NumberFormatException ex){}
        if(!o.isNull("faculty_name"))
            u.faculty_name = o.optString("faculty_name");
        try{
            u.university = Integer.parseInt(o.optString("university"));
        }catch(NumberFormatException ex){}
        if(!o.isNull("university_name"))
            u.university_name = o.optString("university_name");
        try{
            u.graduation = Integer.parseInt(o.optString("graduation"));
        }catch(NumberFormatException ex){}
        if(!o.isNull("activity"))
            u.status = Api.unescape(o.optString("activity"));
        if(!o.isNull("relation"))
            u.relation = o.optInt("relation");
        if (!o.isNull("lists")) {
            JSONArray array = o.optJSONArray("lists");
            if (array != null) {
                String ids = "";
                for (int i=0; i<array.length()-1;++i)
                    ids += array.getString(i) + ",";
                ids += array.getString(array.length()-1);
                u.friends_list_ids = ids;
            }
        }
        if(!o.isNull("last_seen")) {
            JSONObject object = o.optJSONObject("last_seen");
            if (object != null)
                u.last_seen = object.optLong("time");
        }
        if(!o.isNull("counters")) {
            JSONObject object = o.optJSONObject("counters");
            if (object != null) {
                u.albums_count = object.optInt("albums");
                u.videos_count = object.optInt("videos");
                u.audios_count = object.optInt("audios");
                u.notes_count = object.optInt("notes");
                u.friends_count = object.optInt("friends");
                u.user_photos_count = object.optInt("user_photos");
                u.user_videos_count = object.optInt("user_videos");
                //u.online_friends_count = object.optInt("online_friends");
                //u.followers_count = object.optInt("followers");
                //u.subscriptions_count = object.optInt("subscriptions");
                u.groups_count = object.optInt("groups");
            }
        }
        if(!o.isNull("relation_partner")) {
            JSONObject object = o.optJSONObject("relation_partner");
            if (object != null) {
                u.relation_partner_id = object.optLong("id");
                u.relation_partner_first_name = object.optString("first_name");
                u.relation_partner_last_name = object.optString("last_name");
            }
        }
        return u;
    }
    
    public static User parseFromNews(JSONObject jprofile) throws JSONException {
        User m = new User();
        m.uid = Long.parseLong(jprofile.getString("uid"));
        m.first_name = Api.unescape(jprofile.getString("first_name"));
        m.last_name = Api.unescape(jprofile.getString("last_name"));
        m.photo = jprofile.getString("photo");
        try{
            m.sex = Integer.parseInt(jprofile.optString("sex"));
        }catch(NumberFormatException ex){
            //если там мусор, то мы это пропускаем
            ex.printStackTrace();
        }
        return m;
    }
    
    public static User parseFromGetByPhones(JSONObject o) throws JSONException {
        User u = new User();
        u.uid = o.getLong("uid");
        u.first_name = Api.unescape(o.optString("first_name"));
        u.last_name = Api.unescape(o.optString("last_name"));
        u.phone = o.optString("phone");
        return u;
    }
    
    public static ArrayList<User> parseUsers(JSONArray array) throws JSONException {
        ArrayList<User> users=new ArrayList<User>();
        //it may be null if no users returned
        //no users may be returned if we request users that are already removed
        if(array==null)
            return users;
        int category_count=array.length();
        for(int i=0; i<category_count; ++i){
            if(array.get(i)==null || ((array.get(i) instanceof JSONObject)==false))
                continue;
            JSONObject o = (JSONObject)array.get(i);
            User u = User.parse(o);
            users.add(u);
        }
        return users;
    }
    
    public static ArrayList<User> parseUsersForGetByPhones(JSONArray array) throws JSONException {
        ArrayList<User> users=new ArrayList<User>();
        //it may be null if no users returned
        //no users may be returned if we request users that are already removed
        if(array==null)
            return users;
        int category_count=array.length();
        for(int i=0; i<category_count; ++i){
            if(array.get(i)==null || ((array.get(i) instanceof JSONObject)==false))
                continue;
            JSONObject o = (JSONObject)array.get(i);
            User u = User.parseFromGetByPhones(o);
            users.add(u);
        }
        return users;
    }
    
    public static User parseFromFave(JSONObject jprofile) throws JSONException {
        User m = new User();
        m.uid = Long.parseLong(jprofile.getString("uid"));
        m.first_name = Api.unescape(jprofile.getString("first_name"));
        m.last_name = Api.unescape(jprofile.getString("last_name"));
        m.photo_medium_rec = jprofile.getString("photo_medium_rec");
        if(!jprofile.isNull("online"))
            m.online = jprofile.optInt("online")==1;
        return m;
    }
}
