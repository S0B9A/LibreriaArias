package com.Arias.Literatura.Repositorio;

import com.Arias.Literatura.Modelo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT a FROM Autor a WHERE annoDeNacimiento <= :anno AND annoDeFallecimiento > :anno")
    public List<Autor> ListarAutoresVivosDuranteUnAnno(long anno);
}
