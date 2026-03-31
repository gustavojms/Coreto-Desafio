package br.com.coreto.infrastructure.persistence.repository;

import br.com.coreto.domain.entity.Organizador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizadorRepository extends JpaRepository<Organizador, UUID>, JpaSpecificationExecutor<Organizador> {

    Optional<Organizador> findByIdAndDeletedFalse(UUID id);

    Optional<Organizador> findByUsuarioIdAndDeletedFalse(UUID usuarioId);

    boolean existsByCnpjAndDeletedFalse(String cnpj);
}
