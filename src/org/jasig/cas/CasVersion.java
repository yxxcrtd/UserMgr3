package org.jasig.cas;

/**
 * Class that exposes the CAS version. Fetches the "Implementation-Version" manifest attribute from the jar file.
 */
public final class CasVersion {

    /**
     * Private constructor for CasVersion. You should not be able to instanciate this class.
     */
    private CasVersion() {
        // this class is not instantiable
    }

    /**
     * Return the full CAS version string.
     * 
     * @see java.lang.Package#getImplementationVersion
     */
    public static String getVersion() {
        return CasVersion.class.getPackage().getImplementationVersion();
    }
}
