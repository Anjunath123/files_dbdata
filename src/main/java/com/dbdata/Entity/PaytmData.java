package com.dbdata.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.print.attribute.standard.Destination;
import java.sql.Date;

@Entity
@Table(name="paytm_transactions_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaytmData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String activity;
    private String source;
    private String WalletTxnID;
    private String comment;
    private String debit;
    private String credit;
    private String transactionBreakup;
    private String status;

}

