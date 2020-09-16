package br.gilberto.arquitetura.util;

import java.security.SecureRandom;
import java.util.Random;

public class EncurtadorUtil {
	
	public String nextString() {
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = simbolos[random.nextInt(simbolos.length)];
        return new String(buf);
    }

    public static final String caracteres = "ABCDEFGHJKLMNPQRSTUVWXYZ123456789abcdefghijkmnpqrstuvwxyz";

    private final Random random;

    private final char[] simbolos;

    private final char[] buf;

    public EncurtadorUtil(int tamanho, SecureRandom random, String symbols) {
        this.random = random;
        this.simbolos = symbols.toCharArray();
        this.buf = new char[tamanho];
    }

    public EncurtadorUtil(int tamanho) {
        this(tamanho, new SecureRandom(), caracteres);
    }

}
