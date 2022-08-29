package com.project.dto;

public class FileDto {
    int file_id;
    String name;
    String path;
    String type;
    String date;
    String size;
    boolean used;

    public FileDto(int file_id, String name, String path, String type, String date, String size, boolean used) {
        this.file_id = file_id;
        this.name = name;
        this.path = path;
        this.type = type;
        this.date = date;
        this.size = size;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
