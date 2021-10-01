package com.imgarena.coding.challenge.domain;

import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Tournament entity model.
 *
 * @author Elliot Ball
 */
@Entity
public class GolfTournament {

  @Id
  @GeneratedValue
  private long id;

  private String externalId;

  private String externalSource;

  private String courseName;

  private String name;

  private String countryCode;

  private LocalDate startDate;

  private LocalDate endDate;

  private int rounds;

  public GolfTournament(final long id, final String externalId, final String externalSource,
      final String courseName,
      final String name,
      final String countryCode, final LocalDate startDate, final LocalDate endDate,
      final int rounds) {
    this.id = id;
    this.externalId = externalId;
    this.externalSource = externalSource;
    this.courseName = courseName;
    this.name = name;
    this.countryCode = countryCode;
    this.startDate = startDate;
    this.endDate = endDate;
    this.rounds = rounds;
  }

  public GolfTournament() {
  }

  public long getId() {
    return id;
  }

  public void setId(final long id) {
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

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(final String countryCode) {
    this.countryCode = countryCode;
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

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final GolfTournament that = (GolfTournament) o;
    return id == that.id && rounds == that.rounds && Objects.equals(externalId,
        that.externalId) && Objects.equals(externalSource, that.externalSource)
        && Objects.equals(courseName, that.courseName) && Objects.equals(name,
        that.name) && Objects.equals(countryCode, that.countryCode)
        && Objects.equals(startDate, that.startDate) && Objects.equals(endDate,
        that.endDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, externalId, externalSource, courseName, name, countryCode, startDate,
        endDate, rounds);
  }

  @Override
  public String toString() {
    return "GolfTournament{" +
        "id=" + id +
        ", externalId='" + externalId + '\'' +
        ", externalSource='" + externalSource + '\'' +
        ", courseName='" + courseName + '\'' +
        ", name='" + name + '\'' +
        ", countryCode='" + countryCode + '\'' +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", rounds=" + rounds +
        '}';
  }
}


