package assertk.assertions

import assertk.assertions.support.expected
import assertk.assertions.support.show
import assertk.Assert
import java.io.File
import java.nio.charset.Charset

/**
 * Returns an assert on the file's name.
 */
fun Assert<File>.name() = prop("name", File::getName)

/**
 * Returns an assert on the file's path.
 */
fun Assert<File>.path() = prop("path", File::getPath)

/**
 * Returns an assert on the file's parent.
 */
fun Assert<File>.parent() = prop("parent", File::getParent)

/**
 * Returns an assert on the file's extension.
 */
fun Assert<File>.extension() = prop("extension", File::extension)

/**
 * Returns an assert on the file's contents as text.
 */
fun Assert<File>.text(charset: Charset = Charsets.UTF_8) = prop("text", { it.readText(charset) })

/**
 * Returns an assert on the file's contents as bytes.
 */
fun Assert<File>.bytes() = prop("bytes", File::readBytes)

/**
 * Asserts the file exists.
 */
fun Assert<File>.exists() {
    if (actual.exists()) return
    expected("to exist")
}

/**
 * Asserts the file is a directory.
 * @see [isFile]
 */
fun Assert<File>.isDirectory() {
    if (actual.isDirectory) return
    expected("to be a directory")
}

/**
 * Asserts the file is a simple file (not a directory).
 * @see [isDirectory]
 */
fun Assert<File>.isFile() {
    if (actual.isFile) return
    expected("to be a file")
}

/**
 * Asserts the file is hidden.
 * @see [isNotHidden]
 */
fun Assert<File>.isHidden() {
    if (actual.isHidden) return
    expected("to be hidden")
}

/**
 * Asserts the file is not hidden.
 * @see [isHidden]
 */
fun Assert<File>.isNotHidden() {
    if (!actual.isHidden) return
    expected("to not be hidden")
}

/**
 * Asserts the file has the expected name.
 */
@Deprecated(message = "Use name().isEqualTo(expected) instead",
        replaceWith = ReplaceWith("name().isEqualTo(expected)"))
fun Assert<File>.hasName(expected: String) {
    assert(actual.name, "name").isEqualTo(expected)
}

/**
 * Asserts the file has the expected path.
 */
@Deprecated(message = "Use path().isEqualTo(expected) instead",
        replaceWith = ReplaceWith("path().isEqualTo(expected)"))
fun Assert<File>.hasPath(expected: String) {
    assert(actual.path, "path").isEqualTo(expected)
}

/**
 * Asserts the file has the expected parent path.
 */
@Deprecated(message = "Use parent().isEqualTo(expected) instead",
        replaceWith = ReplaceWith("parent().isEqualTo(expected)"))
fun Assert<File>.hasParent(expected: String) {
    assert(actual.parent, "parent").isEqualTo(expected)
}

/**
 * Asserts the file has the expected extension.
 */
@Deprecated(message = "Use extension().isEqualTo(expected) instead",
        replaceWith = ReplaceWith("extension().isEqualTo(expected)"))
fun Assert<File>.hasExtension(expected: String) {
    assert(actual.extension, "extension").isEqualTo(expected)
}

/**
 * Asserts the file contains exactly the expected text (and nothing else).
 * @param charset The character set of the file, default is [Charsets.UTF_8]
 * @see [containsText]
 * @see [matchesText]
 */
@Deprecated(message = "Use text(charset).isEqualTo(expected) instead",
        replaceWith = ReplaceWith("text(charset).isEqualTo(expected)"))
fun Assert<File>.hasText(expected: String, charset: Charset = Charsets.UTF_8) {
    val text = actual.readText(charset)
    assert(text, "text").isEqualTo(expected)
}

/**
 * Asserts the file contains the expected text, it may contain other things.
 * @param charset The character set of the file, default is [Charsets.UTF_8]
 * @see [hasText]
 * @see [matchesText]
 */
@Deprecated(message = "Use text(charset).contains(expected) instead",
        replaceWith = ReplaceWith("text(charset).contains(expected)"))
fun Assert<File>.containsText(expected: String, charset: Charset = Charsets.UTF_8) {
    val text = actual.readText(charset)
    assert(text, "text").contains(expected)
}

/**
 * Asserts the file's text matches the expected regular expression.
 * @param charset The character set of the file, default is [Charsets.UTF_8]
 * @see [hasText]
 * @see [matchesText]
 */
@Deprecated(message = "Use text(charset).matches(expected) instead",
        replaceWith = ReplaceWith("text(charset).matches(expected)"))
fun Assert<File>.matchesText(expected: Regex, charset: Charset = Charsets.UTF_8) {
    val text = actual.readText(charset)
    assert(text, "text").matches(expected)
}

/**
 * Asserts the file has the expected direct child.
 */
fun Assert<File>.hasDirectChild(expected: File) {
    if (actual.listFiles()?.contains(expected) == true) return
    expected("to have direct child ${show(expected)}")
}
