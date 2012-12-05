package com.perm.kate.api;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WallMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    public long from_id;
    public long to_id;
    public long date; 
    public String text;
    public long id;
    public String online;
    public ArrayList<Attachment> attachments;
    public long comment_count;
    public boolean comment_can_post;

    //likes
    public int like_count;
    public boolean user_like;
    public boolean can_like;
    public boolean like_can_publish;
    
    public long copy_owner_id=0;
    public long copy_post_id=0;
    
    public static WallMessage parse(JSONObject o) throws JSONException {
        WallMessage wm = new WallMessage();
        wm.id = o.getLong("id");
        wm.from_id = o.getLong("from_id");
        wm.to_id = o.getLong("to_id");
        wm.date = o.getLong("date");
        wm.online = o.optString("online");
        wm.text = Api.unescape(o.getString("text"));
        if (o.has("likes")){
            JSONObject jlikes = o.getJSONObject(NewsJTags.LIKES);
            wm.like_count = jlikes.getInt("count");
            wm.user_like = jlikes.getInt("user_likes")==1;
            wm.can_like = jlikes.getInt("can_like")==1;
            wm.like_can_publish = jlikes.getInt("can_publish")==1;
        }
        wm.copy_owner_id = o.optLong("copy_owner_id");
        JSONArray attachments=o.optJSONArray("attachments");
        JSONObject geo_json=o.optJSONObject("geo");
        //владельцем опроса является to_id. Даже если добавить опрос в группу от своего имени, то from_id буду я, но опрос всё-равно будет принадлежать группе.
        wm.attachments=Attachment.parseAttachments(attachments, wm.to_id, wm.copy_owner_id, geo_json);
        if (o.has("comments")){
            JSONObject jcomments = o.getJSONObject("comments");
            wm.comment_count = jcomments.getInt("count");
            wm.comment_can_post = jcomments.getInt("can_post")==1;
        }
        return wm;
    }
    
    public static WallMessage parseForNotifications(JSONObject o) throws JSONException {
        WallMessage wm = new WallMessage();
        wm.id = o.getLong("id");
        wm.from_id = Long.parseLong(o.getString("owner_id"));
        wm.text = Api.unescape(o.getString("text"));
        //likes is there but I don't parse it because I don't need it
        //if (o.has("likes")){
        //    JSONObject jlikes = o.getJSONObject(NewsJTags.LIKES);
        //    wm.like_count = jlikes.getInt("count");
        //    wm.user_like = jlikes.getInt("user_likes")==1;
        //    wm.can_like = jlikes.getInt("can_like")==1;
        //    wm.like_can_publish = jlikes.getInt("can_publish")==1;
        //}
        JSONArray attachments=o.optJSONArray("attachments");
        JSONObject geo_json=o.optJSONObject("geo");
        wm.attachments=Attachment.parseAttachments(attachments, wm.to_id, wm.copy_owner_id, geo_json);
        return wm;
    }
}