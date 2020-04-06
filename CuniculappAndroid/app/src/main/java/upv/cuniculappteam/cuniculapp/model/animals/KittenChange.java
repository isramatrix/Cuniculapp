package upv.cuniculappteam.cuniculapp.model.animals;

import java.util.Date;

import upv.cuniculappteam.cuniculapp.model.utils.Traceable;
import upv.cuniculappteam.cuniculapp.model.utils.TraceableCreator;

public class KittenChange extends Traceable
{
    public static final Creator<KittenChange> CREATOR = new TraceableCreator<>(KittenChange.class);

    private Integer id;

    private Integer maternalNestAmount;

    private Integer maternalFattenAmount;

    private Integer meatNestAmount;

    private Integer meatFattenAmount;

    private String reason;

    private Date date;

    private Kitten kitten;

    public KittenChange() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMaternalNestAmount() {
        return maternalNestAmount;
    }

    public void setMaternalNestAmount(Integer maternalNestAmount) {
        this.maternalNestAmount = maternalNestAmount;
    }

    public Integer getMaternalFattenAmount() {
        return maternalFattenAmount;
    }

    public void setMaternalFattenAmount(Integer maternalFattenAmount) {
        this.maternalFattenAmount = maternalFattenAmount;
    }

    public Integer getMeatNestAmount() {
        return meatNestAmount;
    }

    public void setMeatNestAmount(Integer meatNestAmount) {
        this.meatNestAmount = meatNestAmount;
    }

    public Integer getMeatFattenAmount() {
        return meatFattenAmount;
    }

    public void setMeatFattenAmount(Integer meatFattenAmount) {
        this.meatFattenAmount = meatFattenAmount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Kitten getKitten() {
        return kitten;
    }

    public void setKitten(Kitten kitten) {
        this.kitten = kitten;
    }
}
