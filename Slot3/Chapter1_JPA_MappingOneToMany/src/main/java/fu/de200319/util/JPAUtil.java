package fu.de200319.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    // Tên "hsf302FU" phải khớp y hệt với <persistence-unit name="hsf302FU"> trong persistence.xml
    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("hsf302FU");

    // Chặn không cho tạo object từ class này (áp dụng pattern Singleton)
    private JPAUtil() {
    }

    public static EntityManagerFactory getEMF() {
        return EMF;
    }

    public static void close() {
        if (EMF.isOpen()) {
            EMF.close();
        }
    }
}