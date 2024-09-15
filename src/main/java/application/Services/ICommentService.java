package application.Services;

import application.Dto.CommentDto;

import java.util.List;
import java.util.Optional;

public interface ICommentService {

    CommentDto create(CommentDto commentDto);
    void deleteById(Long id);

   Optional<CommentDto> getCommentById(Long id);

    List<CommentDto> getAll();

    List<CommentDto> getByProductId(Long productId);

    List<CommentDto> getByUserId(Long userId);
}

