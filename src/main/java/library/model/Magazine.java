package library.model;

import java.time.MonthDay;
import java.util.Objects;

public class Magazine extends Publication {
    public static final String TYPE = "Magazyn";
    private MonthDay monthDay;
    private String lanaguage;

    public Magazine(String title, String publisher, int month, int day, String lanaguage, int year) {
        super(title, publisher, year);
        this.lanaguage = lanaguage;
        this.monthDay = MonthDay.of(month,day);
    }

    public MonthDay getMonthDay() {
        return monthDay;
    }

    public void setMonthDay(MonthDay monthDay) {
        this.monthDay = monthDay;
    }

    public String getLanaguage() {
        return lanaguage;
    }

    public void setLanaguage(String lanaguage) {
        this.lanaguage = lanaguage;
    }

    @Override
    public String toCsv() {
        return TYPE + ";"
                + getTitle() + ";"
                + getPublisher() + ";"
                + getYear() + ";"
                + monthDay.getMonthValue() + ";"
                + monthDay.getDayOfMonth() + ";"
                + lanaguage;
    }

    @Override
    public String toString() {
        return super.toString() + "; " + monthDay.getMonthValue() + "; " + monthDay.getDayOfMonth() + "; " + lanaguage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Magazine)) return false;
        if (!super.equals(o)) return false;
        Magazine magazine = (Magazine) o;
        return Objects.equals(monthDay, magazine.monthDay) &&
                Objects.equals(lanaguage, magazine.lanaguage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), monthDay, lanaguage);
    }
}
