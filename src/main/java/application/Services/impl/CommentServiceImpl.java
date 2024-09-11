package application.Services.impl;

import application.Dto.CommentDto;
import application.Repository.CommentRepository;
import application.Repository.ProductRepository;
import application.Repository.UserRepository;
import application.Services.ICommentService;
import application.model.Comment;
import application.model.Product;
import application.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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

    @Override
    public CommentDto create(CommentDto commentDto) {
        Product product = productRepository.findById(commentDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + commentDto.getProductId()));
        User user = userRepository.findById(commentDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + commentDto.getUserId()));

        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        comment.setDate(commentDto.getDate());
        comment.setProduct(product);
        comment.setUser(user);

        Comment Comment = commentRepository.save(comment);
        return convertToDto(Comment);

    }

    @Override
    public void deleteById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            commentRepository.deleteById(id);
            System.out.println("Comment deleted successfully");
        } else {
            System.out.println("Comment not found َََ");
        }
    }

    @Override
    public Optional<CommentDto> getCommentById(Long id) {
        return commentRepository.findById(id).map(this::convertToDto);
    }

    @Override
    public List<CommentDto> getAll() {
        return commentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getByProductId(Long productId) {
        return commentRepository.findByProductId(productId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getByUserId(Long userId) {
        return commentRepository.findByUserId(userId).stream()
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
