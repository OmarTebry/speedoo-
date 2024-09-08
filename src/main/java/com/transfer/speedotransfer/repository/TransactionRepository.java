package com.transfer.speedotransfer.repository;

import com.transfer.speedotransfer.entity.Transaction;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    //@Query("SELECT t FROM Transaction t WHERE t.user.id = :userId ORDER BY t.transactionDate DESC")
    //List<Transaction> findAllByUserId(Long userId);
    // Custom query method to find transactions where user is either sender or recipient, sorted by date
    List<Transaction> findAllBySenderAccountNumberInOrRecipientAccountNumberIn(Set<String> senderAccountNumbers, Set<String> recipientAccountNumbers, Sort sort);
}
