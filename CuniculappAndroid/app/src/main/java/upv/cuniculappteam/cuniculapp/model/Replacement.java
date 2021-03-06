package upv.cuniculappteam.cuniculapp.model;

import upv.cuniculappteam.cuniculapp.model.facilities.Farm;
import upv.cuniculappteam.cuniculapp.model.utils.Traceable;
import upv.cuniculappteam.cuniculapp.model.utils.TraceableCreator;

public class Replacement extends Traceable
{
    public static final Creator<Replacement> CREATOR = new TraceableCreator<>(Replacement.class);

    private Integer days;

    private Integer rabbitsAmount;

    private Integer[] jails;

    private String farm;

    public Replacement() { }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getRabbitsAmount() {
        return rabbitsAmount;
    }

    public void setRabbitsAmount(Integer rabbitsAmount) {
        this.rabbitsAmount = rabbitsAmount;
    }

    public Integer[] getJails() {
        return jails;
    }

    public void setJails(Integer[] jails) {
        this.jails = jails;
    }

    public String getFarm() {
        return farm;
    }

    public void setFarm(String farm) {
        this.farm = farm;
    }
}
