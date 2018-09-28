package com.whut.oneday.item;

public class TodayWeather {
    private String time;
    private String location;
    private String temperature;
    private boolean symbol;
    private Integer weather;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public boolean isSymbol() {
        return symbol;
    }

    public void setSymbol(boolean symbol) {
        this.symbol = symbol;
    }

    public Integer getWeather() {
        return weather;
    }

    public void setWeather(Integer weather) {
        this.weather = weather;
    }

    public TodayWeather() {
    }

    public TodayWeather(String time, String location, String temperature, boolean symbol, Integer weather) {
        this.time = time;
        this.location = location;
        this.temperature = temperature;
        this.symbol = symbol;
        this.weather = weather;
    }
}
