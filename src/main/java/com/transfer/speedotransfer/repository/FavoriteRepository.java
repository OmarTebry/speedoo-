package com.transfer.speedotransfer.repository;

import com.transfer.speedotransfer.entity.Favorite;
import com.transfer.speedotransfer.entity.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findAllByUserId(Long userId);

    boolean existsByUserAndAccountNumber(User user, String accountNumber);
    void deleteById(Long favoriteId);
}
