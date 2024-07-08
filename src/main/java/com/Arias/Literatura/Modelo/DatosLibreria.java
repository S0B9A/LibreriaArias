package com.Arias.Literatura.Modelo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibreria(
        @JsonAlias("results") List<DatosLibro> libros
) {
}
