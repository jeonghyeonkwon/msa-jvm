package jeonghyeon.msa.auth.repository;

import jeonghyeon.msa.auth.domain.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboxRepository extends JpaRepository<Outbox, Long> {
}
