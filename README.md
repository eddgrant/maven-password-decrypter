# maven-password-decrypter

A utility for decrypting Maven passwords and master passwords.

# Use cases:

There are various potential use cases for this utility however I created it to allow me to use encrypted Maven credentials from within a Gradle build.
I find it is commonplace that corporate CI servers use the canonical Maven locations (`settings.xml` and `settings-security.xml`) to store credentials. It is often beneficial to be able to access this information in Gradle builds without having to duplicate or decrypt the credentials at rest.

# Example usage:

I have put together a [simple example project][1] which demonstrates how to use the utility within a Gradle build.

## Abuse:

Whilst the encryption mechanism used by Maven may well be trivial to reverse-engineer it still provides a basic degree of security versus storing decrypted secrets at rest.
This utility facilitates a legitimate use case of re-using those credentials without having to duplicate or decrypt them at rest, however it still goes without saying that this utility should not be abused.

## Disclaimer:

Simply put: I accept absolutely no liability for the use of this code. Please use it responsibly, don't be a bellend... everyone will hate you for it!

[1]: https://github.com/eddgrant/maven-password-decrypter-gradle-example