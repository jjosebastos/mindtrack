package br.com.fiap.mindtrack.form.dashboard;

import br.com.fiap.mindtrack.form.mood.Humor;
import br.com.fiap.mindtrack.form.mood.HumorRepository;
import br.com.fiap.mindtrack.form.mood.HumorType; // Importe seu Enum
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DashboardService {

    private final HumorRepository humorRepository;

    public DashboardService(HumorRepository humorRepository) {
        this.humorRepository = humorRepository;
    }

    public DashboardDto getDashboardData(Long userId) {
        DashboardDto dto = new DashboardDto();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime ninetyDaysAgo = now.minusDays(90);
        LocalDateTime thirtyDaysAgo = now.minusDays(30);

        List<Humor> registros = humorRepository.findByUser_IdUserAndDataRegistroAfterOrderByDataRegistroAsc(userId, ninetyDaysAgo);

        dto.setTotalLogs(registros.size());

        long happyCount = registros.stream()
                .filter(h -> h.getHumorType() == HumorType.FELIZ)
                .count();
        dto.setDaysHappy(happyCount);

        dto.setCurrentStreak(calculateStreak(registros));
        List<Humor> ultimos30Dias = registros.stream()
                .filter(h -> h.getDataRegistro().isAfter(thirtyDaysAgo))
                .toList();

        // 6. Prepara Gráfico de Evolução
        List<String> labels = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

        for (Humor h : ultimos30Dias) {
            labels.add(h.getDataRegistro().format(formatter)); // Ajustado para dataRegistro
            values.add(convertHumorToInt(h.getHumorType()));   // Passando o Enum
        }
        dto.setEvolutionLabels(labels);
        dto.setEvolutionValues(values);

        long countFeliz = registros.stream().filter(h -> h.getHumorType() == HumorType.FELIZ).count();
        long countNeutro = registros.stream().filter(h -> h.getHumorType() == HumorType.NEUTRO).count();
        long countTriste = registros.stream().filter(h -> h.getHumorType() == HumorType.TRISTE).count();
        long countRaiva = registros.stream().filter(h -> h.getHumorType() == HumorType.RAIVA).count();

        dto.setQuarterlyCounts(List.of(countFeliz, countNeutro, countTriste, countRaiva));

        return dto;
    }

    private int convertHumorToInt(HumorType tipo) {
        if (tipo == null) return 0;

        return switch (tipo) {
            case FELIZ -> 4;
            case NEUTRO -> 3;
            case TRISTE -> 2;
            case RAIVA -> 1;
            default -> 0;
        };
    }

    private long calculateStreak(List<Humor> registros) {
        if (registros.isEmpty()) return 0;

        List<LocalDate> datasUnicas = registros.stream()
                .map(h -> h.getDataRegistro().toLocalDate())
                .distinct()
                .sorted(Comparator.reverseOrder())
                .toList();

        long streak = 0;
        LocalDate checarData = LocalDate.now();

        if (!datasUnicas.contains(checarData)) {
            checarData = checarData.minusDays(1);
        }

        for (LocalDate data : datasUnicas) {
            if (data.equals(checarData)) {
                streak++;
                checarData = checarData.minusDays(1);
            } else {
                break;
            }
        }
        return streak;
    }
}