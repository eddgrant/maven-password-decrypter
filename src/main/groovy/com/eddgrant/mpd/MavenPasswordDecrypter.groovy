package com.eddgrant.mpd

import org.sonatype.plexus.components.cipher.DefaultPlexusCipher
import org.sonatype.plexus.components.cipher.PlexusCipher
import org.sonatype.plexus.components.sec.dispatcher.DefaultSecDispatcher

class MavenPasswordDecrypter {

  private final PlexusCipher cipher

  private final SettingsSecurityParser settingsSecurityParser

  private SettingsXmlParser settingsXmlParser

  public MavenPasswordDecrypter(final SettingsSecurityParser settingsSecurityParser,
                                final SettingsXmlParser settingsXmlParser,
                                final PlexusCipher cipher = new DefaultPlexusCipher()) {
    this.settingsSecurityParser = settingsSecurityParser
    this.settingsXmlParser = settingsXmlParser
    this.cipher = cipher
  }

  public String getMasterPassword(final String settingsSecurityFilePath) {
    final String encryptedMasterPassword = settingsSecurityParser.getMasterPassword(settingsSecurityFilePath)
    return decryptMasterPassword(encryptedMasterPassword)
  }

  public List<ServerDetails> getServerDetails(final String settingsXmlFilePath, final String masterPassword) {
    final List<ServerDetails> serverDetails = settingsXmlParser.getServers(settingsXmlFilePath)
    serverDetails.each { serverDetail ->
      serverDetail.decryptedPassword = decryptNonMasterPassword(serverDetail.encryptedPassword, masterPassword)
    }
    return serverDetails
  }

  private String decryptMasterPassword(final String decoratedAndEncryptedMasterPassword) {
    final String decryptedMasterPassword = cipher.decryptDecorated(decoratedAndEncryptedMasterPassword,
                                                                   DefaultSecDispatcher.SYSTEM_PROPERTY_SEC_LOCATION)
    return decryptedMasterPassword
  }

  private String decryptNonMasterPassword(final String decoratedAndEncryptedPassword,
                                          final String masterPassword) {
    final String decryptedClearTextPassword = cipher.decryptDecorated(decoratedAndEncryptedPassword, masterPassword)
    return decryptedClearTextPassword
  }
}