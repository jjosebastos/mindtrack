package br.com.fiap.mindtrack.form.mood;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HumorRepository extends JpaRepository<Humor, Long> {
}
