package bjason.swagger.entity;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "tictactoe", path = "tictactoe")
public interface TicTacToe extends PagingAndSortingRepository<TicTacToeEntity,Long> {

}

