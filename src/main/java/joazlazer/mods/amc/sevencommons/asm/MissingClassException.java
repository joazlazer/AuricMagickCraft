package joazlazer.mods.amc.sevencommons.asm;

/**
 * <p>Indicates that a required class could not be found.</p>
 *
 * @author diesieben07
 */
public class MissingClassException extends RuntimeException {

	MissingClassException(String message) {
		super(message);
	}

	public MissingClassException(String message, Throwable cause) {
		super(message, cause);
	}
}
