package net.cgro.votemanager.model;

import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;

public class Urne
{
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty nummer;
    private final SimpleStringProperty status;

    public Urne(String name, int nummer){
        this.name = new SimpleStringProperty(name);
        this.nummer = new SimpleIntegerProperty(nummer);
        this.status = new SimpleStringProperty();
        this.updateStatus();
    }
    
    private Urne()
    {
        this.name = new SimpleStringProperty();
        this.nummer = new SimpleIntegerProperty();
        this.status = new SimpleStringProperty();
        this.updateStatus();
    }
    
    @XmlID
    @XmlAttribute
    public String getName()
    {
        return name.get();
    }

    public void setName(String name)
    {
        this.name.set(name);
    }
    
    @XmlAttribute
    public int getNummer()
    {
        return nummer.get();
    }
    
    public void setNummer(int nummer)
    {
        this.nummer.set(nummer);
    }
    
    public void updateStatus()
    {
        if(!Wahl.getInstance().existiertErgebnis(this))
        {
            status.set("Kein Ergebnis");
            return;
        }
        
        ArrayList<CheckResult> checks = new ArrayList<>();
        boolean result = Wahl.getInstance().getErgebnis(this).doChecks(checks);
        
        if(result == true)
        {
            status.set("Ergebnis existiert - OK");
        }
        else
        {
            status.set("Ergebnis existiert - FEHLER GEFUNDEN");
        }
    }
    
    public SimpleStringProperty statusProperty()
    {
        return status;
    }
}