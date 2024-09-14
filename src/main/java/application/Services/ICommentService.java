package application.Services;

import application.Dto.CommentDto;

import java.util.List;

public interface ICommentService {

    CommentDto create(CommentDto commentDto);
    void deleteById(Long id);

   CommentDto getCommentById(Long id);

    List<CommentDto> getAll();

    List<CommentDto> getByProductId(Long productId);

    List<CommentDto> getByUserId(Long userId);
}

