package ru.startandroid.currencies.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Valute", strict = false)
public class Valute implements Parcelable{


    @Element(required = false, name = "CharCode")
    private String charCode;

    @Element(required = true, name = "Value")
    private String value;

    public Valute(Parcel source) {
        charCode = source.readString();
        value = source.readString();
    }

    public Valute(){}

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(charCode);
        dest.writeString(value);
    }

    public static Parcelable.Creator<Valute> CREATOR = new Creator<Valute>() {
        @Override
        public Valute createFromParcel(Parcel source) {
            return new Valute(source);
        }

        @Override
        public Valute[] newArray(int size) {
            return new Valute[size];
        }
    };
}
