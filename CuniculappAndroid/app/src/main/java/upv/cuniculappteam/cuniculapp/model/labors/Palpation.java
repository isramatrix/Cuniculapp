package upv.cuniculappteam.cuniculapp.model.labors;

import java.util.Date;

import upv.cuniculappteam.cuniculapp.model.Cycle;
import upv.cuniculappteam.cuniculapp.model.Labor;
import upv.cuniculappteam.cuniculapp.model.utils.Traceable;
import upv.cuniculappteam.cuniculapp.model.utils.TraceableCreator;

public class Palpation extends Traceable
{
    public static final Creator<Palpation> CREATOR = new TraceableCreator<>(Palpation.class);

    private Date date;

    private String labor;

    private Integer pregnantRabbits;

    private String cycle;

    public Palpation() { }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLabor() {
        return labor;
    }

    public void setLabor(String labor) {
        this.labor = labor;
    }

    public Integer getPregnantRabbits() {
        return pregnantRabbits;
    }

    public void setPregnantRabbits(Integer pregnantRabbits) {
        this.pregnantRabbits = pregnantRabbits;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }
}
