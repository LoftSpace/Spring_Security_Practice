package moin.demo.repository;

import moin.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByUserId(String userId);
    @Modifying
    @Query("UPDATE User u SET u.todayTransferCount = u.todayTransferCount + 1, u.todayTransferUsdAmount = u.todayTransferUsdAmount + :sourceAmount WHERE u.id = :userId")
    void updateTransferCountAndAmount(@Param("userId") String userId, @Param("sourceAmount") Long sourceAmount);


}
