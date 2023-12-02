package cedeladela.hookabook.repository;


import cedeladela.hookabook.entity.Audiobook;
import cedeladela.hookabook.entity.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

}
