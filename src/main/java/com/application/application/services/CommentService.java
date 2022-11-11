package com.application.application.services;

import java.util.List;

import com.application.application.dto.CommentDto;
import com.application.application.dto.CommentRequest;

public interface CommentService {
    public List<CommentDto> getComments(long productId);

    public List<CommentDto> getComments(String email);

    public void addComment(CommentRequest commentRequest);
}
