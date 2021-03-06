package com.ox3dr.services.android.lib.drone.property;

import android.os.Parcel;
import android.os.Parcelable;

import com.ox3dr.services.android.lib.coordinate.LatLng;

/**
 * Stores GPS information.
 */
public class Gps implements Parcelable {

    public static final String LOCK_2D = "2D";
    public static final String LOCK_3D = "3D";
    public static final String NO_FIX = "NoFix";

    private final static int LOCK_2D_TYPE = 2;
    private final static int LOCK_3D_TYPE = 3;

    private final float mGpsEph;
    private final int mSatCount;
    private final int mFixType;
    private final LatLng mPosition;

    public Gps(LatLng position, float gpsEph, int satCount, int fixType){
        mPosition = position;
        mGpsEph = gpsEph;
        mSatCount = satCount;
        mFixType = fixType;
    }

    public Gps(float latitude, float longitude, float gpsEph, int satCount, int fixType){
        this(new LatLng(latitude, longitude), gpsEph, satCount, fixType);
    }

    public boolean isValid(){
        return mPosition != null;
    }

    public float getGpsEph(){
        return mGpsEph;
    }

    public int getSatellitesCount(){
        return mSatCount;
    }

    public int getFixType(){
        return mFixType;
    }

    public String getFixStatus(){
        switch (mFixType) {
            case LOCK_2D_TYPE:
                return LOCK_2D;

            case LOCK_3D_TYPE:
                return LOCK_3D;

            default:
                return NO_FIX;
        }
    }

    public LatLng getPosition(){
        return mPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gps)) return false;

        Gps gps = (Gps) o;

        if (mFixType != gps.mFixType) return false;
        if (Float.compare(gps.mGpsEph, mGpsEph) != 0) return false;
        if (mSatCount != gps.mSatCount) return false;
        return !(mPosition != null ? !mPosition.equals(gps.mPosition) : gps.mPosition != null);
    }

    @Override
    public int hashCode() {
        int result = (mGpsEph != +0.0f ? Float.floatToIntBits(mGpsEph) : 0);
        result = 31 * result + mSatCount;
        result = 31 * result + mFixType;
        result = 31 * result + (mPosition != null ? mPosition.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Gps{" +
                "mGpsEph=" + mGpsEph +
                ", mSatCount=" + mSatCount +
                ", mFixType=" + mFixType +
                ", mPosition=" + mPosition +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.mGpsEph);
        dest.writeInt(this.mSatCount);
        dest.writeInt(this.mFixType);
        dest.writeParcelable(this.mPosition, 0);
    }

    private Gps(Parcel in) {
        this.mGpsEph = in.readFloat();
        this.mSatCount = in.readInt();
        this.mFixType = in.readInt();
        this.mPosition = in.readParcelable(LatLng.class.getClassLoader());
    }

    public static final Parcelable.Creator<Gps> CREATOR = new Parcelable.Creator<Gps>() {
        public Gps createFromParcel(Parcel source) {
            return new Gps(source);
        }

        public Gps[] newArray(int size) {
            return new Gps[size];
        }
    };
}
