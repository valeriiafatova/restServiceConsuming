package com.rest.consuming.command;

import com.rest.consuming.entity.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

@ShellComponent
public class ActivityCommand {
    private static final Logger log = LoggerFactory.getLogger(ActivityCommand.class);

    @Autowired
    RestTemplate restTemplate;

    @ShellMethod("Get Activity by Id")
    public String get(int id) {
        Activity activity = restTemplate.getForObject(
                "http://localhost:8080/activities/" + id, Activity.class);
        return activity.toString();
    }

    @ShellMethod("Delete Activity by Id")
    public void delete(int id) {
        restTemplate.delete("http://localhost:8080/activities/" + id);
    }
}
