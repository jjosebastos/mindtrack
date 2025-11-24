package br.com.fiap.mindtrack.form.mood;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HumorRepository extends JpaRepository<Humor, Long>, JpaSpecificationExecutor<Humor> {

    List<Humor> findByUser_IdUserAndDataRegistroAfterOrderByDataRegistroAsc(Long userId, LocalDateTime data);

    @Query(nativeQuery = true, value = """
        SELECT * FROM t_mt_humor 
        WHERE id_user = :userId 
        AND ts_registro >= :startDate 
        ORDER BY ts_registro ASC
        """)
    List<Humor> findHistoryLast30Days(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate);

    @Query("SELECT h.humorType, COUNT(h) FROM Humor h WHERE h.user.idUser = :userId AND h.dataRegistro >= :startDate GROUP BY h.humorType")
    List<Object[]> countHumorByTypesSince(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate);

    long countByUser_IdUser(Long userId);

    Page<Humor> findByUser_IdUser(Long idUser, Pageable pageable);
}
