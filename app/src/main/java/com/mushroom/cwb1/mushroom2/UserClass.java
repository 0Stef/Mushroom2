package com.mushroom.cwb1.mushroom2;

public class UserClass {
    private int _id;
    private int user_id;
    private String user_name;
    private String password;
    private long last_login;
    private long first_login;
    private float total_distance;
    private float highest_speed;
    private int nb_won_challenges;
    private float highest_acceleration;
    private float average_speed;
    private long total_time;
    private double highest_altitude;
    private double lowest_altitude;
    private double highest_altitude_diff;
    private int nb_days_biked;
    private  String country;
    private String city;


    public UserClass(){

    }


    public UserClass(int _id,int user_id, String user_name,
                     String password, long last_login, long first_login,
                     float total_distance, float highest_speed, int nb_won_challenges, float highest_acceleration,
                     float average_speed, long total_time, double highest_altitude, double lowest_altitude,
                     double highest_altitude_diff,int nb_days_biked, String country, String city) {

        this._id = _id;
        this.user_id = user_id;
        this.user_name = user_name;
        this.password = password;
        this.last_login = last_login;
        this.first_login = first_login;
        this.total_distance = total_distance;
        this.highest_speed = highest_speed;
        this.nb_won_challenges = nb_won_challenges;
        this.highest_acceleration = highest_acceleration;
        this.average_speed = average_speed;
        this.total_time = total_time;
        this.highest_altitude = highest_altitude;
        this.lowest_altitude = lowest_altitude;
        this.highest_altitude_diff = highest_altitude_diff;
        this.nb_days_biked = nb_days_biked;
        this.country = country;
        this.city = city;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
    public int get_id() {
        return _id;
    }

    public void setuser_id() {
        this.user_id = user_id;
    }
    public int getUser_id(){
        return user_id;
    }

    public void setUser_name(){
        this.user_name = user_name;
    }
    public String getUser_name(){
        return user_name;
    }

    private void setPassword(){
        this.password = password;
    }
    private String getPassword(){
        return password;
    }

    public void setLast_login(){
        this.last_login = last_login;
    }
    public long getLast_login(){
        return last_login;
    }

    public void setFirst_login(){
        this.first_login = first_login;
    }
    public long getFirst_login(){
        return first_login;
    }

    public void setTotal_distance(){
        this.total_distance = total_distance;
    }
    public float getTotal_distance(){
        return total_distance;
    }

    public void setHighest_speed(){
        this.highest_speed = highest_speed;
    }
    public float getHighest_speed(){
        return highest_speed;
    }

    public void setNb_won_challenges(){
        this.nb_won_challenges = nb_won_challenges;
    }
    public int getNb_won_challenges(){
        return nb_won_challenges;
    }

    public void setHighest_acceleration(){
        this.highest_acceleration = highest_acceleration;
    }
    public float getHighest_acceleration(){
        return highest_acceleration;
    }

    public void setAverage_speed(){
        this.average_speed = average_speed;
    }
    public float getAverage_speed(){
        return average_speed;
    }

    public void setTotal_time(){
        this.total_time = total_time;
    }
    public long getTotal_time(){
        return total_time;
    }

    public void setHighest_altitude(){
        this.highest_altitude = highest_altitude;
    }
    public double getHighest_altitude(){
        return highest_altitude;
    }

    public void setLowest_altitude(){
        this.lowest_altitude = lowest_altitude;
    }
    public double getLowest_altitude(){
        return lowest_altitude;
    }

    public void setHighest_altitude_diff(){
        this.highest_altitude_diff = highest_altitude_diff;
    }
    public double getHighest_altitude_diff(){
        return highest_altitude_diff;
    }

    public void setNb_days_biked(){
        this.nb_days_biked = nb_days_biked;
    }
    public int getNb_days_biked(){
        return nb_days_biked;
    }

    public void setCountry(){
        this.country = country;
    }
    public String getCountry(){
        return country;
    }

    public void setCity(){
        this.city = city;
    }
    public String getCity(){
        return city;
    }



}
