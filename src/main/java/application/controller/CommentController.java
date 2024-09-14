package application.controller;

import application.Dto.CommentDto;
import application.Services.ICommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private ICommentService iCommentService;

    public CommentController(ICommentService iCommentService) {
        this.iCommentService = iCommentService;
    }

    @PostMapping("/create")
    public ResponseEntity<CommentDto> create(@RequestBody CommentDto commentDto) {
        CommentDto commentDto1= iCommentService.create(commentDto);
        return new ResponseEntity<>(commentDto1,HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        iCommentService.deleteById(id);
        return new ResponseEntity<>("the comment was deleted",HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id) {
        CommentDto commentDto = iCommentService.getCommentById(id);
        return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments() {
        List<CommentDto> comments = iCommentService.getAll();
        return new ResponseEntity<>(comments,HttpStatus.OK);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<CommentDto>> getCommentsByProductId(@PathVariable Long productId) {
        List<CommentDto> comments = iCommentService.getByProductId(productId);
        return new ResponseEntity<>(comments,HttpStatus.OK);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentDto>> getCommentsByUserId(@PathVariable Long userId) {
        List<CommentDto> comments = iCommentService.getByUserId(userId);
        return new ResponseEntity<>(comments,HttpStatus.OK);
    }

}