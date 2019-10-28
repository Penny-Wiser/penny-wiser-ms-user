package com.penniless.springboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Document(collection = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private ObjectId id;
  private String externalId;
  private String email;
  private String firstName;
  private String lastName;
  private String password;
}
