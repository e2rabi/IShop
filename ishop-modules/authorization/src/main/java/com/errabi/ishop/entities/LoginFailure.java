package com.errabi.ishop.entities;

import com.errabi.ishop.entities.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginFailure {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id ;

    private String userName;

    @ManyToOne
    private User user;

    private String sourceIp ;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate ;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;
}
