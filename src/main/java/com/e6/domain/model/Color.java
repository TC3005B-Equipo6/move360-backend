package com.e6.domain.model;

public class Color {
    private int id;
    private String name;
    private String hex;

    public Color () {}

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private int id;
        private String name;
        private String hex;

        public Builder id(int id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder hex(String hex) { this.hex = hex; return this;}

        public Color build(){
            Color color = new Color();
            color.id = this.id;
            color.name = this.name;
            color.hex = this.hex;
            return color;
        }
    }

    public Color (int id, String name, String hex) {
        this.id = id;
        this.name = name;
        this.hex = hex;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getHex() {
        return hex;
    }
}
