package org.technischools.shop.service;

import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EventLogService {

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    private final List<String> actions = new ArrayList<>();

    public void log(String source, String message) {
        String action = String.format("[%s] %-35s → %s", LocalTime.now().format(FMT), source, message);
        actions.add(action);
        System.out.println("[LOG] " + action);
    }

    public List<String> getActions() {
        return Collections.unmodifiableList(actions);
    }

    public void clear() {
        actions.clear();
    }
}
