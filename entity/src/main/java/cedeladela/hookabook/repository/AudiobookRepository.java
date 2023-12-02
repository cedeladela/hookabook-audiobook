package cedeladela.hookabook.repository;


import cedeladela.hookabook.entity.Audiobook;
import cedeladela.hookabook.entity.HbUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudiobookRepository extends CrudRepository<Audiobook, Long> {

}
