package ch.zhaw.pm3.helpy.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Class describing the possible athenticatio levels
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Profiles {

    public static final String NO_AUTH = "noauth";
    public static final String BASIC_AUTH = "basicauth";

}
