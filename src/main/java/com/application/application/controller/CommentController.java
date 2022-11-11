package com.application.application.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.application.application.dto.CommentRequest;
import com.application.application.services.CommentService;

@Controller
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/list")
    public ResponseEntity<?> getComments(@RequestParam("id") long productId) {
        try {
            return ResponseEntity.ok().body(commentService.getComments(productId));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/user")
    public ResponseEntity<?> getCommentByUser(@RequestHeader String email) {
        try {
            return ResponseEntity.ok().body(commentService.getComments(email));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/add")
    public ResponseEntity<?> addComment(@RequestBody CommentRequest commentRequest) {
        try {
            URI uri = URI
                    .create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/comment/add").toUriString());
            commentService.addComment(commentRequest);
            return ResponseEntity.created(uri).build();

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
