package com.infratrack.infratech_backend.model;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * A construction project.
 *
 * Mapped to the "projects" collection in MongoDB. Field names here match
 * the Flutter app's Project model (lib/models/project.dart) field for
 * field, so that when the Flutter side is later wired up to call this API
 * directly, the JSON shapes line up with no extra translation step.
 */
@Document(collection = "projects")
public class Project {

    @Id
    private String id;

    @NotBlank(message = "name is required")
    private String name;

    private String description;

    private String location;

    /** User id of the customer this project belongs to. */
    private String clientId;

    /** User id of the project manager responsible for this project. */
    private String managerId;

    /** User ids of engineers assigned to this project. */
    private List<String> teamIds = List.of();

    private LocalDate startDate;

    private LocalDate expectedEndDate;

    @PositiveOrZero(message = "budget must not be negative")
    private double budget;

    @PositiveOrZero(message = "amountSpent must not be negative")
    private double amountSpent;

    /** Overall completion percentage, 0-100. */
    private int progress;

    /** One of: PLANNED, IN_PROGRESS, ON_HOLD, COMPLETED, CANCELLED. */
    private String status;

    private Instant createdAt;

    public Project() {
        // Required by Spring Data MongoDB for object mapping.
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public List<String> getTeamIds() {
        return teamIds;
    }

    public void setTeamIds(List<String> teamIds) {
        this.teamIds = teamIds;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(LocalDate expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getAmountSpent() {
        return amountSpent;
    }

    public void setAmountSpent(double amountSpent) {
        this.amountSpent = amountSpent;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
