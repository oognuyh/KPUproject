package com.fleecyclouds.flipthetripover.sb.cardItem;

public class cardDetailItem {
    public String thumbnail;
    public String title;
    public String addr1;
    public String eventstartdate;
    public String contentId;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getEventstartdate() {
        return eventstartdate;
    }

    public void setEventstartdate(String eventstartdate) {
        this.eventstartdate = eventstartdate;
    }


    public cardDetailItem(String thumbnail, String title, String addr1, String eventstartdate,String contentId) {
        this.thumbnail = thumbnail;
        this.contentId =contentId;
        this.title = title;
        this.addr1 = addr1;
        this.eventstartdate = eventstartdate;
    }
}
