package com.courseservice.course_service.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

  private final Cloudinary cloudinary;

  public CloudinaryService(Cloudinary cloudinary) {
    this.cloudinary = cloudinary;
  }

  public Map<String, String> uploadFile(MultipartFile file) throws IOException {
    return cloudinary
        .uploader()
        .upload(file.getBytes(), ObjectUtils.asMap("resource_type", "video"));
  }
}

// azure_video_indexer

//                "raw_convert",
//
// "azure_video_indexer","notification_url","https://ebc7-103-178-142-104.ngrok-free.app/upload/transcript"));
