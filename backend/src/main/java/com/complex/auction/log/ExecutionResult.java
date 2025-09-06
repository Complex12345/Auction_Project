package com.complex.auction.log;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.*;
import java.time.Duration;
import java.time.Instant;

@Entity
@Table(name = "execution_results")
public class ExecutionResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Instant startTime;

    @Column(nullable = false, updatable = false)
    private Instant endTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Lob
    @Column(name = "result_data")
    private String result;

    @Lob
    @Column(name = "error_details")
    private String error;

    public ExecutionResult() {
    }

    public ExecutionResult(Instant startTime, Instant endTime, Status status, String result, String error) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.result = result;
        this.error = error;
    }

    @Transient
    public Duration getDuration() {
        if (startTime == null || endTime == null) {
            return Duration.ZERO;
        }
        return Duration.between(startTime, endTime);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}


