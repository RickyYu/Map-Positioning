package com.smarter56.waxberry.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table GPS_INFO_MODEL.
 */
public class GpsInfoModel {

    private Double lat;
    private Double lon;
    private String updateTime;
    private String vehicleNo;
    private String placeName;
    private Float speed;
    private Float direction;

    public GpsInfoModel() {
    }

    public GpsInfoModel(Double lat, Double lon, String updateTime, String vehicleNo, String placeName, Float speed, Float direction) {
        this.lat = lat;
        this.lon = lon;
        this.updateTime = updateTime;
        this.vehicleNo = vehicleNo;
        this.placeName = placeName;
        this.speed = speed;
        this.direction = direction;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public Float getDirection() {
        return direction;
    }

    public void setDirection(Float direction) {
        this.direction = direction;
    }

	@Override
	public String toString() {
		return "GpsInfoModel [lat=" + lat + ", lon=" + lon + ", updateTime="
				+ updateTime + ", vehicleNo=" + vehicleNo + ", placeName="
				+ placeName + ", speed=" + speed + ", direction=" + direction
				+ "]";
	}


}
