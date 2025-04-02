package com.example.naturelink.services;

import com.example.naturelink.entity.Activity;

import java.util.List;
import java.util.Optional;

public interface IActivityService {
    List<Activity> getAllActivities();
    Optional<Activity> getActivityById(Integer id);
    Activity addActivity(Activity activity);
    Activity updateActivity(Integer id, Activity activityDetails);
    void deleteActivity(Integer id);
}