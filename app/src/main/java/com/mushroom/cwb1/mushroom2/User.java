package com.mushroom.cwb1.mushroom2;

public class User {

    private int _id;
    private String user_name;
    private String password;
    private  String country;
    private String city;

    private long first_login;
    private long last_login;

    private float total_distance;
    private long total_time;

    private float highest_speed;
    private float average_speed;
    private float highest_acceleration;

    private double highest_altitude;
    private double lowest_altitude;
    private double highest_altitude_diff;

    private int nb_won_challenges;
    private int nb_days_biked;


    public User(){
        //One day, something very important will be written here.
    }

    public User(String user_name, String password, String country, String city) {
        this.user_name = user_name;
        this.password = password;
        this.country = country;
        this.city = city;
    }

    public User(String user_name, String password, String country, String city,
                long first_login, long last_login, long total_distance, long total_time,
                float highest_speed, float average_speed, float highest_acceleration,
                double highest_altitude, double lowest_altitude, double highest_altitude_diff,
                int nb_won_challenges, int nb_days_biked) {

        this.user_name = user_name;
        this.password = password;
        this.country = country;
        this.city = city;
        this.first_login = first_login;
        this.last_login = last_login;

        this.total_distance = total_distance;
        this.total_time = total_time;
        this.highest_speed = highest_speed;
        this.average_speed = average_speed;
        this.highest_acceleration = highest_acceleration;
        this.highest_altitude = highest_altitude;
        this.highest_altitude_diff = highest_altitude_diff;
        this.nb_won_challenges = nb_won_challenges;
        this.nb_days_biked = nb_days_biked;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getFirst_login() {
        return first_login;
    }

    public void setFirst_login(long first_login) {
        this.first_login = first_login;
    }

    public long getLast_login() {
        return last_login;
    }

    public void setLast_login(long last_login) {
        this.last_login = last_login;
    }

    public float getTotal_distance() {
        return total_distance;
    }

    public void setTotal_distance(float total_distance) {
        this.total_distance = total_distance;
    }

    public long getTotal_time() {
        return total_time;
    }

    public void setTotal_time(long total_time) {
        this.total_time = total_time;
    }

    public float getHighest_speed() {
        return highest_speed;
    }

    public void setHighest_speed(float highest_speed) {
        this.highest_speed = highest_speed;
    }

    public float getAverage_speed() {
        return average_speed;
    }

    public void setAverage_speed(float average_speed) {
        this.average_speed = average_speed;
    }

    public float getHighest_acceleration() {
        return highest_acceleration;
    }

    public void setHighest_acceleration(float highest_acceleration) {
        this.highest_acceleration = highest_acceleration;
    }

    public double getHighest_altitude() {
        return highest_altitude;
    }

    public void setHighest_altitude(double highest_altitude) {
        this.highest_altitude = highest_altitude;
    }

    public double getLowest_altitude() {
        return lowest_altitude;
    }

    public void setLowest_altitude(double lowest_altitude) {
        this.lowest_altitude = lowest_altitude;
    }

    public double getHighest_altitude_diff() {
        return highest_altitude_diff;
    }

    public void setHighest_altitude_diff(double highest_altitude_diff) {
        this.highest_altitude_diff = highest_altitude_diff;
    }

    public int getNb_won_challenges() {
        return nb_won_challenges;
    }

    public void setNb_won_challenges(int nb_won_challenges) {
        this.nb_won_challenges = nb_won_challenges;
    }

    public int getNb_days_biked() {
        return nb_days_biked;
    }

    public void setNb_days_biked(int nb_days_biked) {
        this.nb_days_biked = nb_days_biked;
    }
}


