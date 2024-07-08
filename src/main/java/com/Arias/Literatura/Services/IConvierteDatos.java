package com.Arias.Literatura.Services;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
