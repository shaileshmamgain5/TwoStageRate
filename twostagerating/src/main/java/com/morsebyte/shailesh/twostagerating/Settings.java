package com.morsebyte.shailesh.twostagerating;

/**
 * Created by GuestHouser on 2/29/16.
 */
public class Settings {
    private int launchTimes = 10;

    private int installDays = 5;

    private int eventsTimes = -1;

    float thresholdRating=3;

    public enum StoreType {
        GOOGLEPLAY,
        AMAZON
    }
    StoreType storeType = StoreType.GOOGLEPLAY;

    public void setInstallDays( int installDays)
    {
        this.installDays = installDays;
    }
    public int getInstallDays()
    {
        return this.installDays;
    }
    public void setLaunchTimes( int launchTimes)
    {
        this.launchTimes = launchTimes;
    }
    public int getLaunchTimes()
    {
        return this.launchTimes;
    }
    public void setEventsTimes( int eventsTimes)
    {
        this.eventsTimes = eventsTimes;
    }
    public int getEventsTimes()
    {
        return this.eventsTimes;
    }

    public void setThresholdRating( float thresholdRating)
    {
        this.thresholdRating = thresholdRating;
    }
    public float getThresholdRating()
    {
        return this.thresholdRating;
    }
    public void setStoreType(StoreType storeType)
    {
        this.storeType =storeType;
    }
    public StoreType getStoreType( )
    {
        return this.storeType;
    }





}
