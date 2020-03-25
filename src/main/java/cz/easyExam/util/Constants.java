package cz.easyExam.util;

import cz.easyExam.model.Role;

public final class Constants {

    /**
     * Default user role.
     */
    public static final Role DEFAULT_ROLE = Role.USER;

    private Constants() {
        throw new AssertionError();
    }
}
