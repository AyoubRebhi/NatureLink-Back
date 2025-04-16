package com.example.naturelink.Service;

import com.example.naturelink.Entity.Activity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IActivityService {
    List<Activity> getAllActivities();
    Optional<Activity> getActivityById(Integer id);
    Activity updateActivityWithImages(Integer id, Activity activityDetails, List<MultipartFile> imageFiles);
    void deleteActivity(Integer id);
    public Activity addActivityWithImages(Activity activity, List<MultipartFile> imageFiles);

    }
