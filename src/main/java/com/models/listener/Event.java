package com.models.listener;

import java.util.function.Consumer;

public interface Event<T> {
    boolean addListener(Consumer<T> listener);

    void broadcast(T args);
}
