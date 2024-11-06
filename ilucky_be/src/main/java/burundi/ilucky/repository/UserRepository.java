package burundi.ilucky.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import burundi.ilucky.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);

    @Query(value = "FROM User u WHERE u.totalStar IN (SELECT DISTINCT u2.totalStar FROM User u2 ORDER BY u2.totalStar DESC LIMIT :topNumber) ORDER BY u.totalStar DESC")
    List<User> findTopUsersByTotalStar(@Param("topNumber") int topNumber);
}
