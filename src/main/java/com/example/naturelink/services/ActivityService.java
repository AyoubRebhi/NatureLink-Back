package com.example.naturelink.services;

import com.example.naturelink.entity.Activity;
import com.example.naturelink.repository.IActivityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService implements IActivityService {

    private final IActivityRepository activityRepository;

    public ActivityService(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    @Override
    public Optional<Activity> getActivityById(Integer id) {
        return activityRepository.findById(Long.valueOf(id));
    }

    @Override
    public Activity addActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public Activity updateActivity(Integer id, Activity activityDetails) {
        return activityRepository.findById(Long.valueOf(id)).map(activity -> {
            activity.setName(activityDetails.getName());
            activity.setDescription(activityDetails.getDescription());
            activity.setProviderId(activityDetails.getProviderId());
            activity.setLocation(activityDetails.getLocation());
            activity.setDuration(activityDetails.getDuration());
            activity.setPrice(activityDetails.getPrice());
            activity.setMaxParticipants(activityDetails.getMaxParticipants());
            activity.setDifficultyLevel(activityDetails.getDifficultyLevel());
            activity.setRequiredEquipment(activityDetails.getRequiredEquipment());
            return activityRepository.save(activity);
        }).orElseThrow(() -> new RuntimeException("Activity not found"));
    }

    @Override
    public void deleteActivity(Integer id) {
        activityRepository.deleteById(Long.valueOf(id));
    }
}