package com.kaloudasdev.frostshield.utils;

import javax.net.ssl.HttpsURLConnection;
import java.awt.Color;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DiscordWebhook {
    
    private final String url;
    private String content;
    private String username;
    private String avatarUrl;
    private boolean tts;
    private List<EmbedObject> embeds = new ArrayList<>();
    
    public DiscordWebhook(String url) { this.url = url; }
    
    public void setContent(String content) { this.content = content; }
    public void setUsername(String username) { this.username = username; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public void setTts(boolean tts) { this.tts = tts; }
    public void addEmbed(EmbedObject embed) { this.embeds.add(embed); }
    
    public void execute() throws Exception {
        if (this.content == null && this.embeds.isEmpty()) {
            throw new IllegalArgumentException("Set content or add at least one embed");
        }
        
        JSONObject json = new JSONObject();
        json.put("content", this.content);
        json.put("username", this.username);
        json.put("avatar_url", this.avatarUrl);
        json.put("tts", this.tts);
        
        if (!this.embeds.isEmpty()) {
            List<JSONObject> embedObjects = new ArrayList<>();
            for (EmbedObject embed : this.embeds) {
                JSONObject jsonEmbed = new JSONObject();
                jsonEmbed.put("title", embed.getTitle());
                jsonEmbed.put("description", embed.getDescription());
                jsonEmbed.put("url", embed.getUrl());
                jsonEmbed.put("color", embed.getColor());
                jsonEmbed.put("timestamp", embed.getTimestamp());
                
                if (embed.getFooter() != null) {
                    JSONObject jsonFooter = new JSONObject();
                    jsonFooter.put("text", embed.getFooter().getText());
                    jsonFooter.put("icon_url", embed.getFooter().getIconUrl());
                    jsonEmbed.put("footer", jsonFooter);
                }
                
                if (embed.getImage() != null) {
                    JSONObject jsonImage = new JSONObject();
                    jsonImage.put("url", embed.getImage().getUrl());
                    jsonEmbed.put("image", jsonImage);
                }
                
                if (embed.getThumbnail() != null) {
                    JSONObject jsonThumbnail = new JSONObject();
                    jsonThumbnail.put("url", embed.getThumbnail().getUrl());
                    jsonEmbed.put("thumbnail", jsonThumbnail);
                }
                
                if (embed.getAuthor() != null) {
                    JSONObject jsonAuthor = new JSONObject();
                    jsonAuthor.put("name", embed.getAuthor().getName());
                    jsonAuthor.put("url", embed.getAuthor().getUrl());
                    jsonAuthor.put("icon_url", embed.getAuthor().getIconUrl());
                    jsonEmbed.put("author", jsonAuthor);
                }
                
                List<JSONObject> jsonFields = new ArrayList<>();
                for (EmbedObject.Field field : embed.getFields()) {
                    JSONObject jsonField = new JSONObject();
                    jsonField.put("name", field.getName());
                    jsonField.put("value", field.getValue());
                    jsonField.put("inline", field.isInline());
                    jsonFields.add(jsonField);
                }
                jsonEmbed.put("fields", jsonFields.toArray());
                embedObjects.add(jsonEmbed);
            }
            json.put("embeds", embedObjects.toArray());
        }
        
        URL url = new URL(this.url);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.addRequestProperty("Content-Type", "application/json");
        connection.addRequestProperty("User-Agent", "Java-DiscordWebhook");
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        
        OutputStream stream = connection.getOutputStream();
        stream.write(json.toString().getBytes());
        stream.flush();
        stream.close();
        
        connection.getInputStream().close();
        connection.disconnect();
    }
    
    public static class EmbedObject {
        private String title;
        private String description;
        private String url;
        private int color;
        private String timestamp;
        private Footer footer;
        private Thumbnail thumbnail;
        private Image image;
        private Author author;
        private List<Field> fields = new ArrayList<>();
        
        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public String getUrl() { return url; }
        public int getColor() { return color; }
        public String getTimestamp() { return timestamp; }
        public Footer getFooter() { return footer; }
        public Thumbnail getThumbnail() { return thumbnail; }
        public Image getImage() { return image; }
        public Author getAuthor() { return author; }
        public List<Field> getFields() { return fields; }
        
        public EmbedObject setTitle(String title) { this.title = title; return this; }
        public EmbedObject setDescription(String description) { this.description = description; return this; }
        public EmbedObject setUrl(String url) { this.url = url; return this; }
        public EmbedObject setColor(int color) { this.color = color; return this; }
        public EmbedObject setTimestamp(String timestamp) { this.timestamp = timestamp; return this; }
        public EmbedObject setFooter(String text, String iconUrl) { this.footer = new Footer(text, iconUrl); return this; }
        public EmbedObject setThumbnail(String url) { this.thumbnail = new Thumbnail(url); return this; }
        public EmbedObject setImage(String url) { this.image = new Image(url); return this; }
        public EmbedObject setAuthor(String name, String url, String iconUrl) { this.author = new Author(name, url, iconUrl); return this; }
        public EmbedObject addField(String name, String value, boolean inline) { this.fields.add(new Field(name, value, inline)); return this; }
        
        private class Footer { private String text; private String iconUrl; Footer(String text, String iconUrl) { this.text = text; this.iconUrl = iconUrl; } public String getText() { return text; } public String getIconUrl() { return iconUrl; } }
        private class Thumbnail { private String url; Thumbnail(String url) { this.url = url; } public String getUrl() { return url; } }
        private class Image { private String url; Image(String url) { this.url = url; } public String getUrl() { return url; } }
        private class Author { private String name; private String url; private String iconUrl; Author(String name, String url, String iconUrl) { this.name = name; this.url = url; this.iconUrl = iconUrl; } public String getName() { return name; } public String getUrl() { return url; } public String getIconUrl() { return iconUrl; } }
        private class Field { private String name; private String value; private boolean inline; Field(String name, String value, boolean inline) { this.name = name; this.value = value; this.inline = inline; } public String getName() { return name; } public String getValue() { return value; } public boolean isInline() { return inline; } }
    }
    
    private static class JSONObject {
        private final HashMap<String, Object> map = new HashMap<>();
        public void put(String key, Object value) { map.put(key, value); }
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            builder.append("{");
            int i = 0;
            for (Map.Entry<String, Object> entry : entries) {
                builder.append(quote(entry.getKey())).append(":");
                builder.append(toString(entry.getValue()));
                if (i != entries.size() - 1) builder.append(",");
                i++;
            }
            builder.append("}");
            return builder.toString();
        }
        private String toString(Object object) {
            if (object == null) return "null";
            if (object instanceof JSONObject) return object.toString();
            if (object instanceof String) return quote((String) object);
            if (object.getClass().isArray()) {
                StringBuilder builder = new StringBuilder();
                builder.append("[");
                int length = Array.getLength(object);
                for (int i = 0; i < length; i++) {
                    builder.append(toString(Array.get(object, i)));
                    if (i != length - 1) builder.append(",");
                }
                builder.append("]");
                return builder.toString();
            }
            return object.toString();
        }
        private String quote(String string) { return "\"" + string + "\""; }
    }
}
