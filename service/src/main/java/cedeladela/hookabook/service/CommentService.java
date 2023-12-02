package cedeladela.hookabook.service;

import cedeladela.hookabook.entity.Comment;
import cedeladela.hookabook.repository.CommentRepository;
import exception.comment.CommentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAll() {
        return (List<Comment>) commentRepository.findAll();
    }

    public Comment getById(Long id) {
        Optional<Comment> existingCommentOptional = commentRepository.findById(id);
        if (existingCommentOptional.isPresent()) {
            return existingCommentOptional.get();
        } else {
            throw new CommentNotFoundException(id);
        }
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment update(Comment comment) {
        Optional<Comment> existingCommentOptional = commentRepository.findById(comment.getId());
        if (existingCommentOptional.isPresent()) {
            Comment existingComment = existingCommentOptional.get();
            existingComment.setCommentText(comment.getCommentText());
            existingComment.setCommentDate(comment.getCommentDate());
            // Update user and audiobook if necessary
            if (comment.getUser() != null) {
                existingComment.setUser(comment.getUser());
            }
            if (comment.getAudiobook() != null) {
                existingComment.setAudiobook(comment.getAudiobook());
            }
            return commentRepository.save(existingComment);
        } else {
            throw new CommentNotFoundException(comment.getId());
        }
    }

    public void delete(Long id) {
        Optional<Comment> existingCommentOptional = commentRepository.findById(id);
        if (existingCommentOptional.isPresent()) {
            commentRepository.deleteById(id);
        } else {
            throw new CommentNotFoundException(id);
        }
    }
}
