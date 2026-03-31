package br.com.coreto.infrastructure.persistence.repository;

import br.com.coreto.domain.entity.Talento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TalentoRepository extends JpaRepository<Talento, UUID>, JpaSpecificationExecutor<Talento> {

    Optional<Talento> findByIdAndDeletedFalse(UUID id);

    Optional<Talento> findByUsuarioIdAndDeletedFalse(UUID usuarioId);
}
