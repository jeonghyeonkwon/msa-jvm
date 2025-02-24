package jeonghyeon.msa.auth.repository;

import jeonghyeon.msa.auth.domain.Outbox;
import jeonghyeon.msa.auth.domain.OutboxType;
import jeonghyeon.msa.common.event.EventType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OutboxRepository extends JpaRepository<Outbox, Long> {
    @Query("SELECT o FROM Outbox o WHERE o.outboxType = :outboxType AND o.createdDate > :createdAt ORDER BY o.createdDate ASC")
    List<Outbox> findByCreatedAtLessThanEqualsOrderByCreatedAtAsc(
            @Param("outboxType") OutboxType outboxType,
            @Param("createdAt") LocalDateTime minusSeconds,
            Pageable ofSize);
}
