package cedeladela.hookabook.repository;


import cedeladela.hookabook.entity.Category;
import cedeladela.hookabook.entity.HbUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

}
