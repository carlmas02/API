package com.courseservice.course_service.service;

import java.util.concurrent.CompletableFuture;

public interface UploadService {

  public CompletableFuture<String> performTranscript(String url);
}
