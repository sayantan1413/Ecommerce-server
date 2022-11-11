package com.application.application.services.Implementation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.application.dto.CommentDto;
import com.application.application.dto.CommentRequest;
import com.application.application.model.Comments;
import com.application.application.model.Product;
import com.application.application.model.User;
import com.application.application.repository.CommentDao;
import com.application.application.services.AuthenticationService;
import com.application.application.services.CommentService;
import com.application.application.services.ProductService;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ProductService productService;

    @Override
    public List<CommentDto> getComments(String email) {
        User user = authenticationService.authenticateUser(email);
        List<Comments> comments = commentDao.findByUser(user);
        return listcomment(comments);
    }

    private List<CommentDto> listcomment(List<Comments> comments) {
        List<CommentDto> commentList = new ArrayList<>();
        for (Comments comment : comments) {
            CommentDto commentDto = new CommentDto(comment.getUser().getCompanyName(), comment.getMessage(),
                    formatDate(comment.getCreatedDate()));
            commentList.add(commentDto);
        }
        return commentList;
    }

    @Override
    public void addComment(CommentRequest commentRequest) {
        User user = authenticationService.authenticateUser(commentRequest.getEmail());
        Product product = productService.getProductById(commentRequest.getProductId());
        Comments comments = new Comments(user, product, commentRequest.getMessage());
        commentDao.save(comments);
    }

    @Override
    public List<CommentDto> getComments(long productId) {
        Product product = productService.getProductById(productId);
        List<Comments> comments = commentDao.findByProduct(product);
        return listcomment(comments);
    }

    private String formatDate(LocalDateTime date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return dtf.format(date);
    }

}
