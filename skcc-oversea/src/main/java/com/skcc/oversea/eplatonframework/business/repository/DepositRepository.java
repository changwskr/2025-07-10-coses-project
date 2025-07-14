package com.skcc.oversea.eplatonframework.business.repository;

import com.skcc.oversea.eplatonframework.business.entity.Deposit;
import com.skcc.oversea.eplatonframework.business.entity.DepositPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, DepositPK> {

        /**
         * Find deposit by primary key
         */
        Optional<Deposit> findByPrimaryKey(DepositPK primaryKey);

        /**
         * Find deposits by account number
         */
        @Query("SELECT d FROM Deposit d WHERE d.accountNumber = :accountNumber")
        List<Deposit> findByAccountNumber(@Param("accountNumber") String accountNumber);

        /**
         * Find deposits by customer ID
         */
        @Query("SELECT d FROM Deposit d WHERE d.customerId = :customerId")
        List<Deposit> findByCustomerId(@Param("customerId") String customerId);

        /**
         * Find deposits by branch code
         */
        @Query("SELECT d FROM Deposit d WHERE d.branchCode = :branchCode")
        List<Deposit> findByBranchCode(@Param("branchCode") String branchCode);

        /**
         * Find deposits by deposit type
         */
        @Query("SELECT d FROM Deposit d WHERE d.depositType = :depositType")
        List<Deposit> findByDepositType(@Param("depositType") String depositType);

        /**
         * Find active deposits
         */
        @Query("SELECT d FROM Deposit d WHERE d.isActive = true")
        List<Deposit> findActiveDeposits();

        /**
         * Create a new deposit
         */
        default Deposit create(String accountNumber, String customerId, String branchCode,
                        String depositType, double amount, String currency) {
                DepositPK pk = new DepositPK(accountNumber, customerId);
                Deposit deposit = new Deposit(pk);
                deposit.setBranchCode(branchCode);
                deposit.setDepositType(depositType);
                deposit.setAmount(amount);
                deposit.setCurrency(currency);
                deposit.setIsActive(true);
                return save(deposit);
        }

        /**
         * Find deposit by account number
         */
        @Query("SELECT d FROM Deposit d WHERE d.primaryKey.accountNumber = :accountNumber")
        Optional<Deposit> findByAccountNo(@Param("accountNumber") String accountNumber);

        /**
         * Find deposits by account status
         */
        @Query("SELECT d FROM Deposit d WHERE d.isActive = :isActive")
        List<Deposit> findByAccountStatus(@Param("isActive") Boolean isActive);

        /**
         * Find deposits by account type
         */
        @Query("SELECT d FROM Deposit d WHERE d.depositType = :accountType")
        List<Deposit> findByAccountType(@Param("accountType") String accountType);

        /**
         * Sum balance by customer ID
         */
        @Query("SELECT COALESCE(SUM(d.amount), 0) FROM Deposit d WHERE d.primaryKey.customerId = :customerId AND d.isActive = true")
        BigDecimal sumBalanceByCustomerId(@Param("customerId") String customerId);

        /**
         * Find matured deposits
         */
        @Query("SELECT d FROM Deposit d WHERE d.maturityDate <= :maturityDate AND d.isActive = true")
        List<Deposit> findMaturedDeposits(@Param("maturityDate") LocalDateTime maturityDate);
}