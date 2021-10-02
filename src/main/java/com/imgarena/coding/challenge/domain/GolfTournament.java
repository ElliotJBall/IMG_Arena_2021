package com.imgarena.coding.challenge.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Tournament entity model.
 *
 * @author Elliot Ball
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class GolfTournament {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String externalId;

  private String externalSource;

  private String courseName;

  private String name;

  private String countryName;

  private LocalDate startDate;

  private LocalDate endDate;

  private int rounds;

  @CreatedDate
  private LocalDateTime createdDate;

  @LastModifiedDate
  private LocalDateTime lastModifiedDate;

  public GolfTournament(final Long id, final String externalId, final String externalSource,
      final String courseName, final String name, final String countryName,
      final LocalDate startDate,
      final LocalDate endDate, final int rounds, final LocalDateTime createdDate,
      final LocalDateTime lastModifiedDate) {
    this.id = id;
    this.externalId = externalId;
    this.externalSource = externalSource;
    this.courseName = courseName;
    this.name = name;
    this.countryName = countryName;
    this.startDate = startDate;
    this.endDate = endDate;
    this.rounds = rounds;
    this.createdDate = createdDate;
    this.lastModifiedDate = lastModifiedDate;
  }

  public GolfTournament() {

  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getExternalId() {
    return externalId;
  }

  public void setExternalId(final String externalId) {
    this.externalId = externalId;
  }

  public String getExternalSource() {
    return externalSource;
  }

  public void setExternalSource(final String externalSource) {
    this.externalSource = externalSource;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(final String courseName) {
    this.courseName = courseName;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getCountryName() {
    return countryName;
  }

  public void setCountryName(final String countryName) {
    this.countryName = countryName;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(final LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(final LocalDate endDate) {
    this.endDate = endDate;
  }

  public int getRounds() {
    return rounds;
  }

  public void setRounds(final int rounds) {
    this.rounds = rounds;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(final LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public LocalDateTime getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(final LocalDateTime lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  // Based on https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final GolfTournament that = (GolfTournament) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  @Override
  public String toString() {
    return "GolfTournament{" +
        "id=" + id +
        ", externalId='" + externalId + '\'' +
        ", externalSource='" + externalSource + '\'' +
        ", courseName='" + courseName + '\'' +
        ", name='" + name + '\'' +
        ", countryName='" + countryName + '\'' +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", rounds=" + rounds +
        ", createdDate=" + createdDate +
        ", lastModifiedDate=" + lastModifiedDate +
        '}';
  }
}


