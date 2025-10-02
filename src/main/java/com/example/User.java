package com.example; 

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private List<String> likedSongs;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.likedSongs = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getLikedSongs() {
        return likedSongs;
    }

    public void addLikedSong(String songName) {
        if (!likedSongs.contains(songName)) {
            this.likedSongs.add(songName);
        }
    }
}