package ru.startandroid.currencies.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict = false)
public class Response {

    @Attribute(name = "Date")
    private String date;



    @ElementList(required = false, name = "Valute", inline = true)
    private List<Valute> valutes;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public List<Valute> getValutes() {
        return valutes;
    }

    public void setValutes(List<Valute> valutes) {
        this.valutes = valutes;
    }
}
