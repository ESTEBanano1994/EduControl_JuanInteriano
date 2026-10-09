
package org.kinal.evaluacion.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class SecurityUtil {

    private SecurityUtil() {
    }

    public static String generarSHA256(String password) {

        try {
            MessageDigest md =
                    MessageDigest.getInstance("SHA-256");

            byte[] bytes = md.digest(
                    password.getBytes(StandardCharsets.UTF_8));

            StringBuilder resultado = new StringBuilder();

            for (byte b : bytes) {
                resultado.append(
                        String.format("%02x", b & 0xff));
            }

            return resultado.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "No se pudo utilizar SHA-256", e);
        }
    }

    public static boolean verificarPassword(
            String password, String hashGuardado) {

        if (password == null || hashGuardado == null) {
            return false;
        }

        String hashIngresado = generarSHA256(password);

        return MessageDigest.isEqual(
                hashIngresado.getBytes(StandardCharsets.US_ASCII),
                hashGuardado.toLowerCase(java.util.Locale.ROOT)
                        .getBytes(StandardCharsets.US_ASCII));
    }
}
