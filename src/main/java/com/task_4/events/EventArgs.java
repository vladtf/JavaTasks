package com.task_4.events;

public class EventArgs {
    private Object value;

    public EventArgs() {
    }

    public EventArgs(Object value) {

        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
