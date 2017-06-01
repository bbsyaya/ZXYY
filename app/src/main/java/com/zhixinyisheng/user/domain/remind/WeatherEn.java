package com.zhixinyisheng.user.domain.remind;

import java.util.List;

/**
 * Created by 焕焕 on 2017/4/28.
 */

public class WeatherEn {

    /**
     * base : stations
     * clouds : {"all":0}
     * cod : 200
     * coord : {"lat":38.04,"lon":114.48}
     * dt : 1493344800
     * id : 1795270
     * main : {"humidity":18,"pressure":1010,"temp":300.15,"temp_max":300.15,"temp_min":300.15}
     * name : Shijiazhuang
     * sys : {"country":"CN","id":7408,"message":0.0163,"sunrise":1493328580,"sunset":1493377805,"type":1}
     * visibility : 10000
     * weather : [{"description":"clear sky","icon":"01d","id":800,"main":"Clear"}]
     * wind : {"deg":280,"speed":4}
     */

    private String base;
    /**
     * all : 0
     */

    private CloudsBean clouds;
    private int cod;
    /**
     * lat : 38.04
     * lon : 114.48
     */

    private CoordBean coord;
    private int dt;
    private int id;
    /**
     * humidity : 18
     * pressure : 1010
     * temp : 300.15
     * temp_max : 300.15
     * temp_min : 300.15
     */

    private MainBean main;
    private String name;
    /**
     * country : CN
     * id : 7408
     * message : 0.0163
     * sunrise : 1493328580
     * sunset : 1493377805
     * type : 1
     */

    private SysBean sys;
    private int visibility;
    /**
     * deg : 280
     * speed : 4
     */

    private WindBean wind;
    /**
     * description : clear sky
     * icon : 01d
     * id : 800
     * main : Clear
     */

    private List<WeatherBean> weather;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public CloudsBean getClouds() {
        return clouds;
    }

    public void setClouds(CloudsBean clouds) {
        this.clouds = clouds;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public CoordBean getCoord() {
        return coord;
    }

    public void setCoord(CoordBean coord) {
        this.coord = coord;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MainBean getMain() {
        return main;
    }

    public void setMain(MainBean main) {
        this.main = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SysBean getSys() {
        return sys;
    }

    public void setSys(SysBean sys) {
        this.sys = sys;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public WindBean getWind() {
        return wind;
    }

    public void setWind(WindBean wind) {
        this.wind = wind;
    }

    public List<WeatherBean> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherBean> weather) {
        this.weather = weather;
    }

    public static class CloudsBean {
        private int all;

        public int getAll() {
            return all;
        }

        public void setAll(int all) {
            this.all = all;
        }
    }

    public static class CoordBean {
        private double lat;
        private double lon;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }
    }

    public static class MainBean {
        private int humidity;
        private int pressure;
        private double temp;
        private double temp_max;
        private double temp_min;

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public int getPressure() {
            return pressure;
        }

        public void setPressure(int pressure) {
            this.pressure = pressure;
        }

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public double getTemp_max() {
            return temp_max;
        }

        public void setTemp_max(double temp_max) {
            this.temp_max = temp_max;
        }

        public double getTemp_min() {
            return temp_min;
        }

        public void setTemp_min(double temp_min) {
            this.temp_min = temp_min;
        }
    }

    public static class SysBean {
        private String country;
        private int id;
        private double message;
        private int sunrise;
        private int sunset;
        private int type;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getMessage() {
            return message;
        }

        public void setMessage(double message) {
            this.message = message;
        }

        public int getSunrise() {
            return sunrise;
        }

        public void setSunrise(int sunrise) {
            this.sunrise = sunrise;
        }

        public int getSunset() {
            return sunset;
        }

        public void setSunset(int sunset) {
            this.sunset = sunset;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public static class WindBean {
        private int deg;
        private int speed;

        public int getDeg() {
            return deg;
        }

        public void setDeg(int deg) {
            this.deg = deg;
        }

        public int getSpeed() {
            return speed;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }
    }

    public static class WeatherBean {
        private String description;
        private String icon;
        private int id;
        private String main;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }
    }
}
