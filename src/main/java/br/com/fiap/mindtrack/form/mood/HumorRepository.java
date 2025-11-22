package br.com.fiap.mindtrack.form.mood;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HumorRepository extends JpaRepository<Humor, Long>, JpaSpecificationExecutor<Humor> {

    Page<Humor> findByUser_IdUser(Long idUser, Pageable pageable);
}
