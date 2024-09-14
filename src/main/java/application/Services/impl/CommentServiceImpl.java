package application.Services.impl;

import application.Dto.CommentDto;
import application.Repository.CommentRepository;
import application.Repository.ProductRepository;
import application.Repository.UserRepository;
import application.Services.ICommentService;
import application.exception.ResourceNotFoundException;
import application.model.Comment;
import application.model.Product;
import application.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements ICommentService {


    private CommentRepository commentRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    @Override
    public CommentDto create(CommentDto commentDto) {
        Product product = productRepository.findById(commentDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + commentDto.getProductId()));
        User user = userRepository.findById(commentDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + commentDto.getUserId()));

        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        comment.setDate(commentDto.getDate());
        comment.setProduct(product);
        comment.setUser(user);

        Comment Comment = commentRepository.save(comment);
        return convertToDto(Comment);

    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            commentRepository.deleteById(id);
            System.out.println("Comment deleted successfully");
        } else {
            throw new ResourceNotFoundException("Comment with ID " + id + " not found.");
        }
    }

    @Override
    public CommentDto getCommentById(Long id) {
        return Optional.ofNullable(commentRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with ID " + id + " not found.")));
    }

    @Override
    public List<CommentDto> getAll() {
        return commentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getByProductId(Long productId) {
        List<Comment> comments = commentRepository.findByProductId(productId);
        if (comments.isEmpty()) {
            throw new ResourceNotFoundException("No comments found for product with ID " + productId);
        }
        return comments.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getByUserId(Long userId) {
        List<Comment> comments = commentRepository.findByUserId(userId);
        if (comments.isEmpty()) {
            throw new ResourceNotFoundException("No comments found for user with ID " + userId);
        }
        return comments.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CommentDto convertToDto(Comment comment) {

        if (comment == null) {
            return null;
        }
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setDate(comment.getDate());
        return commentDto;
    }

    public Comment convertToEntity(CommentDto commentDto) {
        if (commentDto == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setText(commentDto.getText());
        comment.setDate(commentDto.getDate());
        return comment;

    }
}
