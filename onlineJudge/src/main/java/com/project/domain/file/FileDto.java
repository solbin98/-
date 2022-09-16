package com.project.domain.file;


import java.time.LocalDateTime;

public class FileDto {
    int file_id;
    String name;
    String path;
    String type;
    LocalDateTime date;
    boolean used;

    public FileDto(int file_id, String name, String path, String type, LocalDateTime date, boolean used) {
        this.file_id = file_id;
        this.name = name;
        this.path = path;
        this.type = type;
        this.date = date;
        this.used = used;
    }

    public int getFile_id() {
        return file_id;
    }

    public void setFile_id(int file_id) {
        this.file_id = file_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public boolean getUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
