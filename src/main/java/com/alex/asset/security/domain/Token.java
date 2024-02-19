package com.alex.asset.security.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "token")
public class Token {

  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  public UUID id;

  @CreatedDate
  public LocalDateTime created;

  @LastModifiedDate
  public LocalDateTime expired;


  @OneToOne(fetch = FetchType.EAGER)
  public User user;



}
