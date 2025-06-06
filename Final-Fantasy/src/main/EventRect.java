package main;

import java.awt.*;

public class EventRect extends Rectangle {

    private int eventRectDefaultX, eventRectDefaultY;
    private boolean eventDone = false;

    public boolean isEventDone() {
        return eventDone;
    }


    public int getEventRectDefaultX() {
        return eventRectDefaultX;
    }

    public void setEventRectDefaultX(int eventRectDefaultX) {
        this.eventRectDefaultX = eventRectDefaultX;
    }

    public int getEventRectDefaultY() {
        return eventRectDefaultY;
    }

    public void setEventRectDefaultY(int eventRectDefaultY) {
        this.eventRectDefaultY = eventRectDefaultY;
    }
}
