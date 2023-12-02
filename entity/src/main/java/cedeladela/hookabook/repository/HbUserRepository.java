package cedeladela.hookabook.repository;


import cedeladela.hookabook.entity.HbUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HbUserRepository extends CrudRepository<HbUser, Long> {

}
