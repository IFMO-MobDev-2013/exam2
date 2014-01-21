package ru.marsermd.Exam2;

/**
 * Created with IntelliJ IDEA.
 * User: misch_000
 * Date: 14.01.14
 * Time: 15:31
 * To change this template use File | Settings | File Templates.
 */
public class PizzeriaModel {
    private static PizzeriaModel ourInstance = new PizzeriaModel();

    public static PizzeriaModel getInstance() {
        return ourInstance;
    }

    private PizzeriaModel() {
    }

    private static int couriersCount = 0;
    public static int getCouriersCount() {
        return couriersCount;
    }
    public static void setCouriersCount(int count) {
        couriersCount = count;
    }

    private static String name = "";
    public static String getName() {
        return name;
    }
    public static void setName(String pizzeriaName) {
        name = pizzeriaName;
    }

    public static String getFormatedTime(int time) {
        String ans = "" + (time / 2);
        if (ans.length() == 1)
            ans = 0 + ans;
        ans += " : " + (time % 2 == 0 ? "00" : "30");
        return ans;
    }
    public static String getFormatedTimeMinutes(int time) {
        String ans = time * 30 + " min";
        return ans;
    }

    public static int deformatTime(String time) {
        String[] s = time.split(" : ");
        return Integer.parseInt(s[0]) * 2 + Integer.parseInt(s[1]) / 30;
    }

    private static final int START_TIME = 20, END_TIME = 48;
    public static boolean isWorkingTime(int time) {
        return time >= START_TIME && time <= END_TIME;
    }

    public static boolean isWorkingTime(int time1, int time2) {
        return isWorkingTime(time1) && isWorkingTime(time2) && time1 < time2;
    }
}
