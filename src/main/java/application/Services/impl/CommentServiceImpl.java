package application.Services.impl;

import application.Dto.CommentDto;
import application.Services.ICommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService {
    @Override
    public CommentDto add(Long userId, Long productId, CommentDto commentDto) {
        return null;
    }

    @Override
    public void delete(Long commentId) {

    }

    @Override
    public List<CommentDto> getByProduct(Long productId) {
        return null;
    }
}
