package application.Services;

import application.Dto.CommentDto;

import java.util.List;

public interface ICommentService {

    public CommentDto add( Long  userId, Long productId, CommentDto commentDto);

    public void  delete(Long commentId);

    List<CommentDto> getByProduct(Long productId);
}
