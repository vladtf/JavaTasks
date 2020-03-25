package com.models.listener;

import java.util.function.Consumer;

public interface Event<T> {
    void addListener(Consumer<T> listener);

    void broadcast(T args);
}
