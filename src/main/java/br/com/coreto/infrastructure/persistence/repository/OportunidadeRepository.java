package br.com.coreto.infrastructure.persistence.repository;

import br.com.coreto.domain.entity.Oportunidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OportunidadeRepository extends JpaRepository<Oportunidade, UUID>, JpaSpecificationExecutor<Oportunidade> {

    Optional<Oportunidade> findByIdAndDeletedFalse(UUID id);
}
