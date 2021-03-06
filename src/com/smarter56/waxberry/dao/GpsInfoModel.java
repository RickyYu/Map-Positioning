package com.smarter56.waxberry.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table GPS_INFO_MODEL.
 */
public class GpsInfoModel {

    private Double lat;
    private Double lon;
    private Long updateTime;
    private String uploadTime;
    private String vehicleNo;
    private String placeName;
    private Float speed;
    private Float direction;
    private Integer totalKM;

    public GpsInfoModel() {
    }

    public GpsInfoModel(Double lat, Double lon, Long updateTime, String uploadTime, String vehicleNo, String placeName, Float speed, Float direction, Integer totalKM) {
        this.lat = lat;
        this.lon = lon;
        this.updateTime = updateTime;
        this.uploadTime = uploadTime;
        this.vehicleNo = vehicleNo;
        this.placeName = placeName;
        this.speed = speed;
        this.direction = direction;
        this.totalKM = totalKM;
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

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
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

    public Integer getTotalKM() {
        return totalKM;
    }

    public void setTotalKM(Integer totalKM) {
        this.totalKM = totalKM;
    }

	@Override
	public String toString() {
		return "GpsInfoModel [lat=" + lat + ", lon=" + lon + ", updateTime="
				+ updateTime + ", uploadTime=" + uploadTime + ", vehicleNo="
				+ vehicleNo + ", placeName=" + placeName + ", speed=" + speed
				+ ", direction=" + direction + ", totalKM=" + totalKM + "]";
	}


}
