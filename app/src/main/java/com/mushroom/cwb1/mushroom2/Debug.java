package com.mushroom.cwb1.mushroom2;

import android.content.Context;
import android.widget.TextView;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Random;

public class Debug {

    private Context context;
    private TextView debugView;

    private UserHandler userHandler;
    private DataBaseHandler2 dbHandler;
    private Random random;

    private long maxRideTime = 604800000l;
    private long minRidetime = 1800000l;
    private long maxMeasureTime = 5000l;
    private long minMeasureTime = 250l;

    private float dfltAcc = 0f;
    private float maxAcc = 16f;
    private float minAcc = 0f;
    private float maxAccI = 5f;
    private float minAccI = 0.01f;

    private double dfltLon = 3000d;
    private double dfltLat = 3000d;
    private double maxDistRI = 30d;
    private double minDistRI = 5d;
    private double maxDisI = 10d;
    private double minDistI = 0d;
    private float dfltAlt = 30f;

    private float dfltMag = 5f;
    private float maxMag = 10f;
    private float minMag = 2f;
    private float maxMagI = 3f;
    private float minMagI = 0.01f;


    public Debug(Context context, TextView debugView) {
        this.context = context;
        this.debugView = debugView;

        userHandler = new UserHandler(context);
        dbHandler = new DataBaseHandler2(context);
        random = new Random();
    }

    public boolean execute(String command, String param) {
        if (command.equals("reset") || command.equals("rst")) {
            System.out.println("    -   Reset");
            if (param.equals("app")) {
                resetApp();
            } else if (param.equals("default") || param.equals("dflt")) {
                dbHandler.resetTable(dbHandler.TABLE_DEFAULT);
            } else if (userHandler.isExistingUser(param)) {
                dbHandler.resetTable(param);
            }
            return true;
        }
        if (command.equals("delete") || command.equals("dlt")) {
            System.out.println("    -   Delete");
            if (userHandler.isExistingUser(param)) {
                deleteUser(param);
            }
            return true;
        }
        if (command.equals("stock") || command.equals("fill")) {
            System.out.println("    -   Stock");
            String[] params = param.split("_:_");

            String userName = "";
            int length = 250;
            int amount = 1;

            if (params.length > 0) userName = params[0];
            if (params.length > 1) length = Integer.parseInt(params[1]);
            if (params.length > 2) amount = Integer.parseInt(params[2]);

            if (userHandler.isExistingUser(userName)) {
                createMeasurements(userName, length, amount);
                update(userName);
            }
            return true;
        }
        if (command.equals("update") || command.equals("upd")) {
            System.out.println("    -   Update");
            if (userHandler.isExistingUser(param)) {
                update(param);
            }
            return true;
        }
        if (command.equals("get_userlist") || command.equals("list") || command.equals("lst")) {
            System.out.println("    -   Users");
            getUserList();
            return true;
        }
        if (command.equals("cmd") || command.equals("?") || command.equals("help")) {
            System.out.println("    -   List");
            showCommands();
            return true;
        }
        if (command.equals("clear screen") || command.equals("cls")){
            System.out.println("    -   Clear");
            debugView.setText("");
            return true;
        }
        return false;
    }

    private void resetApp() {
        LinkedList<User> list = userHandler.getList(userHandler.getAll());
        for (User user : list) {
            dbHandler.deleteTable(user.getUser_name());
        }
        dbHandler.resetTable(dbHandler.TABLE_DEFAULT);
        userHandler.resetTable();
    }

    private void deleteUser(String userName) {
        if (userHandler.isExistingUser(userName)) {
            dbHandler.deleteTable(userName);
            userHandler.deleteUser(userName);
        }
    }

    private void getUserList() {
        LinkedList<User> list = userHandler.getList(userHandler.getAll());
        debugView.append("\n");
        debugView.append("List: \n");
        for (User user : list) {
            debugView.append("  - " + user.toString() + "\n");
        }
    }

    private void showCommands() {
        debugView.append("\n");
        debugView.append("Commands:\n");
        debugView.append("  - reset  [app/user]\n");
        debugView.append("  - delete [user]\n");
        debugView.append("  - stock  [user] [length] [amount]\n");
        debugView.append("  - update [user]\n");
        debugView.append("  - get userlist\n");
        debugView.append("  - clear screen\n");
    }

    private void createMeasurements(String userName, int length, int amount) {
        dbHandler.setTable(userName);
        int rideID = dbHandler.getGreatestRideID();
        long time = dbHandler.getRow(dbHandler.getLast()).getMillisec();
        if (time == 0l) {
            time = Calendar.getInstance().getTimeInMillis();
        }

        float accX = dfltAcc;
        float accY = dfltAcc;
        float accZ = dfltAcc;

        double lon = dfltLon;
        double lat = dfltLat;

        float magX = dfltMag;
        float magY = dfltMag;
        float magZ = dfltMag;

        for (int i = 0; i < amount; i++) {
            rideID++;
            time += minRidetime + random.nextFloat() * (maxRideTime - minRidetime);

            double distanceR = random.nextDouble() * (maxDistRI - minDistRI) + minDistRI;
            if (random.nextBoolean()) {
                if (random.nextBoolean()) {
                    lon += distanceR;
                } else {
                    lon -= distanceR;
                }
            } else {
                if (random.nextBoolean()) {
                    lat += distanceR;
                } else {
                    lat -= distanceR;
                }
            }

            for (int j = 0; j < length; j++) {
                dbRow row = new dbRow();
                row.setRide_id(rideID);

                //Time
                long rideTime = minMeasureTime + (long) (random.nextFloat() * (maxMeasureTime - minMeasureTime));
                //System.out.println("        " + rideTime + ", " + time);
                time += rideTime;
                row.setMillisec(time);


                //Acceleration
                do {
                    float delta = random.nextFloat() * (maxAccI - minAccI) + minAccI;
                    if (random.nextBoolean()) {
                        accX += delta;
                    } else {
                        accX -= delta;
                    }
                } while (!(minAcc < accX  && accX < maxAcc));

                do {
                    float delta = random.nextFloat() * (maxAccI - minAccI) + minAccI;
                    if (random.nextBoolean()) {
                        accZ += delta;
                    } else {
                        accZ -= delta;
                    }
                } while (!(minAcc < accZ  && accZ < maxAcc));

                row.setAccelerometer(accX, accY, accZ);


                //GPS
                double distance = random.nextDouble() * (maxDisI - minDistI) + minDistI;
                if (random.nextBoolean()) {
                    if (random.nextBoolean()) {
                        lon += distance;
                    } else {
                        lon -= distance;
                    }
                } else {
                    if (random.nextBoolean()) {
                        lat += distance;
                    } else {
                        lat -= distance;
                    }
                }

                float velocity = (float) ((distance * 1000) / rideTime);

                row.setGps(velocity, lon, lat, dfltAlt);


                //Magnetic
                do {
                    float delta = random.nextFloat() * (maxMagI - minMagI) + minMagI;
                    if (random.nextBoolean()) {
                        magX += delta;
                    } else {
                        magX -= delta;
                    }
                } while (!(minMag < magX  && magX < maxMag));

                do {
                    float delta = random.nextFloat() * (maxMagI - minMagI) + minMagI;
                    if (random.nextBoolean()) {
                        magY += delta;
                    } else {
                        magY -= delta;
                    }
                } while (!(minMag < magY  && magY < maxMag));

                do {
                    float delta = random.nextFloat() * (maxMagI - minMagI) + minMagI;
                    if (random.nextBoolean()) {
                        magZ += delta;
                    } else {
                        magZ -= delta;
                    }
                } while (!(minMag < magZ  && magZ < maxMag));

                row.setMagnetic(magX, magY, magZ);

                dbHandler.addPoint(row);
                System.out.println("        -   " + row.toString());
            }
        }
    }

    private void update(String userName) {
        dbHandler.setTable(userName);
        User user = userHandler.getUserInformation(userName);

        user.setTotal_distance(dbHandler.getDistance(dbHandler.getAll()));
        user.setTotal_time(dbHandler.getTime(dbHandler.getAll()));
        user.setHighest_speed(dbHandler.getGreatestValue(dbHandler.COLUMN_GPS_VEL).getVelocity());

        float acc = 0f;
        LinkedList<dbRow> list = dbHandler.getList(dbHandler.getAll());
        for (dbRow row : list) {
            float x = row.getAccelerometer_xValue();
            float y = row.getAccelerometer_yValue();
            float z = row.getAccelerometer_zValue();

            float c = (float) (Math.pow(x,2d) + Math.pow(y,2d) + Math.pow(z,2d));
            if (c > acc) {
                acc = c;
            }
        }
        user.setHighest_acceleration(acc);

        userHandler.overWrite(user);
    }
}
