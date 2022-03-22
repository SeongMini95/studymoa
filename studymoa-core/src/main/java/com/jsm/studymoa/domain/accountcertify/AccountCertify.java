package com.jsm.studymoa.domain.accountcertify;

import com.jsm.studymoa.domain.BaseTimeEntity;
import com.jsm.studymoa.domain.account.Account;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

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

    public AccountCertify(Account account) throws NoSuchAlgorithmException {
        this.account = account;
        this.code = generateCode();
        this.isUse = false;
        this.expireDatetime = LocalDateTime.now().plusMinutes(30L);
    }

    private String generateCode() throws NoSuchAlgorithmException {
        String now = new Date().toString();
        String uuid = UUID.randomUUID().toString();

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        MessageDigest sha512 = MessageDigest.getInstance("SHA-512");

        md5.update(now.getBytes());
        sha512.update(uuid.getBytes());

        return DatatypeConverter.printHexBinary(md5.digest()) + DatatypeConverter.printHexBinary(sha512.digest());
    }

    public void used() {
        this.isUse = true;
    }
}
