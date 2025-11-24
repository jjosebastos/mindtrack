package br.com.fiap.mindtrack.form.dashboard;

import lombok.Data;

import java.util.List;

@Data
public class DashboardDto {
    private List<Integer> evolutionValues;
    private List<String> evolutionLabels;
    private List<Long> quarterlyCounts;
    private long daysHappy;
    private long totalLogs;
    private long currentStreak;
}