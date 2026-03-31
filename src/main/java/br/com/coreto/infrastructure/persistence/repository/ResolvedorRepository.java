package br.com.coreto.infrastructure.persistence.repository;

import br.com.coreto.domain.entity.Resolvedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ResolvedorRepository extends JpaRepository<Resolvedor, UUID>, JpaSpecificationExecutor<Resolvedor> {

    Optional<Resolvedor> findByIdAndDeletedFalse(UUID id);

    Optional<Resolvedor> findByUsuarioIdAndDeletedFalse(UUID usuarioId);
}
