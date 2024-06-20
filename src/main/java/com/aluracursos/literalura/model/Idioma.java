package com.aluracursos.literalura.model;

public enum Idioma {
    INGLES ("EN", "Inglés"),
    ESPAÑOL ("ES", "Español"),
    FRANCES ("FR", "Francés"),
    PORTUGUES ("PT", "Portugués"),
    OTRO ("Otro", "Otro");


    private String idiomaApi;
    private String idiomaEspañol;

    Idioma(String idiomaApi, String idiomaEspañol) {
        this.idiomaApi = idiomaApi;
        this.idiomaEspañol = idiomaEspañol;
    }

    public static Idioma fromString(String text) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.idiomaApi.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Idioma no encontrado: " + text);
    }

    public static Idioma fromEspanol(String text) {
        for (Idioma categoria : Idioma.values()) {
            if (categoria.idiomaEspañol.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Idioma no encontrado: " + text);
    }

    }


