package com.team13.trojancheckin_out.Layouts;

public class History {

    private String abbreviation;
    private String action;
    private String time;

    public History() {}

    public History(String abbreviation, String action, String time) {
        this.abbreviation = abbreviation;
        this.action = action;
        this.time = time;
    }

    /**
     * @return the abbreviation
     */
    public String getAbbreviation() { return this.abbreviation; }

    public void setAbbreviation(String a) { this.abbreviation = a; }

    /**
     * @return the action
     */
    public String getAction() { return this.action; }

    public void setAction(String a) { this.action = a; }

    /**
     * @return the time
     */
    public String getTime() { return this.time; }

    public void setTime(String a) { this.time = a; }
}
