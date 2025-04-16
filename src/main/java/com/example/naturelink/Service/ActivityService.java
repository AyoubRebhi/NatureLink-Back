package com.example.naturelink.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.naturelink.Entity.Activity;
import com.example.naturelink.Repository.IActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ActivityService implements IActivityService {

    private final IActivityRepository activityRepository;
    @Autowired
    private Cloudinary cloudinary;

    public ActivityService(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    @Override
    public Optional<Activity> getActivityById(Integer id) {
        return activityRepository.findById(Long.valueOf(id));
    }

    @Override
    public void deleteActivity(Integer id) {
        activityRepository.deleteById(Long.valueOf(id));
    }
    public Activity addActivityWithImages(Activity activity, List<MultipartFile> imageFiles) {
        List<String> uploadedImageUrls = new ArrayList<>();
        for (MultipartFile file : imageFiles) {
            try {
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                uploadedImageUrls.add((String) uploadResult.get("secure_url"));
            } catch (Exception e) {
                throw new RuntimeException("Failed to upload image", e);
            }
        }
        activity.setImageUrls(uploadedImageUrls);
        return activityRepository.save(activity);
    }
    public Activity updateActivityWithImages(Integer id, Activity activityDetails, List<MultipartFile> imageFiles) {
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
            activity.setMood(activityDetails.getMood());
            activity.setType(activityDetails.getType());
            activity.setTags(activityDetails.getTags());

            // 🔁 Preserve existing image URLs (passed from frontend)
            List<String> existingImageUrls = activityDetails.getImageUrls() != null
                    ? new ArrayList<>(activityDetails.getImageUrls())
                    : new ArrayList<>();

            // ➕ Upload new images if any and add to the list
            if (imageFiles != null && !imageFiles.isEmpty()) {
                for (MultipartFile file : imageFiles) {
                    try {
                        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                        existingImageUrls.add((String) uploadResult.get("secure_url"));
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to upload image", e);
                    }
                }
            }

            activity.setImageUrls(existingImageUrls);
            return activityRepository.save(activity);
        }).orElseThrow(() -> new RuntimeException("Activity not found"));
    }


}
