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
    private int total_points;


    private int drive_1_km;
    private int drive_5_km;
    private int drive_10_km;
    private int drive_50_km;
    private int drive_100_km;
    private int drive_250_km;
    private int drive_500_km;
    private int drive_1000_km;
    private int drive_5000_km;


    private int topspeed_30;
    private int topspeed_35;
    private int topspeed_40;
    private int topspeed_45;
    private int topspeed_50;

    private int nb_challenge_1;
    private int nb_challenge_5;
    private int nb_challenge_10;
    private int nb_challenge_50;
    private int nb_challenge_200;
    private int nb_challenge_500;

    private int biked_days_1;
    private int biked_days_2;
    private int biked_days_5;
    private int biked_days_7;
    private int biked_days_14;
    private int biked_days_31;
    private int biked_days_100;

    private int below_seelvl;
    private int above_1000m;
    private int alt_diff_10m;
    private int alt_diff_25m;
    private int alt_diff_50m;
    private int alt_diff_100m;


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
                int nb_won_challenges, int nb_days_biked, int total_points, int drive_1_km, int drive_5_km, int drive_10_km,
                int drive_50_km, int drive_100_km, int drive_250_km, int drive_500_km, int drive_1000_km, int drive_5000_km,
                int topspeed_30, int topspeed_35, int topspeed_40, int topspeed_45, int topspeed_50,
                int nb_challenge_1, int nb_challenge_5, int nb_challenge_10, int nb_challenge_50, int nb_challenge_200,
                int nb_challenge_500, int biked_days_1, int biked_days_2, int biked_days_5, int biked_days_7,
                int biked_days_14, int biked_days_31, int biked_days_100, int below_seelvl, int above_1000m,
                int alt_diff_10m, int alt_diff_25m, int alt_diff_50m, int alt_diff_100m) {

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
        this.lowest_altitude = lowest_altitude;
        this.highest_altitude_diff = highest_altitude_diff;
        this.nb_won_challenges = nb_won_challenges;
        this.nb_days_biked = nb_days_biked;
        this.total_points = total_points;

        this.drive_1_km = drive_1_km;
        this.drive_5_km = drive_5_km;
        this.drive_10_km = drive_10_km;
        this.drive_50_km = drive_50_km;
        this.drive_100_km = drive_100_km;
        this.drive_250_km = drive_250_km;
        this.drive_500_km = drive_500_km;
        this.drive_1000_km = drive_1000_km;
        this.drive_5000_km = drive_5000_km;

        this.topspeed_30 = topspeed_30;
        this.topspeed_35 = topspeed_35;
        this.topspeed_40 = topspeed_40;
        this.topspeed_45 = topspeed_45;
        this.topspeed_50 = topspeed_50;

        this.nb_challenge_1 = nb_challenge_1;
        this.nb_challenge_5 = nb_challenge_5;
        this.nb_challenge_10 = nb_challenge_10;
        this.nb_challenge_50 = nb_challenge_50;
        this.nb_challenge_200 = nb_challenge_200;
        this.nb_challenge_500 = nb_challenge_500;

        this.biked_days_1 = biked_days_1;
        this.biked_days_2 = biked_days_2;
        this.biked_days_5 = biked_days_5;
        this.biked_days_7 = biked_days_7;
        this.biked_days_14 = biked_days_14;
        this.biked_days_31 = biked_days_31;
        this.biked_days_100 = biked_days_100;

        this.below_seelvl = below_seelvl;
        this.above_1000m = above_1000m;;
        this.alt_diff_10m = alt_diff_10m;
        this.alt_diff_25m = alt_diff_25m;
        this.alt_diff_50m = alt_diff_50m;
        this.alt_diff_100m = alt_diff_100m;
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


    public int getDrive_1_km() {
        return drive_1_km;
    }

    public void setDrive_1_km(int drive_1_km) {
        this.drive_1_km = drive_1_km;
    }

    public int getDrive_5_km() {
        return drive_5_km;
    }

    public void setDrive_5_km(int drive_5_km) {
        this.drive_5_km = drive_5_km;
    }

    public int getDrive_10_km(){
        return drive_10_km;
    }
    public void setDrive_10_km(int drive_10_km){
        this.drive_10_km = drive_10_km;
    }

    public int getDrive_50_km(){
        return drive_50_km;
    }
    public void setDrive_50_km(int drive_50_km){
        this.drive_50_km = drive_50_km;
    }

    public int getDrive_100_km(){
        return drive_100_km;
    }
    public void setDrive_100_km(int drive_100_km){
        this.drive_100_km = drive_100_km;
    }

    public int getDrive_250_km(){
        return drive_250_km;
    }
    public void setDrive_250_km(int drive_250_km){
        this.drive_250_km = drive_250_km;
    }

    public int getDrive_500_km(){
        return drive_500_km;
    }
    public void setDrive_500_km(int drive_500_km){
        this.drive_500_km = drive_500_km;
    }

    public int getDrive_1000_km(){
        return drive_1000_km;
    }
    public void setDrive_1000_km(int drive_1000_km){
        this.drive_1000_km = drive_1000_km;
    }

    public int getDrive_5000_km(){
        return drive_5000_km;
    }
    public void setDrive_5000_km(int drive_5000_km){
        this.drive_5000_km = drive_5000_km;
    }

    public int getTopspeed_30(){
        return topspeed_30;
    }
    public void setTopspeed_30(int topspeed_30){
        this.topspeed_30 = topspeed_30;
    }

    public int getTopspeed_35(){
        return topspeed_35;
    }
    public void setTopspeed_35(int topspeed_35){
        this.topspeed_35 = topspeed_35;
    }

    public int getTopspeed_40(){
        return topspeed_40;
    }
    public void setTopspeed_40(int topspeed_40){
        this.topspeed_40 = topspeed_40;
    }

    public int getTopspeed_45(){
        return topspeed_45;
    }
    public void setTopspeed_45(int topspeed_45){
        this.topspeed_45 = topspeed_45;
    }

    public int getTopspeed_50(){
        return topspeed_50;
    }
    public void setTopspeed_50(int topspeed_50){
        this.topspeed_50 = topspeed_50;
    }

    public int getNb_challenge_1(){
        return nb_challenge_1;
    }
    public int getNb_challenge_5(){
        return nb_challenge_5;
    }
    public int getNb_challenge_10(){
        return nb_challenge_10;
    }
    public int getNb_challenge_50(){
        return nb_challenge_50;
    }
    public int getNb_challenge_200(){
        return nb_challenge_200;
    }
    public int getNb_challenge_500(){
        return nb_challenge_500;
    }

    public void setNb_challenge_1(int nb_challenge_1){
        this.nb_challenge_1 = nb_challenge_1;
    }
    public void setNb_challenge_5(int nb_challenge_5){
        this.nb_challenge_5 = nb_challenge_5;
    }
    public void setNb_challenge_10(int nb_challenge_10){
        this.nb_challenge_10 = nb_challenge_10;
    }
    public void setNb_challenge_50(int nb_challenge_50){
        this.nb_challenge_50 = nb_challenge_50;
    }
    public void setNb_challenge_200(int nb_challenge_200){
        this.nb_challenge_200 = nb_challenge_200;
    }
    public void setNb_challenge_500(int nb_challenge_500){
        this.nb_challenge_500 = nb_challenge_500;
    }

    public int getBiked_days_1(){
        return biked_days_1;
    }
    public int getBiked_days_2(){
        return biked_days_2;
    }
    public int getBiked_days_5(){
        return biked_days_5;
    }
    public int getBiked_days_7(){
        return biked_days_7;
    }
    public int getBiked_days_14(){
        return biked_days_14;
    }
    public int getBiked_days_31(){
        return biked_days_31;
    }
    public int getBiked_days_100(){
        return biked_days_100;
    }

    public void setBiked_days_1(int biked_days_1){
        this.biked_days_1 = biked_days_1;
    }
    public void setBiked_days_2(int biked_days_2){
        this.biked_days_2 = biked_days_2;
    }
    public void setBiked_days_5(int biked_days_5){
        this.biked_days_5 = biked_days_5;
    }
    public void setBiked_days_7(int biked_days_7){
        this.biked_days_7 = biked_days_7;
    }
    public void setBiked_days_14(int biked_days_14){
        this.biked_days_14 = biked_days_14;
    }
    public void setBiked_days_31(int biked_days_31){
        this.biked_days_31 = biked_days_31;
    }
    public void setBiked_days_100(int biked_days_100){this.biked_days_100 = biked_days_100;}

    public int getBelow_seelvl(){return below_seelvl;}
    public int getAbove_1000m(){return above_1000m;}
    public int getAlt_diff_10m(){return alt_diff_10m;}
    public int getAlt_diff_25m(){return alt_diff_25m;}
    public int getAlt_diff_50m(){return alt_diff_50m;}
    public int getAlt_diff_100m(){return alt_diff_100m;}

    public void setBelow_seelvl(int below_seelvl){this.below_seelvl = below_seelvl;}
    public void setAbove_1000m(int above_1000m){this.above_1000m = above_1000m;}
    public void setAlt_diff_10m(int alt_diff_10m){this.alt_diff_10m = alt_diff_10m;}
    public void setAlt_diff_25m(int alt_diff_25m){this.alt_diff_25m = alt_diff_25m;}
    public void setAlt_diff_50m(int alt_diff_50m){this.alt_diff_50m = alt_diff_50m;}
    public void setAlt_diff_100m(int alt_diff_100m){this.alt_diff_100m = alt_diff_100m;}

    public int getTotal_points() {
        return total_points;
    }

    public void setTotal_points(int total_points) {
        this.total_points = total_points;
    }
}


