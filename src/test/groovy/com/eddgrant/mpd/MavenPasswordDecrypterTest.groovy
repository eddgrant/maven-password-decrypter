package com.eddgrant.mpd

import org.junit.Before
import org.junit.Test

class MavenPasswordDecrypterTest {

  private SettingsSecurityParser settingsSecurityParser

  private SettingsXmlParser settingsXmlParser

  private MavenPasswordDecrypter mavenPasswordDecrypter

  @Before
  public void setUp() {
    settingsSecurityParser = new SettingsSecurityParser()
    settingsXmlParser = new SettingsXmlParser()
    mavenPasswordDecrypter = new MavenPasswordDecrypter(settingsSecurityParser, settingsXmlParser)
  }

  @Test
  public void shouldDecryptMasterPassword() {
    final String filePath = "src/test/resources/test-files/settings-security.xml"
    final String expectedPassword = "12345abcde"
    assert expectedPassword == mavenPasswordDecrypter.getMasterPassword(filePath)
  }

  @Test
  public void shouldDecryptServerPasswords() {
    final String filePath = "src/test/resources/test-files/settings-with-multiple-server-entries.xml"
    final String masterPassword = "This is my master password"
    final List<ServerDetails> returnedServerDetails = mavenPasswordDecrypter.getServerDetails(filePath, masterPassword)
    (1..3).each { index ->
      assert "${index}" == returnedServerDetails[index -1].id
      assert "u${index}" == returnedServerDetails[index -1].username
      assert "This is server ${index}'s password" == returnedServerDetails[index - 1].decryptedPassword
    }
  }
}
