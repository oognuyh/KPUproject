package com.fleecyclouds.flipthetripover.sb.cardItem;

public class cardCourseItem
{
    public String thumbnail;
    public String title;
    public String addr1;
    public String overView;

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
        return overView;
    }

    public void setEventstartdate(String eventstartdate) {
        this.overView = eventstartdate;
    }


    public cardCourseItem(String thumbnail, String title, String addr1, String overView) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.addr1 = addr1;
        this.overView = overView;
    }
}
