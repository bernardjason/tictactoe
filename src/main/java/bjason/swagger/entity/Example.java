package bjason.swagger.entity;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "example", path = "example")
public interface Example extends PagingAndSortingRepository<ExampleEntity,Long> {

}

