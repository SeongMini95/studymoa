package com.jsm.studymoa.domain.accountcertify;

import com.jsm.studymoa.domain.BaseTimeEntity;
import com.jsm.studymoa.domain.account.Account;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Entity
@DynamicUpdate
@Table(name = "account_certify")
public class AccountCertify extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "code", nullable = false, length = 160)
    private String code;

    @Column(name = "is_use", nullable = false)
    private boolean isUse;

    @Column(name = "expire_datetime", nullable = false)
    private LocalDateTime expireDatetime;

    @Builder
    public AccountCertify(Long id, Account account, String code, boolean isUse, LocalDateTime expireDatetime) {
        this.id = id;
        this.account = account;
        this.code = code;
        this.isUse = isUse;
        this.expireDatetime = expireDatetime;
    }
}
