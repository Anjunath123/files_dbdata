package com.dbdata.Repository;

import com.dbdata.Entity.PaytmData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDataRepository extends JpaRepository<PaytmData,Long> {
}
